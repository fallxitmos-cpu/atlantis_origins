package com.samplecat.atlantisorigins.common.world.feature;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, AtlantisOrigins.MOD_ID);

    public static final Supplier<Feature<NoneFeatureConfiguration>> BRIMSTONE_ORE = FEATURES.register(
            "brimstone_ore", () -> new BrimstoneOreFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<NoneFeatureConfiguration>> SILVER_AMALGAM_BLOCK = FEATURES.register(
            "silver_amalgam_block", () -> new QuicksilverOreFeature(NoneFeatureConfiguration.CODEC));
}
