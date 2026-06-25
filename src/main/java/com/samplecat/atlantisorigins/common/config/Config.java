package com.samplecat.atlantisorigins.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue PRESSURE_ENABLED = BUILDER
            .comment("Whether the pressure system is enabled")
            .define("pressureEnabled", true);

    public static final ModConfigSpec.BooleanValue HYPOTHERMIA_ENABLED = BUILDER
            .comment("Whether the hypothermia system is enabled")
            .define("hypothermiaEnabled", true);

    public static final ModConfigSpec.BooleanValue DECOMPRESSION_SICKNESS_ENABLED = BUILDER
            .comment("Whether decompression sickness is enabled")
            .define("decompressionSicknessEnabled", false);

    public static final ModConfigSpec.IntValue OXYGEN_TANK_MAX_OXYGEN = BUILDER
            .comment("Maximum oxygen amount for the oxygen tank")
            .defineInRange("oxygenTankMaxOxygen", 300, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue OXYGEN_TANK_RECOVERY_TIME = BUILDER
            .comment("Seconds for oxygen tank to fully recover after surfacing")
            .defineInRange("oxygenTankRecoveryTimeSeconds", 3, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue OXYGEN_CONSUMPTION_DEEP_MULTIPLIER = BUILDER
            .comment("Oxygen consumption multiplier when Y < 0")
            .defineInRange("oxygenConsumptionDeepMultiplier", 1.1, 0.1, 10.0);

    public static final ModConfigSpec.DoubleValue HORROR_SPEED_BOOST = BUILDER
            .comment("Swim speed boost from horror effect")
            .defineInRange("horrorSpeedBoost", 0.1, 0.0, 10.0);

    public static final ModConfigSpec.IntValue EFFECT_FADE_OUT_TICKS = BUILDER
            .comment("Ticks for effects to fade out after leaving depth")
            .defineInRange("effectFadeOutTicks", 100, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.BooleanValue USE_BUILTIN_DYNAMIC_LIGHT = BUILDER
            .comment("Use built-in dynamic light for diving lamp; fall back to LambDynamicLights if false or unavailable")
            .define("useBuiltinDynamicLight", true);

    public static final ModConfigSpec.BooleanValue ORICHALCUM_ARMOR_EFFECTS_ENABLED = BUILDER
            .comment("Whether Orichalcum armor piece bonuses and full-set bonuses are enabled")
            .define("orichalcumArmorEffectsEnabled", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
