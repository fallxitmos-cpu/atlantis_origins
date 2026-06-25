package com.samplecat.atlantisorigins.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GazingJellyfish extends Squid {
    private static final float GAZE_RANGE = 24.0F;
    private static final int REVELATION_TIME = 200; // 10 seconds

    private int gazeTicks = 0;

    // Vanilla squid-like proportions: ~0.8 blocks wide/deep, ~1.5 blocks tall.
    private static final Vec3 BODY_CENTER_OFFSET = new Vec3(0.0, 0.75, 0.0);
    private static final double BODY_WIDTH = 0.8D;
    private static final double BODY_HEIGHT = 1.5D;
    private static final double BODY_DEPTH = 0.8D;

    public GazingJellyfish(EntityType<? extends Squid> entityType, Level level) {
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
    public void tick() {
        super.tick();
        if (this.level().isClientSide() || !this.isAlive()) {
            return;
        }

        AABB box = this.getBoundingBox().inflate(GAZE_RANGE);
        List<Player> players = this.level().getEntitiesOfClass(Player.class, box);
        boolean beingWatched = false;
        for (Player player : players) {
            if (player.hasLineOfSight(this)) {
                beingWatched = true;
                break;
            }
        }

        if (beingWatched) {
            this.gazeTicks++;
            if (this.gazeTicks >= REVELATION_TIME) {
                for (Player player : players) {
                    if (player.hasLineOfSight(this)) {
                        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0));
                    }
                }
                this.gazeTicks = 0;
            }
        } else {
            this.gazeTicks = Math.max(0, this.gazeTicks - 1);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("GazeTicks", this.gazeTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("GazeTicks")) {
            this.gazeTicks = compound.getInt("GazeTicks");
        }
    }
}
