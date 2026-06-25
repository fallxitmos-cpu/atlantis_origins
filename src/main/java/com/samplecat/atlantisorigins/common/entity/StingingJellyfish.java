package com.samplecat.atlantisorigins.common.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class StingingJellyfish extends Squid {

    // Vanilla squid-like proportions: ~0.8 blocks wide/deep, ~1.5 blocks tall.
    private static final Vec3 BODY_CENTER_OFFSET = new Vec3(0.0, 0.75, 0.0);
    private static final double BODY_WIDTH = 0.8D;
    private static final double BODY_HEIGHT = 1.5D;
    private static final double BODY_DEPTH = 0.8D;
    public StingingJellyfish(EntityType<? extends Squid> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected AABB makeBoundingBox() {
        return EntityHitboxHelper.makeBox(this, BODY_CENTER_OFFSET,
                BODY_WIDTH, BODY_HEIGHT, BODY_DEPTH);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (!this.level().isClientSide() && this.isAlive()) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 0));
        }
    }
}
