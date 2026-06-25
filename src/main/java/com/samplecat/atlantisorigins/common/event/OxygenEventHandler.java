package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.attachment.PlayerAttachments;
import com.samplecat.atlantisorigins.common.config.Config;
import com.samplecat.atlantisorigins.common.network.ModNetwork;
import com.samplecat.atlantisorigins.common.util.CuriosHelper;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import net.minecraft.util.RandomSource;

@EventBusSubscriber(modid = AtlantisOrigins.MOD_ID)
@SuppressWarnings("deprecation")
public class OxygenEventHandler {

    private static final int BREATH_INTERVAL = 40;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide() || player.isSpectator() || player.isCreative()) {
            return;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        boolean tankEquipped = CuriosHelper.hasOxygenTankEquipped(player);
        boolean headUnderwater = player.isEyeInFluid(net.minecraft.tags.FluidTags.WATER);
        boolean fullySubmerged = player.isUnderWater();

        float maxOxygen = Config.OXYGEN_TANK_MAX_OXYGEN.get().floatValue();
        float oxygen = PlayerAttachments.getOxygen(player);

        // Disable water breathing effect while tank is equipped
        if (tankEquipped && player.hasEffect(MobEffects.WATER_BREATHING)) {
            player.removeEffect(MobEffects.WATER_BREATHING);
        }

        if (tankEquipped) {
            if (headUnderwater) {
                // Consume / recover on breath ticks
                if (player.tickCount % BREATH_INTERVAL == 0) {
                    float consumption = player.getY() < 0 ? 1.0F * Config.OXYGEN_CONSUMPTION_DEEP_MULTIPLIER.get().floatValue() : 1.0F;

                    oxygen -= consumption;

                    // Recovery chances
                    RandomSource random = player.getRandom();
                    if (random.nextFloat() < 0.0025F) {
                        oxygen += 0.75F;
                    }
                    if (random.nextFloat() < 0.025F) {
                        oxygen += 0.25F;
                    }

                    // Clamp minimum to 1 so tank never fully empties
                    if (oxygen < 1.0F) {
                        oxygen = 1.0F;
                    }
                    if (oxygen > maxOxygen) {
                        oxygen = maxOxygen;
                    }

                    PlayerAttachments.setOxygen(player, oxygen);
                }

                // While tank has meaningful oxygen, keep vanilla air full
                if (oxygen > maxOxygen * 0.01F) {
                    player.setAirSupply(player.getMaxAirSupply());
                }
            } else {
                // Head above water: recover oxygen
                int recoveryTicks = Config.OXYGEN_TANK_RECOVERY_TIME.get() * 20;
                float recoveryRate = maxOxygen / recoveryTicks;
                if (oxygen < maxOxygen) {
                    oxygen = Math.min(maxOxygen, oxygen + recoveryRate);
                    PlayerAttachments.setOxygen(player, oxygen);
                }
            }
        }

        // Sync oxygen to client every 10 ticks
        if (player.tickCount % 10 == 0) {
            ModNetwork.sendOxygenToClient(serverPlayer, Math.round(PlayerAttachments.getOxygen(player)));
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            float oxygen = PlayerAttachments.getOxygen(serverPlayer);
            ModNetwork.sendOxygenToClient(serverPlayer, Math.round(oxygen));
        }
    }
}
