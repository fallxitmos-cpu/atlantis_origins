package com.samplecat.atlantisorigins.common.ai.goals;

import com.samplecat.atlantisorigins.common.ai.steering.SeparationSteering;
import com.samplecat.atlantisorigins.common.entity.DeepGuardian;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * Keeps a non-captain Deep Guardian near its captain when the squad has no
 * active target, while also maintaining separation from other squad members.
 */
public class FollowSquadLeaderGoal extends Goal {

    private static final double MIN_DIST_SQR = 6.0D * 6.0D;
    private static final double MAX_DIST_SQR = 20.0D * 20.0D;
    private static final double BACK_OFF_DIST = 4.0D;

    private static final double SEPARATION_RADIUS = 4.0D;
    private static final double MIN_SEPARATION = 2.5D;
    private static final double SEPARATION_STRENGTH = 2.0D;

    private final DeepGuardian guardian;

    public FollowSquadLeaderGoal(DeepGuardian guardian) {
        this.guardian = guardian;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return !this.guardian.isCaptain() && this.guardian.getCaptainEntity() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void stop() {
        this.guardian.getNavigation().stop();
    }

    @Override
    public void tick() {
        DeepGuardian captain = this.guardian.getCaptainEntity();
        if (captain == null) {
            return;
        }

        double distSqr = this.guardian.distanceToSqr(captain);
        this.guardian.getLookControl().setLookAt(captain, 15.0F, 30.0F);

        Vec3 desired;
        double speed;
        if (distSqr > MAX_DIST_SQR) {
            desired = captain.position();
            speed = 1.1D;
        } else if (distSqr < MIN_DIST_SQR) {
            Vec3 away = this.guardian.position().subtract(captain.position());
            if (away.lengthSqr() < 0.0001D) {
                away = new Vec3(1.0D, 0.0D, 0.0D);
            } else {
                away = away.normalize();
            }
            desired = this.guardian.position().add(away.scale(BACK_OFF_DIST));
            speed = 0.8D;
        } else {
            desired = this.guardian.position();
            speed = 0.6D;
        }

        Vec3 separation = SeparationSteering.compute(this.guardian, SEPARATION_RADIUS, MIN_SEPARATION, SEPARATION_STRENGTH);
        if (separation.lengthSqr() > 0.0001D) {
            desired = desired.add(separation);
            speed = Math.max(speed, 0.8D);
        }

        if (desired.distanceToSqr(this.guardian.position()) > 1.0D) {
            this.guardian.getNavigation().moveTo(desired.x, desired.y, desired.z, speed);
        } else {
            this.guardian.getNavigation().stop();
        }
    }
}
