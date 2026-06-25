package com.samplecat.atlantisorigins.common.entity;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, AtlantisOrigins.MOD_ID);

    public static final Supplier<EntityType<AbyssalJellyfish>> ABYSSAL_JELLYFISH = ENTITIES.register(
            "abyssal_jellyfish",
            () -> EntityType.Builder.of(AbyssalJellyfish::new, MobCategory.WATER_CREATURE)
                    .sized(1.0F, 1.3F)
                    .clientTrackingRange(8)
                    .build(AtlantisOrigins.MOD_ID + ":abyssal_jellyfish"));

    public static final Supplier<EntityType<BioluminescentFish>> BIOLUMINESCENT_FISH = ENTITIES.register(
            "bioluminescent_fish",
            () -> EntityType.Builder.of(BioluminescentFish::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5F, 0.4F)
                    .clientTrackingRange(8)
                    .build(AtlantisOrigins.MOD_ID + ":bioluminescent_fish"));

    public static final Supplier<EntityType<DeepEel>> DEEP_EEL = ENTITIES.register(
            "deep_eel",
            () -> EntityType.Builder.of(DeepEel::new, MobCategory.WATER_AMBIENT)
                    // 参照 bbmodel 尺寸（高约 3px ≈ 0.19 格）并适当放大以兼顾游戏体验
                    .sized(0.6F, 0.4F)
                    .clientTrackingRange(8)
                    .build(AtlantisOrigins.MOD_ID + ":deep_eel"));

    public static final Supplier<EntityType<AbyssalCrab>> ABYSSAL_CRAB = ENTITIES.register(
            "abyssal_crab",
            () -> EntityType.Builder.of(AbyssalCrab::new, MobCategory.WATER_CREATURE)
                    .sized(0.7F, 0.5F)
                    .clientTrackingRange(8)
                    .build(AtlantisOrigins.MOD_ID + ":abyssal_crab"));

    public static final Supplier<EntityType<Gulper>> GULPER = ENTITIES.register(
            "gulper",
            () -> EntityType.Builder.of(Gulper::new, MobCategory.WATER_CREATURE)
                    .sized(0.8F, 0.7F)
                    .clientTrackingRange(8)
                    .build(AtlantisOrigins.MOD_ID + ":gulper"));

    public static final Supplier<EntityType<KrillSwarm>> KRILL_SWARM = ENTITIES.register(
            "krill_swarm",
            () -> EntityType.Builder.of(KrillSwarm::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(6)
                    .build(AtlantisOrigins.MOD_ID + ":krill_swarm"));

    public static final Supplier<EntityType<StingingJellyfish>> STINGING_JELLYFISH = ENTITIES.register(
            "stinging_jellyfish",
            () -> EntityType.Builder.of(StingingJellyfish::new, MobCategory.WATER_AMBIENT)
                    .sized(0.8F, 1.0F)
                    .clientTrackingRange(8)
                    .build(AtlantisOrigins.MOD_ID + ":stinging_jellyfish"));

    public static final Supplier<EntityType<GazingJellyfish>> GAZING_JELLYFISH = ENTITIES.register(
            "gazing_jellyfish",
            () -> EntityType.Builder.of(GazingJellyfish::new, MobCategory.WATER_AMBIENT)
                    .sized(0.8F, 1.0F)
                    .clientTrackingRange(8)
                    .build(AtlantisOrigins.MOD_ID + ":gazing_jellyfish"));

    public static final Supplier<EntityType<DeepGuardian>> DEEP_GUARDIAN = ENTITIES.register(
            "deep_guardian",
            () -> EntityType.Builder.of(DeepGuardian::new, MobCategory.MONSTER)
                    // The model root is now at the head, so a 1x1x1 box tightly fits it.
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(8)
                    .build(AtlantisOrigins.MOD_ID + ":deep_guardian"));

    public static final Supplier<EntityType<PoseidonsBlade>> POSEIDONS_BLADE = ENTITIES.register(
            "poseidons_blade",
            () -> EntityType.Builder.of(PoseidonsBlade::new, MobCategory.MONSTER)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(8)
                    .build(AtlantisOrigins.MOD_ID + ":poseidons_blade"));

    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ABYSSAL_JELLYFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                (type, level, reason, pos, rand) -> Squid.checkSurfaceWaterAnimalSpawnRules(type, level, reason, pos, rand) && pos.getY() < 0,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(BIOLUMINESCENT_FISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                AbstractFish::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(DEEP_EEL.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                AbstractFish::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ABYSSAL_CRAB.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                Axolotl::checkAxolotlSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GULPER.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                AbstractFish::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(KRILL_SWARM.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                AbstractFish::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(STINGING_JELLYFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                Squid::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GAZING_JELLYFISH.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                Squid::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(DEEP_GUARDIAN.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                (type, level, reason, pos, rand) -> Guardian.checkGuardianSpawnRules(type, level, reason, pos, rand) && pos.getY() < 30,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(POSEIDONS_BLADE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR,
                (type, level, reason, pos, rand) -> Guardian.checkGuardianSpawnRules(type, level, reason, pos, rand) && pos.getY() < 30,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
