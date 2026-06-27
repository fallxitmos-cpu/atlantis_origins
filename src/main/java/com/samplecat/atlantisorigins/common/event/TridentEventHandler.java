package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.common.entity.DeepGuardian;
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

    private static final float DEEP_GUARDIAN_THROWN_DAMAGE = 10.0F;
    private static final double PACK_BONUS_RADIUS = 8.0D;
    private static final int PACK_BONUS_COUNT = 5;
    private static final float PACK_BONUS_MULTIPLIER = 1.2F;

    private static final float MOURNING_DAMAGE_MULTIPLIER = 1.1F;

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        // Deep Guardians are fully immune to damage from other Deep Guardians.
        if (event.getEntity() instanceof DeepGuardian
                && event.getSource().getEntity() instanceof DeepGuardian) {
            event.setCanceled(true);
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

        // Deep Guardian trident: fixed 10 damage, +20% when 5+ Deep Guardians are nearby.
        if (weapon.is(ModItems.DEEP_GUARDIAN_TRIDENT.get())) {
            float amount = DEEP_GUARDIAN_THROWN_DAMAGE;
            Entity owner = source.getEntity();
            if (owner instanceof DeepGuardian guardian) {
                int nearby = guardian.level().getEntitiesOfClass(
                        DeepGuardian.class,
                        guardian.getBoundingBox().inflate(PACK_BONUS_RADIUS),
                        e -> e != guardian && e.isAlive()).size();
                if (nearby >= PACK_BONUS_COUNT) {
                    amount *= PACK_BONUS_MULTIPLIER;
                }
                if (guardian.isInMourning()) {
                    amount *= MOURNING_DAMAGE_MULTIPLIER;
                }
            }
            event.setAmount(amount);
            return;
        }

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
