package com.samplecat.atlantisorigins.common.ai.formation;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Arranges squad members in a square grid centered on the target.
 *
 * <p>Used for larger squads where a simple fan would spread too wide.</p>
 */
public class SquareFormation implements Formation {

    private static final double SPACING = 4.0D;

    @Override
    public Vec3 getPosition(LivingEntity target, int index, int count, @Nullable Vec3 forwardHint) {
        int gridSize = (int) Math.ceil(Math.sqrt(count));
        int gx = index % gridSize;
        int gz = index / gridSize;
        double offsetX = (gx - (gridSize - 1) / 2.0D) * SPACING;
        double offsetZ = (gz - (gridSize - 1) / 2.0D) * SPACING;
        return target.position().add(offsetX, 0.0D, offsetZ);
    }
}
