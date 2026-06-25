package com.samplecat.atlantisorigins.common.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class BiomeHelper {

    public static boolean isOceanBiome(Level level, net.minecraft.core.BlockPos pos) {
        Holder<Biome> biomeHolder = level.getBiome(pos);

        if (biomeHolder.is(BiomeTags.IS_OCEAN)) {
            return true;
        }

        ResourceKey<Biome> key = biomeHolder.unwrapKey().orElse(null);
        if (key == null) {
            return false;
        }

        return key.location().getPath().contains("ocean");
    }
}
