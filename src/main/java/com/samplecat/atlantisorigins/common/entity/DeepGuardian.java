package com.samplecat.atlantisorigins.common.entity;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.ai.squad.SquadManager;
import com.samplecat.atlantisorigins.common.block.CaptainGuardianLightBlock;
import com.samplecat.atlantisorigins.common.block.ModBlocks;
import com.samplecat.atlantisorigins.common.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class DeepGuardian extends Guardian implements RangedAttackMob {

    // The model root is positioned so the head sits at the entity origin.
    // The hitbox is enlarged to cover the head + upper body and follows yaw.
    private static final Vec3 BODY_BOX_CENTER_OFFSET = new Vec3(0.0, 0.75, 0.0);
    private static final double BODY_BOX_WIDTH = 2.5D;
    private static final double BODY_BOX_HEIGHT = 1.5D;
    private static final double BODY_BOX_DEPTH = 2.5D;

    public static final int ANIMATION_IDLE = 0;
    public static final int ANIMATION_ATTACK = 1;
    public static final int ANIMATION_SWIM = 2;

    private static final EntityDataAccessor<Integer> DATA_ANIMATION_STATE = SynchedEntityData.defineId(
            DeepGuardian.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_CAPTAIN = SynchedEntityData.defineId(
            DeepGuardian.class, EntityDataSerializers.BOOLEAN);

    private static final String NBT_ANIMATION_STATE = "AnimationState";
    private static final String NBT_CAPTAIN = "DeepGuardianCaptain";
    private static final String NBT_TEAM_ID = "DeepGuardianTeamId";
    private static final String NBT_CAPTAIN_UUID = "DeepGuardianCaptainUUID";
    private static final String NBT_MOURNING_UNTIL = "DeepGuardianMourningUntil";

    private static final double REINFORCEMENT_RADIUS = 32.0D;

    private static final ResourceLocation MOURNING_SPEED_ID = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "deep_guardian_mourning_speed");
    private static final ResourceLocation MOURNING_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "deep_guardian_mourning_damage");

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();

    private int attackAnimationTicks;
    private LivingEntity pendingThrowTarget;

    @Nullable
    private UUID teamId;
    @Nullable
    private UUID captainUUID;
    private long mourningUntilTick;
    @Nullable
    private BlockPos captainLightPos;

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
        builder.define(DATA_CAPTAIN, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(NBT_ANIMATION_STATE, this.entityData.get(DATA_ANIMATION_STATE));
        compound.putBoolean(NBT_CAPTAIN, this.isCaptain());
        if (this.teamId != null) {
            compound.putUUID(NBT_TEAM_ID, this.teamId);
        }
        if (this.captainUUID != null) {
            compound.putUUID(NBT_CAPTAIN_UUID, this.captainUUID);
        }
        compound.putLong(NBT_MOURNING_UNTIL, this.mourningUntilTick);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains(NBT_ANIMATION_STATE)) {
            this.entityData.set(DATA_ANIMATION_STATE, compound.getInt(NBT_ANIMATION_STATE));
        }
        this.setCaptain(compound.getBoolean(NBT_CAPTAIN));
        if (compound.hasUUID(NBT_TEAM_ID)) {
            this.teamId = compound.getUUID(NBT_TEAM_ID);
        }
        if (compound.hasUUID(NBT_CAPTAIN_UUID)) {
            this.captainUUID = compound.getUUID(NBT_CAPTAIN_UUID);
        }
        this.mourningUntilTick = compound.getLong(NBT_MOURNING_UNTIL);
        this.updateMourningModifiers();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, spawnData);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.DEEP_GUARDIAN_TRIDENT.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        return result;
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (target == null || this.level().isClientSide()) {
            return;
        }
        // Call all nearby Deep Guardians to attack the same target.
        AABB box = this.getBoundingBox().inflate(REINFORCEMENT_RADIUS);
        for (DeepGuardian ally : this.level().getEntitiesOfClass(DeepGuardian.class, box, e -> e != this && e.isAlive())) {
            if (ally.getTarget() == null || ally.getTarget() != target) {
                ally.setTarget(target);
            }
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new com.samplecat.atlantisorigins.common.ai.goals.SquadAttackGoal(this));
        this.goalSelector.addGoal(3, new com.samplecat.atlantisorigins.common.ai.goals.FollowSquadLeaderGoal(this));

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

    private int squadAttackCooldown;

    public int getSquadAttackCooldown() {
        return this.squadAttackCooldown;
    }

    public void setSquadAttackCooldown(int ticks) {
        this.squadAttackCooldown = ticks;
    }

    private void tickSquadAttackCooldown() {
        if (this.squadAttackCooldown > 0) {
            this.squadAttackCooldown--;
        }
    }

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

        this.tickSquadAttackCooldown();

        if (!this.level().isClientSide()) {
            SquadManager.tick(this);
            if (this.isCaptain()) {
                this.updateCaptainLightBlock();
            }
        }

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

    // -------------------------------------------------------------------------
    // Squad state accessors
    // -------------------------------------------------------------------------

    public boolean isCaptain() {
        return this.entityData.get(DATA_CAPTAIN);
    }

    public void setCaptain(boolean captain) {
        this.entityData.set(DATA_CAPTAIN, captain);
        if (!this.level().isClientSide()) {
            this.setGlowingTag(captain);
        }
    }

    @Nullable
    public UUID getTeamId() {
        return this.teamId;
    }

    public void setTeamId(@Nullable UUID teamId) {
        this.teamId = teamId;
    }

    @Nullable
    public UUID getCaptainUUID() {
        return this.captainUUID;
    }

    public void setCaptainUUID(@Nullable UUID captainUUID) {
        this.captainUUID = captainUUID;
    }

    public long getMourningUntilTick() {
        return this.mourningUntilTick;
    }

    public void setMourningUntilTick(long mourningUntilTick) {
        this.mourningUntilTick = mourningUntilTick;
        this.updateMourningModifiers();
    }

    public boolean isInMourning() {
        return !this.level().isClientSide() && this.level().getGameTime() < this.mourningUntilTick;
    }

    public boolean hasCaptain() {
        return this.captainUUID != null;
    }

    @Nullable
    public DeepGuardian getCaptainEntity() {
        if (this.captainUUID == null || this.level().isClientSide()) {
            return null;
        }
        if (this.isCaptain() && this.getUUID().equals(this.captainUUID)) {
            return this;
        }
        List<DeepGuardian> captains = this.level().getEntitiesOfClass(DeepGuardian.class,
                this.getBoundingBox().inflate(SquadManager.CAPTAIN_EXISTENCE_RADIUS),
                e -> e.isAlive() && e.isCaptain() && e.getUUID().equals(this.captainUUID));
        return captains.isEmpty() ? null : captains.getFirst();
    }

    public void clearMourning() {
        this.mourningUntilTick = 0;
        this.updateMourningModifiers();
    }

    public void updateMourningModifiers() {
        if (this.level().isClientSide()) {
            return;
        }
        boolean mourning = this.isInMourning();
        this.applyModifier(Attributes.MOVEMENT_SPEED, MOURNING_SPEED_ID, 0.1D,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE, mourning);
        this.applyModifier(Attributes.ATTACK_DAMAGE, MOURNING_DAMAGE_ID, 0.1D,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE, mourning);
    }

    private void applyModifier(Holder<Attribute> attribute, ResourceLocation id, double amount,
                               AttributeModifier.Operation operation, boolean add) {
        AttributeInstance instance = this.getAttribute(attribute);
        if (instance == null) {
            return;
        }
        boolean has = instance.getModifier(id) != null;
        if (add && !has) {
            instance.addPermanentModifier(new AttributeModifier(id, amount, operation));
        } else if (!add && has) {
            instance.removeModifier(id);
        }
    }

    // -------------------------------------------------------------------------
    // Captain light block
    // -------------------------------------------------------------------------

    private void updateCaptainLightBlock() {
        if (this.level().isClientSide()) {
            return;
        }
        BlockPos pos = this.blockPosition();
        // Remove the previous tick's light block so we can place a fresh one.
        this.removeCaptainLightBlock();
        if (this.canPlaceCaptainLight(pos)) {
            boolean waterlogged = this.level().getFluidState(pos).is(Fluids.WATER);
            BlockState state = ModBlocks.CAPTAIN_GUARDIAN_LIGHT.get().defaultBlockState()
                    .setValue(CaptainGuardianLightBlock.WATERLOGGED, waterlogged);
            this.level().setBlock(pos, state, Block.UPDATE_ALL);
            this.captainLightPos = pos;
        } else {
            this.captainLightPos = null;
        }
    }

    public void removeCaptainLightBlock() {
        if (this.level().isClientSide() || this.captainLightPos == null) {
            return;
        }
        BlockState state = this.level().getBlockState(this.captainLightPos);
        if (state.is(ModBlocks.CAPTAIN_GUARDIAN_LIGHT.get())) {
            if (state.getValue(CaptainGuardianLightBlock.WATERLOGGED)) {
                this.level().setBlock(this.captainLightPos, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL);
            } else {
                this.level().removeBlock(this.captainLightPos, false);
            }
        }
        this.captainLightPos = null;
    }

    private boolean canPlaceCaptainLight(BlockPos pos) {
        BlockState state = this.level().getBlockState(pos);
        return state.isAir()
                || state.is(Blocks.WATER)
                || state.is(ModBlocks.CAPTAIN_GUARDIAN_LIGHT.get());
    }
}
