package com.samplecat.atlantisorigins.common.item;

import com.samplecat.atlantisorigins.common.attachment.PlayerAttachments;
import com.samplecat.atlantisorigins.common.damage.ModDamageSources;
import com.samplecat.atlantisorigins.common.event.PoseidonsEvolutionHandler;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PoseidonsFleshItem extends Item {

    public PoseidonsFleshItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (level instanceof ServerLevel serverLevel && entity instanceof ServerPlayer player) {
            PlayerAttachments.setEvolved(player, true);
            PoseidonsEvolutionHandler.applyIfEvolved(player);
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0, false, false, false));
            player.hurt(ModDamageSources.evolution(serverLevel, player), Float.MAX_VALUE);
        }
        return result;
    }
}
