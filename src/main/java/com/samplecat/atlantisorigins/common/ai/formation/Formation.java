package com.samplecat.atlantisorigins.common.ai.formation;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Describes how a squad member should position itself relative to a target.
 */
public interface Formation {

    /**
     * @param target      the squad's current target
     * @param index       this member's stable index in the squad
     * @param count       total number of members in the squad
     * @param forwardHint optional preferred forward direction; may be null
     * @return the desired world position for this member
     */
    Vec3 getPosition(LivingEntity target, int index, int count, @Nullable Vec3 forwardHint);
}
