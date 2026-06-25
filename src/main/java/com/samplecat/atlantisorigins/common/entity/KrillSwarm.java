package com.samplecat.atlantisorigins.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Wrapper around vanilla Cod so the mod controls its class/AI/hitbox.
 */
public class KrillSwarm extends Cod {

    // Cod-like model: ~0.6 blocks long, ~0.15 wide, ~0.3 tall.
    private static final Vec3 BODY_CENTER_OFFSET = new Vec3(0.0, 0.15, 0.0);
    private static final double BODY_LENGTH = 0.6D;
    private static final double BODY_WIDTH = 0.15D;
    private static final double BODY_HEIGHT = 0.3D;

    public KrillSwarm(EntityType<? extends Cod> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected AABB makeBoundingBox() {
        return EntityHitboxHelper.makeOrientedBox(this, BODY_CENTER_OFFSET,
                BODY_LENGTH, BODY_WIDTH, BODY_HEIGHT);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }
}
