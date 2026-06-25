package com.samplecat.atlantisorigins.common.entity;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class AbyssalJellyfish extends Squid {

    public static final int VARIANT_WHITE = 0;
    public static final int VARIANT_GOLD = 1;
    public static final int VARIANT_BLUE = 2;
    public static final int VARIANT_RED = 3;

    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(
            AbyssalJellyfish.class, EntityDataSerializers.INT);

    // The jellyfish is roughly 0.9 blocks wide/deep and 2.0 blocks tall (bell + tentacles).
    // It is rendered upright, so a plain yaw-rotated AABB is sufficient.
    private static final Vec3 BODY_CENTER_OFFSET = new Vec3(0.0, 1.0, 0.0);
    private static final double BODY_WIDTH = 0.9D;
    private static final double BODY_HEIGHT = 2.0D;
    private static final double BODY_DEPTH = 0.9D;

    // Bypass Squid#hurt() so this creature does not spray squid ink when damaged.
    // unreflectSpecial is required to invoke LivingEntity#hurt without dynamic dispatch,
    // otherwise the overridden hurt() in this class would be called recursively.
    private static final MethodHandle LIVING_ENTITY_HURT;

    static {
        MethodHandle handle = null;
        try {
            Method method = LivingEntity.class.getDeclaredMethod("hurt", DamageSource.class, float.class);
            handle = MethodHandles.lookup().unreflectSpecial(method, AbyssalJellyfish.class);
        } catch (Exception e) {
            AtlantisOrigins.LOGGER.error("Failed to set up AbyssalJellyfish hurt bypass, falling back to Squid#hurt", e);
        }
        LIVING_ENTITY_HURT = handle;
    }

    public AbyssalJellyfish(EntityType<? extends Squid> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT, VARIANT_WHITE);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, spawnData);
        this.setVariant(this.random.nextInt(4));
        return result;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Variant")) {
            this.setVariant(compound.getInt("Variant"));
        }
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    @Override
    protected AABB makeBoundingBox() {
        return EntityHitboxHelper.makeBox(this, BODY_CENTER_OFFSET,
                BODY_WIDTH, BODY_HEIGHT, BODY_DEPTH);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (LIVING_ENTITY_HURT == null) {
            return super.hurt(source, amount);
        }
        try {
            return (boolean) LIVING_ENTITY_HURT.invoke(this, source, amount);
        } catch (Throwable e) {
            AtlantisOrigins.LOGGER.error("Failed to invoke LivingEntity#hurt for AbyssalJellyfish", e);
            return super.hurt(source, amount);
        }
    }
}
