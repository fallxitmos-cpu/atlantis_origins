package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.attachment.PlayerAttachments;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;

@EventBusSubscriber(modid = AtlantisOrigins.MOD_ID)
public class PoseidonsEvolutionHandler {

    private static final ResourceLocation HEALTH_BONUS_ID = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "evolution_health_bonus");
    private static final ResourceLocation SWIM_SPEED_BONUS_ID = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "evolution_swim_speed_bonus");

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        applyIfEvolved(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        applyIfEvolved(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        if (!PlayerAttachments.isEvolved(player)) {
            return;
        }
        if (player.isEyeInFluid(net.minecraft.tags.FluidTags.WATER)) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false, false));
        }
    }

    public static void applyIfEvolved(Player player) {
        if (!PlayerAttachments.isEvolved(player)) {
            return;
        }
        addModifier(player, Attributes.MAX_HEALTH, HEALTH_BONUS_ID, 20.0,
                AttributeModifier.Operation.ADD_VALUE);
        addModifier(player, NeoForgeMod.SWIM_SPEED, SWIM_SPEED_BONUS_ID, 1.0,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    private static void addModifier(Player player, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
                                    ResourceLocation id, double amount, AttributeModifier.Operation operation) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) {
            return;
        }
        if (instance.getModifier(id) != null) {
            return;
        }
        instance.addPermanentModifier(new AttributeModifier(id, amount, operation));
    }
}
