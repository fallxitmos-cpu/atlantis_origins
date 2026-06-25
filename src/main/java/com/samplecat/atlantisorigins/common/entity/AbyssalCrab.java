package com.samplecat.atlantisorigins.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Wrapper around vanilla Axolotl so the mod controls its class/AI/hitbox.
 */
public class AbyssalCrab extends Axolotl {

    // The axolotl model is horizontal: ~0.7 blocks long, ~0.25 wide, ~0.3 tall.
    private static final Vec3 BODY_CENTER_OFFSET = new Vec3(0.0, 0.15, 0.0);
    private static final double BODY_LENGTH = 0.7D;
    private static final double BODY_WIDTH = 0.25D;
    private static final double BODY_HEIGHT = 0.3D;

    public AbyssalCrab(EntityType<? extends Axolotl> entityType, Level level) {
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
