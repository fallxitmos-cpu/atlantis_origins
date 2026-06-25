package com.samplecat.atlantisorigins.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Wrapper around vanilla Pufferfish so the mod controls its class/AI/hitbox.
 */
public class Gulper extends Pufferfish {

    // Pufferfish-like body: roughly cubic, ~0.6 blocks in each axis.
    private static final Vec3 BODY_CENTER_OFFSET = new Vec3(0.0, 0.3, 0.0);
    private static final double BODY_SIZE = 0.6D;

    public Gulper(EntityType<? extends Pufferfish> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected AABB makeBoundingBox() {
        // Length == width == height, so an oriented box is not necessary,
        // but using makeBox still gives us a centered, model-sized collision box.
        return EntityHitboxHelper.makeBox(this, BODY_CENTER_OFFSET,
                BODY_SIZE, BODY_SIZE, BODY_SIZE);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }
}
