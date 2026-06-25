package com.samplecat.atlantisorigins.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 深海鳗鱼。继承 Cod 以复用原版鳕鱼 AI 与行为，使用自定义模型/纹理/动画。
 */
public class DeepEel extends Cod {

    // Model is ~44 px (2.75 blocks) long and only ~3 px (0.2 blocks) tall/wide.
    // Use an oriented AABB so the hitbox follows the eel's yaw.
    private static final Vec3 BODY_CENTER_OFFSET = new Vec3(0.0, 0.1, 0.2);
    private static final double BODY_LENGTH = 2.75D;
    private static final double BODY_WIDTH = 0.2D;
    private static final double BODY_HEIGHT = 0.2D;

    public static final int ANIMATION_SWIM = 0;

    private static final EntityDataAccessor<Integer> DATA_ANIMATION_STATE = SynchedEntityData.defineId(
            DeepEel.class, EntityDataSerializers.INT);

    public final AnimationState swimAnimationState = new AnimationState();

    public DeepEel(EntityType<? extends Cod> entityType, Level level) {
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

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ANIMATION_STATE, ANIMATION_SWIM);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("AnimationState", this.entityData.get(DATA_ANIMATION_STATE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("AnimationState")) {
            this.entityData.set(DATA_ANIMATION_STATE, compound.getInt("AnimationState"));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.updateAnimationStates();
        }
    }

    private void updateAnimationStates() {
        this.swimAnimationState.startIfStopped(this.tickCount);
    }

    public int getAnimationState() {
        return this.entityData.get(DATA_ANIMATION_STATE);
    }

    public void setAnimationState(int state) {
        this.entityData.set(DATA_ANIMATION_STATE, state);
    }
}
