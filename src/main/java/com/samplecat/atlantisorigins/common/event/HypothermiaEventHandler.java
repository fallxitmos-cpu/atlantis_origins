package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.attachment.PlayerAttachments;
import com.samplecat.atlantisorigins.common.config.Config;
import com.samplecat.atlantisorigins.common.damage.ModDamageTypes;
import com.samplecat.atlantisorigins.common.effect.ModMobEffects;

import com.samplecat.atlantisorigins.common.item.ModItems;
import com.samplecat.atlantisorigins.common.util.BiomeHelper;
import com.samplecat.atlantisorigins.common.util.WaterCheckHelper;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = AtlantisOrigins.MOD_ID)
public class HypothermiaEventHandler {

    private static final int EFFECT_DURATION = 120; // 6 seconds
    private static final int FREEZE_TICKS = 90; // enough for visible ice overlay, below 140 damage threshold

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        if (entity.level().isClientSide()) {
            return;
        }

        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;
        if (player.isSpectator() || player.isCreative()) {
            return;
        }

        if (!Config.HYPOTHERMIA_ENABLED.get()) {
            return;
        }

        int ticks = PlayerAttachments.getHypothermiaTicks(entity);
        boolean inValidEnvironment = BiomeHelper.isOceanBiome(entity.level(), entity.blockPosition())
                && WaterCheckHelper.isFullySubmerged(entity);

        if (!inValidEnvironment && ticks <= 0) {
            return;
        }

        // Evolution from Poseidon's flesh grants complete immunity to hypothermia.
        if (PlayerAttachments.isEvolved(entity)) {
            if (ticks > 0) {
                PlayerAttachments.setHypothermiaTicks(entity, 0);
            }
            return;
        }

        if (inValidEnvironment) {
            ticks++;
            PlayerAttachments.setHypothermiaTicks(entity, ticks);

            int threshold = getHypothermiaThreshold(entity.level(), entity.blockPosition());
            if (hasBeginnerDivingSet(entity)) {
                threshold = (int) (threshold * 1.2);
            }

            if (ticks >= threshold) {
                applyHypothermiaEffects(entity);
            }
        } else {
            // Recover much faster than accumulation so leaving the water actually resets hypothermia.
            if (ticks > 0) {
                ticks = Math.max(0, ticks - 10);
                PlayerAttachments.setHypothermiaTicks(entity, ticks);
            }
        }
    }

    private static int getHypothermiaThreshold(Level level, BlockPos pos) {
        Holder<Biome> biomeHolder = level.getBiome(pos);
        ResourceKey<Biome> key = biomeHolder.unwrapKey().orElse(null);
        if (key == null) {
            return Integer.MAX_VALUE;
        }

        String path = key.location().getPath();
        int threshold;
        if (path.contains("frozen")) {
            threshold = 1000;
        } else if (path.contains("cold")) {
            threshold = 3000;
        } else if (path.contains("lukewarm")) {
            threshold = 6000;
        } else if (path.equals("warm_ocean")) {
            threshold = 20000;
        } else {
            return Integer.MAX_VALUE;
        }

        if (pos.getY() < 30) {
            threshold -= path.equals("warm_ocean") ? 250 : 500;
        }

        return threshold;
    }

    private static boolean hasBeginnerDivingSet(LivingEntity entity) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
        return chest.is(ModItems.BEGINNER_DIVING_SUIT.get()) && legs.is(ModItems.BEGINNER_DIVING_PANTS.get());
    }

    private static void applyHypothermiaEffects(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(
                ModMobEffects.HYPOTHERMIA, EFFECT_DURATION, 0, false, false, false));

        // Add visible ice/frost overlay without hitting the 140-tick damage threshold.
        entity.setTicksFrozen(Math.min(FREEZE_TICKS, entity.getTicksFrozen() + 5));

        if (entity.tickCount % 40 == 0) {
            entity.hurt(entity.damageSources().source(ModDamageTypes.TRUE_DAMAGE), 1.0F);
        }
    }
}
