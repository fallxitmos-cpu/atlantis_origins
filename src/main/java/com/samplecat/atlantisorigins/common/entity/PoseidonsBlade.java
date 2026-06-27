package com.samplecat.atlantisorigins.common.entity;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PoseidonsBlade extends DeepGuardian implements GeoEntity {

    // Model is 92 Blockbench units tall (5.75 blocks), with 14 units (0.875 blocks)
    // below the ground plane. Keep the DeepGuardian width/depth, only adjust Y.
    private static final Vec3 BODY_BOX_CENTER_OFFSET = new Vec3(0.0, 2.0, 0.0);
    private static final double BODY_BOX_WIDTH = 2.5D;
    private static final double BODY_BOX_HEIGHT = 5.75D;
    private static final double BODY_BOX_DEPTH = 2.5D;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public PoseidonsBlade(EntityType<? extends Guardian> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected AABB makeBoundingBox() {
        return EntityHitboxHelper.makeBox(this, BODY_BOX_CENTER_OFFSET,
                BODY_BOX_WIDTH, BODY_BOX_HEIGHT, BODY_BOX_DEPTH);
  }

    // Defense equivalent to a full set of diamond armor:
    // helmet 3 + chestplate 6 + leggings 5 + boots 3 = 17 armor, 0 toughness.
    public static AttributeSupplier.Builder createAttributes() {
        return Guardian.createAttributes()
                .add(Attributes.MAX_HEALTH, 300.0)
                .add(Attributes.ATTACK_DAMAGE, 12.0)
                .add(Attributes.ARMOR, 17.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    @Override
    protected void registerGoals() {
        // Do not call super.registerGoals() - DeepGuardian's goals are replaced entirely below.
        this.goalSelector.removeAllGoals(goal -> true);
        this.targetSelector.removeAllGoals(goal -> true);

        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));

        // Attack all living entities except itself and other Poseidons Blades.
        // Hostile mobs from this mod are excluded; all other mobs (vanilla/other-mod
        // hostiles, neutrals, passives, players) are valid targets.
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true,
                target -> target != this
                        && !(target instanceof PoseidonsBlade)
                        && !isAtlantisOriginsHostile(target)));
    }

    private static boolean isAtlantisOriginsHostile(LivingEntity target) {
        if (!BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()).getNamespace().equals(AtlantisOrigins.MOD_ID)) {
            return false;
        }
        return target instanceof Monster;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, spawnData);
        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        return result;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        // No ranged attack; this is a melee entity.
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main", 0, state -> PlayState.STOP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
