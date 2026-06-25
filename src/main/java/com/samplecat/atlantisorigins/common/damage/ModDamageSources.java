package com.samplecat.atlantisorigins.common.damage;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ModDamageSources {

    public static DamageSource evolution(Level level, Entity entity) {
        return new DamageSource(
                level.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(ModDamageTypes.EVOLUTION),
                entity);
    }
}
