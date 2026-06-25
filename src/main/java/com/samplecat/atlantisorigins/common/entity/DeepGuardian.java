package com.samplecat.atlantisorigins.common.entity;

import com.samplecat.atlantisorigins.common.item.ModItems;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DeepGuardian extends Guardian implements RangedAttackMob {

    // The model root is positioned so the head sits at the entity origin.
    // The hitbox is enlarged to cover the head + upper body and follows yaw.
    private static final Vec3 BODY_BOX_CENTER_OFFSET = new Vec3(0.0, 0.75, -0.75);
    private static final double BODY_BOX_WIDTH = 2.0D;
    private static final double BODY_BOX_HEIGHT = 1.5D;
    private static final double BODY_BOX_DEPTH = 2.5D;

    public static final int ANIMATION_IDLE = 0;
    public static final int ANIMATION_ATTACK = 1;
    public static final int ANIMATION_SWIM = 2;

    private static final EntityDataAccessor<Integer> DATA_ANIMATION_STATE = SynchedEntityData.defineId(
            DeepGuardian.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();

    private int attackAnimationTicks;
    private LivingEntity pendingThrowTarget;

    public DeepGuardian(EntityType<? extends Guardian> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected AABB makeBoundingBox() {
        // Entity origin stays at the head; the box extends backward to cover
        // the torso and arms, and rotates with the guardian's yaw.
        return EntityHitboxHelper.makeBox(this, BODY_BOX_CENTER_OFFSET,
                BODY_BOX_WIDTH, BODY_BOX_HEIGHT, BODY_BOX_DEPTH);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ANIMATION_STATE, ANIMATION_IDLE);
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, spawnData);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.DEEP_GUARDIAN_TRIDENT.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.05F);
        return result;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new DeepGuardianTridentAttackGoal(this, 1.0D, 40, 10.0F));

        // Replace Guardian's default targeting (players/squids) with indiscriminate hostility
        // toward every living entity except other Deep Guardians.
        this.targetSelector.removeAllGoals(goal -> true);
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(
                this,
                LivingEntity.class,
                true,
                target -> target != this
                        && !(target instanceof DeepGuardian)
                        && (!(target instanceof Player player) || (!player.isCreative() && !player.isSpectator()))
        ));
    }

    private static final int ATTACK_ANIMATION_LENGTH = 20;
    private static final int TRIDENT_THROW_OFFSET = 6;

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        // Start the animation and remember the target; the trident is thrown a few
        // ticks before the animation ends so the visual release lines up.
        this.setAnimationState(ANIMATION_ATTACK);
        this.attackAnimationTicks = 0;
        this.pendingThrowTarget = target;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getAnimationState() == ANIMATION_ATTACK) {
            this.attackAnimationTicks++;

            // Throw the trident 3 ticks before the animation finishes.
            if (this.attackAnimationTicks == ATTACK_ANIMATION_LENGTH - TRIDENT_THROW_OFFSET) {
                this.throwTridentAtPendingTarget();
            }

            if (this.attackAnimationTicks >= ATTACK_ANIMATION_LENGTH) {
                this.setAnimationState(ANIMATION_IDLE);
                this.attackAnimationTicks = 0;
            }
        } else {
            this.attackAnimationTicks = 0;
        }

        if (this.level().isClientSide()) {
            this.updateAnimationStates();
        }
    }

    private void throwTridentAtPendingTarget() {
        LivingEntity target = this.pendingThrowTarget;
        this.pendingThrowTarget = null;

        if (target == null || !target.isAlive() || this.level().isClientSide()) {
            return;
        }

        ThrownTrident thrownTrident = new ThrownTrident(this.level(), this, new ItemStack(ModItems.DEEP_GUARDIAN_TRIDENT.get()));
        double dx = target.getX() - this.getX();
        double dy = target.getY(0.3333333333333333D) - thrownTrident.getY();
        double dz = target.getZ() - this.getZ();
        double horizontalDist = Math.sqrt(dx * dx + dz * dz);
        thrownTrident.shoot(dx, dy + horizontalDist * 0.2D, dz, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(thrownTrident);
    }

    private void updateAnimationStates() {
        switch (this.getAnimationState()) {
            case ANIMATION_ATTACK -> {
                this.attackAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationState.stop();
                this.swimAnimationState.stop();
            }
            case ANIMATION_SWIM -> {
                this.swimAnimationState.startIfStopped(this.tickCount);
                this.idleAnimationState.stop();
                this.attackAnimationState.stop();
            }
            default -> {
                this.idleAnimationState.startIfStopped(this.tickCount);
                this.attackAnimationState.stop();
                this.swimAnimationState.stop();
            }
        }
    }

    public int getAnimationState() {
        return this.entityData.get(DATA_ANIMATION_STATE);
    }

    public void setAnimationState(int state) {
        this.entityData.set(DATA_ANIMATION_STATE, state);
    }

    public static class DeepGuardianTridentAttackGoal extends RangedAttackGoal {
        private final DeepGuardian guardian;

        public DeepGuardianTridentAttackGoal(DeepGuardian guardian, double speed, int attackInterval, float attackRadius) {
            super(guardian, speed, attackInterval, attackRadius);
            this.guardian = guardian;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.guardian.getMainHandItem().is(ModItems.DEEP_GUARDIAN_TRIDENT.get());
        }
    }
}
