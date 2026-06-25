package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.common.item.ModItems;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class TridentEventHandler {

    private static final float ORICHALCUM_THROWN_MIN_DAMAGE = 15.0F;
    private static final float CRIT_CHANCE = 0.20F;
    private static final float CRIT_MULTIPLIER = 1.5F;

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        DamageSource source = event.getSource();
        if (!source.is(DamageTypes.TRIDENT)) {
            return;
        }

        Entity direct = source.getDirectEntity();
        if (!(direct instanceof ThrownTrident trident)) {
            return;
        }

        ItemStack weapon = trident.getWeaponItem();
        if (!weapon.is(ModItems.ORICHALCUM_TRIDENT.get())) {
            return;
        }

        float amount = event.getAmount();
        if (amount < ORICHALCUM_THROWN_MIN_DAMAGE) {
            amount = ORICHALCUM_THROWN_MIN_DAMAGE;
        }

        if (trident.level().getRandom().nextFloat() < CRIT_CHANCE) {
            amount *= CRIT_MULTIPLIER;
        }

        event.setAmount(amount);
    }
}
