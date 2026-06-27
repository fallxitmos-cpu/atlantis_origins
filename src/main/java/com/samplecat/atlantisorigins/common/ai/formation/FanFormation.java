package com.samplecat.atlantisorigins.common.ai.formation;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Arranges squad members in a fan/arc around the target.
 *
 * <p>Members are placed at a fixed radius from the target and spread out
 * perpendicularly, creating a classic ranged firing line.</p>
 */
public class FanFormation implements Formation {

    private static final double RADIUS = 10.0D;
    private static final double SPACING = 3.0D;

    @Override
    public Vec3 getPosition(LivingEntity target, int index, int count, @Nullable Vec3 forwardHint) {
        Vec3 targetPos = target.position();
        double lateralOffset = (index - (count - 1) / 2.0D) * SPACING;

        Vec3 forward;
        if (forwardHint != null && forwardHint.lengthSqr() > 0.0001D) {
            forward = forwardHint.normalize();
        } else {
            forward = new Vec3(0.0D, 0.0D, 1.0D);
        }

        Vec3 perpendicular = new Vec3(-forward.z, 0.0D, forward.x).normalize();
        return targetPos.add(forward.scale(RADIUS)).add(perpendicular.scale(lateralOffset));
    }
}
