package com.samplecat.atlantisorigins.common.ai.goals;

import com.samplecat.atlantisorigins.common.ai.formation.FanFormation;
import com.samplecat.atlantisorigins.common.ai.formation.Formation;
import com.samplecat.atlantisorigins.common.ai.formation.SquareFormation;
import com.samplecat.atlantisorigins.common.ai.squad.SquadManager;
import com.samplecat.atlantisorigins.common.ai.steering.SeparationSteering;
import com.samplecat.atlantisorigins.common.entity.DeepGuardian;
import com.samplecat.atlantisorigins.common.item.ModItems;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

/**
 * Ranged attack goal for Deep Guardians.
 *
 * <p>Solo guardians and captains strafe around their target. Squad members move
 * to a fan or square formation around the target. In all cases a separation
 * force keeps guardians from stacking or spinning in place.</p>
 */
public class SquadAttackGoal extends Goal {

    private static final double IDEAL_MIN_RADIUS = 7.5D;
    private static final double IDEAL_MAX_RADIUS = 13.5D;
    private static final double STRAFE_RADIUS = 10.5D;
    private static final double STRAFE_ANGULAR_SPEED = 0.02D;
    private static final float ATTACK_RADIUS = 15.0F;
    private static final float ATTACK_RADIUS_SQR = ATTACK_RADIUS * ATTACK_RADIUS;
    private static final int ATTACK_INTERVAL_MIN = 30;
    private static final int ATTACK_INTERVAL_MAX = 50;
    private static final int SQUAD_COOLDOWN_MIN = 10;
    private static final int SQUAD_COOLDOWN_MAX = 20;
    private static final double SQUAD_COOLDOWN_RADIUS = 16.0D;

    private static final double SEPARATION_RADIUS = 4.0D;
    private static final double MIN_SEPARATION = 2.5D;
    private static final double SEPARATION_STRENGTH = 2.0D;

    private static final Formation FAN_FORMATION = new FanFormation();
    private static final Formation SQUARE_FORMATION = new SquareFormation();

    private final DeepGuardian guardian;
    private LivingEntity target;
    private int attackTime = -1;
    private int strafeDirection;

    public SquadAttackGoal(DeepGuardian guardian) {
        this.guardian = guardian;
        this.strafeDirection = guardian.getRandom().nextBoolean() ? 1 : -1;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.guardian.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (!this.guardian.getMainHandItem().is(ModItems.DEEP_GUARDIAN_TRIDENT.get())) {
            return false;
        }
        this.target = target;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse() || (this.target != null && this.target.isAlive());
    }

    @Override
    public void stop() {
        this.target = null;
        this.attackTime = -1;
        this.guardian.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.target == null || !this.target.isAlive()) {
            return;
        }

        this.guardian.getLookControl().setLookAt(this.target, 15.0F, 30.0F);

        Vec3 targetPos = this.target.position();
        Vec3 selfPos = this.guardian.position();
        Vec3 toSelf = selfPos.subtract(targetPos);
        double horizontalDist = Math.sqrt(toSelf.x * toSelf.x + toSelf.z * toSelf.z);
        double distSqr = selfPos.distanceToSqr(targetPos);
        boolean hasLineOfSight = this.guardian.getSensing().hasLineOfSight(this.target);

        Vec3 desired = this.computeDesiredPosition(targetPos, toSelf, horizontalDist);
        Vec3 separation = SeparationSteering.compute(this.guardian, SEPARATION_RADIUS, MIN_SEPARATION, SEPARATION_STRENGTH);
        if (separation.lengthSqr() > 0.0001D) {
            desired = desired.add(separation);
        }

        if (desired.distanceToSqr(selfPos) > 1.0D) {
            this.guardian.getNavigation().moveTo(desired.x, desired.y, desired.z, 1.0D);
        } else {
            this.guardian.getNavigation().stop();
        }

        if (this.attackTime <= 0
                && hasLineOfSight
                && distSqr <= ATTACK_RADIUS_SQR
                && this.guardian.getSquadAttackCooldown() <= 0) {
            this.guardian.performRangedAttack(this.target, 1.0F);
            this.applySquadCooldown();
            this.attackTime = Mth.floor(Mth.lerp(
                    Math.sqrt(distSqr) / (double) ATTACK_RADIUS,
                    ATTACK_INTERVAL_MIN, ATTACK_INTERVAL_MAX));
        }

        if (this.attackTime > 0) {
            this.attackTime--;
        }
    }

    private Vec3 computeDesiredPosition(Vec3 targetPos, Vec3 toSelf, double horizontalDist) {
        DeepGuardian captain = this.guardian.getCaptainEntity();
        if (captain != null && !this.guardian.isCaptain()) {
            List<DeepGuardian> members = SquadManager.getSquadMembers(this.guardian,
                    SquadManager.CAPTAIN_EXISTENCE_RADIUS, false);
            if (!members.isEmpty()) {
                int index = members.indexOf(this.guardian);
                int count = members.size();
                if (index >= 0) {
                    Formation formation = count <= 5 ? FAN_FORMATION : SQUARE_FORMATION;
                    Vec3 forwardHint = captain.position().subtract(targetPos);
                    if (forwardHint.lengthSqr() < 0.0001D) {
                        forwardHint = this.guardian.position().subtract(targetPos);
                    }
                    return formation.getPosition(this.target, index, count, forwardHint.normalize());
                }
            }
        }

        if (horizontalDist > IDEAL_MAX_RADIUS || horizontalDist < 0.5D) {
            return targetPos;
        } else if (horizontalDist < IDEAL_MIN_RADIUS) {
            Vec3 away = toSelf.normalize().scale(STRAFE_RADIUS);
            return targetPos.add(away);
        } else {
            double currentAngle = Math.atan2(toSelf.z, toSelf.x);
            double newAngle = currentAngle + this.strafeDirection * STRAFE_ANGULAR_SPEED;
            Vec3 desired = targetPos.add(
                    Math.cos(newAngle) * STRAFE_RADIUS,
                    0.0D,
                    Math.sin(newAngle) * STRAFE_RADIUS);
            return new Vec3(desired.x, targetPos.y, desired.z);
        }
    }

    private void applySquadCooldown() {
        AABB box = this.guardian.getBoundingBox().inflate(SQUAD_COOLDOWN_RADIUS);
        int cooldown = this.guardian.getRandom().nextIntBetweenInclusive(SQUAD_COOLDOWN_MIN, SQUAD_COOLDOWN_MAX);
        for (DeepGuardian ally : this.guardian.level().getEntitiesOfClass(DeepGuardian.class, box,
                e -> e != this.guardian && e.isAlive())) {
            if (ally.getSquadAttackCooldown() < cooldown) {
                ally.setSquadAttackCooldown(cooldown);
            }
        }
    }
}
