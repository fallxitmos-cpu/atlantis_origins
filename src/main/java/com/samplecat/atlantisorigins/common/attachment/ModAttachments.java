package com.samplecat.atlantisorigins.common.attachment;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.config.Config;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES, AtlantisOrigins.MOD_ID);

    // Oxygen is intentionally NOT copyOnDeath: respawning should refresh air supply.
    public static final Supplier<AttachmentType<Float>> PLAYER_OXYGEN = ATTACHMENT_TYPES.register(
            "player_oxygen", () -> AttachmentType.builder(() -> (float) Config.OXYGEN_TANK_MAX_OXYGEN.get()).build());

    public static final Supplier<AttachmentType<Integer>> HYPOTHERMIA_TICKS = ATTACHMENT_TYPES.register(
            "hypothermia_ticks", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<Integer>> PRESSURE_STAGE = ATTACHMENT_TYPES.register(
            "pressure_stage", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<Float>> EFFECT_INTENSITY = ATTACHMENT_TYPES.register(
            "effect_intensity", () -> AttachmentType.builder(() -> 0.0F)
                    .serialize(Codec.FLOAT)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<Integer>> DCS_STAGE = ATTACHMENT_TYPES.register(
            "dcs_stage", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<Integer>> DCS_TIMER = ATTACHMENT_TYPES.register(
            "dcs_timer", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<Float>> DCS_DAMAGE_WINDOW = ATTACHMENT_TYPES.register(
            "dcs_damage_window", () -> AttachmentType.builder(() -> 0.0F)
                    .serialize(Codec.FLOAT)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<Boolean>> DCS_PENDING_MILD = ATTACHMENT_TYPES.register(
            "dcs_pending_mild", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<Double>> DCS_LAST_Y = ATTACHMENT_TYPES.register(
            "dcs_last_y", () -> AttachmentType.builder(() -> 0.0)
                    .serialize(Codec.DOUBLE)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<Long>> DCS_LAST_Y_TICK = ATTACHMENT_TYPES.register(
            "dcs_last_y_tick", () -> AttachmentType.builder(() -> 0L)
                    .serialize(Codec.LONG)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<Boolean>> EVOLVED = ATTACHMENT_TYPES.register(
            "evolved", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL)
                    .copyOnDeath()
                    .sync(ByteBufCodecs.BOOL)
                    .build());

}
