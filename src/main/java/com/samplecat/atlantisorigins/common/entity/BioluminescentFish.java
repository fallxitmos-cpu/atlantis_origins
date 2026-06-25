package com.samplecat.atlantisorigins.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BioluminescentFish extends AbstractSchoolingFish {

    public static final int ANIMATION_SWIM = 0;
    public static final int ANIMATION_HIT = 1;
    public static final int ANIMATION_FLEE = 2;

    private static final EntityDataAccessor<Integer> DATA_ANIMATION_STATE = SynchedEntityData.defineId(
            BioluminescentFish.class, EntityDataSerializers.INT);

    // The model is ~13 px (~0.8 blocks) long. Use an oriented box that follows yaw.
    private static final Vec3 BODY_CENTER_OFFSET = new Vec3(0.0, 0.1, 0.0);
    private static final double BODY_LENGTH = 0.9D;
    private static final double BODY_WIDTH = 0.15D;
    private static final double BODY_HEIGHT = 0.35D;

    private int fleeTicks = 0;

    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState hitAnimationState = new AnimationState();
    public final AnimationState fleeAnimationState = new AnimationState();

    public BioluminescentFish(EntityType<? extends AbstractSchoolingFish> entityType, Level level) {
        super(entityType, level);
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
        compound.putInt("FleeTicks", this.fleeTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("AnimationState")) {
            this.entityData.set(DATA_ANIMATION_STATE, compound.getInt("AnimationState"));
        }
        if (compound.contains("FleeTicks")) {
            this.fleeTicks = compound.getInt("FleeTicks");
        }
    }

    @Override
    protected AABB makeBoundingBox() {
        return EntityHitboxHelper.makeOrientedBox(this, BODY_CENTER_OFFSET,
                BODY_LENGTH, BODY_WIDTH, BODY_HEIGHT);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.updateAnimationStates();
        } else {
            int state = this.getAnimationState();
            if (state == ANIMATION_HIT && this.fleeTicks <= 55) {
                this.setAnimationState(ANIMATION_FLEE);
            }
            if (this.fleeTicks > 0) {
                this.fleeTicks--;
                if (this.fleeTicks <= 0) {
                    this.setAnimationState(ANIMATION_SWIM);
                }
            }
        }
    }

    private void updateAnimationStates() {
        switch (this.getAnimationState()) {
            case ANIMATION_HIT -> {
                this.hitAnimationState.startIfStopped(this.tickCount);
                this.swimAnimationState.stop();
                this.fleeAnimationState.stop();
            }
            case ANIMATION_FLEE -> {
                this.fleeAnimationState.startIfStopped(this.tickCount);
                this.swimAnimationState.stop();
                this.hitAnimationState.stop();
            }
            default -> {
                this.swimAnimationState.startIfStopped(this.tickCount);
                this.hitAnimationState.stop();
                this.fleeAnimationState.stop();
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (result && !this.level().isClientSide()) {
            this.setAnimationState(ANIMATION_HIT);
            this.fleeTicks = 60; // hit ~0.25s + flee ~2.75s
        }
        return result;
    }

    public int getAnimationState() {
        return this.entityData.get(DATA_ANIMATION_STATE);
    }

    public void setAnimationState(int state) {
        this.entityData.set(DATA_ANIMATION_STATE, state);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public int getMaxSchoolSize() {
        return 12;
    }
}
