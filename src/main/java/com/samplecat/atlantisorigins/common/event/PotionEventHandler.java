package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.util.CuriosHelper;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

/**
 * Removes water breathing effects from players wearing an oxygen tank.
 * <p>
 * This catches drinking, splash/lingering potions, commands and any other source.
 * {@link OxygenEventHandler} also continuously removes any existing water breathing effect.
 */
@EventBusSubscriber(modid = AtlantisOrigins.MOD_ID)
public class PotionEventHandler {

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event) {
        MobEffect effect = event.getEffectInstance().getEffect().value();
        if (effect != MobEffects.WATER_BREATHING) {
            return;
        }

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (CuriosHelper.hasOxygenTankEquipped(player)) {
            player.removeEffect(MobEffects.WATER_BREATHING);
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.translatable("message.atlantis_origins.no_water_breathing_with_tank"),
                    true);
        }
    }
}
