package com.samplecat.atlantisorigins.common.world;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import net.neoforged.fml.util.ObfuscationReflectionHelper;

import com.mojang.datafixers.util.Pair;
import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.config.Config;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * Injects the mod's custom ocean biomes into the Overworld's {@link MultiNoiseBiomeSource}
 * before any {@link net.minecraft.server.level.ServerLevel} is created. This is necessary
 * because 1.21.1's datapack registry {@code multi_noise_biome_source_parameter_list} only
 * accepts the built-in {@code minecraft:overworld} / {@code minecraft:nether} presets, so
 * custom biome parameters cannot be added via JSON alone.
 *
 * <p>Patching at {@link ServerAboutToStartEvent} ensures the modified biome source is used
 * when Minecraft builds the Overworld {@link net.minecraft.server.level.ServerLevel}, so
 * feature-per-step caches and chunk generator state are consistent from the start.
 */
public class OverworldBiomeInjector {

    private static final AtomicBoolean PATCHED = new AtomicBoolean(false);

    private static final ResourceKey<Biome> GREEN_ALGAE_SEA = ResourceKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "green_algae_sea"));

    private static final ResourceKey<Biome> GREEN_ALGAE_DEEP_SEA = ResourceKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "green_algae_deep_sea"));

    /**
     * Vanilla ocean biomes that will be replaced by the custom green-algae biomes.
     * This prevents the custom entries from losing to vanilla oceans in the
     * multi-noise nearest-neighbor search.
     */
    private static final Set<ResourceKey<Biome>> VANILLA_OCEANS = Set.of(
            Biomes.OCEAN,
            Biomes.DEEP_OCEAN,
            Biomes.COLD_OCEAN,
            Biomes.DEEP_COLD_OCEAN,
            Biomes.FROZEN_OCEAN,
            Biomes.DEEP_FROZEN_OCEAN,
            Biomes.LUKEWARM_OCEAN,
            Biomes.DEEP_LUKEWARM_OCEAN,
            Biomes.WARM_OCEAN
    );

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        if (!Config.INJECT_OVERWORLD_BIOMES.get()) {
            return;
        }
        if (!PATCHED.compareAndSet(false, true)) {
            return;
        }

        patchOverworld(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        PATCHED.set(false);
    }

    private static void patchOverworld(MinecraftServer server) {
        Registry<LevelStem> levelStems = server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);
        LevelStem overworldStem = levelStems.get(LevelStem.OVERWORLD);
        if (overworldStem == null) {
            AtlantisOrigins.LOGGER.warn("Overworld LevelStem is missing; skipping biome injection.");
            return;
        }

        ChunkGenerator generator = overworldStem.generator();
        if (!(generator.getBiomeSource() instanceof MultiNoiseBiomeSource source)) {
            AtlantisOrigins.LOGGER.warn("Overworld generator is not using MultiNoiseBiomeSource; skipping biome injection.");
            return;
        }

        Registry<Biome> biomes = server.registryAccess().registryOrThrow(Registries.BIOME);
        Holder<Biome> seaHolder = biomes.getHolder(GREEN_ALGAE_SEA).orElse(null);
        Holder<Biome> deepHolder = biomes.getHolder(GREEN_ALGAE_DEEP_SEA).orElse(null);
        if (seaHolder == null || deepHolder == null) {
            AtlantisOrigins.LOGGER.warn("Custom ocean biomes are missing from the biome registry; skipping injection.");
            return;
        }

        // Avoid injecting twice if the source already contains our biomes (e.g. after a /reload).
        if (containsBiome(source, GREEN_ALGAE_SEA) && containsBiome(source, GREEN_ALGAE_DEEP_SEA)) {
            AtlantisOrigins.LOGGER.debug("Overworld biome source already contains custom ocean biomes.");
            return;
        }

        boolean isVanillaOverworld = source.stable(MultiNoiseBiomeSourceParameterLists.OVERWORLD);
        List<Pair<Climate.ParameterPoint, Holder<Biome>>> combined = new ArrayList<>();
        for (Pair<Climate.ParameterPoint, Holder<Biome>> entry : getParameters(source).values()) {
            // For the vanilla overworld preset we replace vanilla oceans so our custom
            // biomes actually win the nearest-neighbor search. For other presets (e.g.
            // Terralith/Tectonic custom sources) we just append our entries.
            if (isVanillaOverworld && VANILLA_OCEANS.contains(entry.getSecond().unwrapKey().orElse(null))) {
                continue;
            }
            combined.add(entry);
        }
        combined.add(Pair.of(createSurfaceParameters(), seaHolder));
        combined.add(Pair.of(createDeepParameters(), deepHolder));

        MultiNoiseBiomeSource newSource = MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(combined));
        replaceBiomeSource(generator, newSource);
        // Pre-compute the per-step feature mappings with the new biome source so that
        // the first chunk decoration does not use stale data.
        generator.refreshFeaturesPerStep();
        generator.validate();

        AtlantisOrigins.LOGGER.info("Injected custom ocean biomes into the Overworld biome source.");
    }

    private static boolean containsBiome(MultiNoiseBiomeSource source, ResourceKey<Biome> key) {
        for (Pair<Climate.ParameterPoint, Holder<Biome>> entry : getParameters(source).values()) {
            if (entry.getSecond().is(key)) {
                return true;
            }
        }
        return false;
    }

    private static Climate.ParameterPoint createSurfaceParameters() {
        return Climate.parameters(
                Climate.Parameter.span(-1.0F, 1.0F),      // temperature (all oceans)
                Climate.Parameter.span(-1.0F, 1.0F),      // humidity
                Climate.Parameter.span(-1.05F, -0.19F),   // continentalness (ocean, including near-shore)
                Climate.Parameter.span(-1.0F, 1.0F),      // erosion
                Climate.Parameter.point(0.0F),            // depth (surface)
                Climate.Parameter.span(-1.0F, 1.0F),      // weirdness
                0.0F);                                    // offset: equal weight with vanilla
    }

    private static Climate.ParameterPoint createDeepParameters() {
        return Climate.parameters(
                Climate.Parameter.span(-1.0F, 1.0F),
                Climate.Parameter.span(-1.0F, 1.0F),
                Climate.Parameter.span(-1.05F, -0.19F),
                Climate.Parameter.span(-1.0F, 1.0F),
                Climate.Parameter.point(1.0F),            // depth (deep/cave layer)
                Climate.Parameter.span(-1.0F, 1.0F),
                0.0F);
    }

    @SuppressWarnings("unchecked")
    private static Climate.ParameterList<Holder<Biome>> getParameters(MultiNoiseBiomeSource source) {
        try {
            Method method = ObfuscationReflectionHelper.findMethod(MultiNoiseBiomeSource.class, "parameters");
            method.setAccessible(true);
            return (Climate.ParameterList<Holder<Biome>>) method.invoke(source);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to read MultiNoiseBiomeSource parameters", e);
        }
    }

    private static void replaceBiomeSource(ChunkGenerator generator, BiomeSource newSource) {
        try {
            Field field = ObfuscationReflectionHelper.findField(ChunkGenerator.class, "biomeSource");
            field.setAccessible(true);
            field.set(generator, newSource);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to replace Overworld ChunkGenerator biome source", e);
        }
    }
}
