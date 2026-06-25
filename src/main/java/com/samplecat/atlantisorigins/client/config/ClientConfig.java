package com.samplecat.atlantisorigins.client.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CLIENT_RENDERING_ENABLED = BUILDER
            .comment("Whether client-side rendering effects are enabled")
            .define("clientRenderingEnabled", true);

    public static final ModConfigSpec.EnumValue<BloodOverlayMode> BLOOD_OVERLAY_MODE = BUILDER
            .comment("Blood overlay rendering mode")
            .defineEnum("bloodOverlayMode", BloodOverlayMode.RED);

    public static final ModConfigSpec.BooleanValue DEBUG_MODE = BUILDER
            .comment("Enable debug overlay showing current Y and Atlantis effect statuses")
            .define("debugMode", false);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public enum BloodOverlayMode {
        RED,
        HARMONIZED,
        BLACK_WHITE,
        OFF
    }
}
