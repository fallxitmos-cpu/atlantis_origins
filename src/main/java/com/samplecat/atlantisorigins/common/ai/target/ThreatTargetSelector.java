package com.samplecat.atlantisorigins.common.ai.target;

import net.minecraft.world.entity.LivingEntity;

/**
 * Evaluates how threatening a target is for squad target selection.
 *
 * <p>The score combines maximum health and armor value. Armor is weighted
 * heavily because each point blocks a noticeable portion of incoming damage.</p>
 */
public final class ThreatTargetSelector {

    private static final double ARMOR_WEIGHT = 5.0D;

    private ThreatTargetSelector() {
    }

    public static double computeThreat(LivingEntity target) {
        return target.getMaxHealth() + target.getArmorValue() * ARMOR_WEIGHT;
    }
}
