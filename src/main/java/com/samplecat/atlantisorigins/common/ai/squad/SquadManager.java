package com.samplecat.atlantisorigins.common.ai.squad;

import com.samplecat.atlantisorigins.common.ai.target.ThreatTargetSelector;
import com.samplecat.atlantisorigins.common.entity.DeepGuardian;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Central place for Deep Guardian squad logic: promotion, target coordination,
 * mourning, and member lookup.
 */
public final class SquadManager {

    public static final int CAPTAIN_PROMOTION_INTERVAL = 100;
    public static final float CAPTAIN_PROMOTION_CHANCE = 0.10F;
    public static final double CAPTAIN_PROMOTION_RADIUS = 16.0D;
    public static final double CAPTAIN_EXISTENCE_RADIUS = 128.0D;
    public static final int SQUAD_TARGET_REEVALUATE_INTERVAL = 20;
    public static final int MOURNING_DURATION = 1000;

    private SquadManager() {
    }

    /**
     * Main per-entity tick. Called from {@link DeepGuardian#tick()} on the server.
     */
    public static void tick(DeepGuardian guardian) {
        if (guardian.level().isClientSide()) {
            return;
        }

        if (guardian.isCaptain()) {
            electTarget(guardian);
            return;
        }

        // Not a captain: clear captain visuals, maintain mourning, follow orders.
        guardian.setGlowingTag(false);
        guardian.removeCaptainLightBlock();
        guardian.updateMourningModifiers();

        if (guardian.isInMourning()) {
            return;
        }

        syncCaptainTarget(guardian);

        if (guardian.hasCaptain()) {
            return;
        }

        tryPromote(guardian);
    }

    /**
     * Determines the entity's current squad role from its stored state.
     */
    public static SquadRole getRole(DeepGuardian guardian) {
        if (guardian.isCaptain()) {
            return SquadRole.CAPTAIN;
        }
        if (guardian.hasCaptain()) {
            return SquadRole.MEMBER;
        }
        return SquadRole.NONE;
    }

    /**
     * Promotes the guardian to captain if the conditions are met:
     * enough nearby allies, no existing captain in range, and the random chance.
     */
    public static void tryPromote(DeepGuardian guardian) {
        if (guardian.tickCount % CAPTAIN_PROMOTION_INTERVAL != 0) {
            return;
        }
        if (guardian.getRandom().nextFloat() >= CAPTAIN_PROMOTION_CHANCE) {
            return;
        }

        List<DeepGuardian> nearby = guardian.level().getEntitiesOfClass(DeepGuardian.class,
                guardian.getBoundingBox().inflate(CAPTAIN_PROMOTION_RADIUS), LivingEntity::isAlive);
        if (nearby.size() < 4) {
            return;
        }

        if (findNearbyCaptain(guardian, CAPTAIN_EXISTENCE_RADIUS) != null) {
            return;
        }

        becomeCaptain(guardian, nearby);
    }

    /**
     * Creates a new squad with the given guardian as captain and assigns nearby
     * guardians as members.
     */
    public static void becomeCaptain(DeepGuardian guardian, List<DeepGuardian> nearbyAllies) {
        UUID team = UUID.randomUUID();
        guardian.setTeamId(team);
        guardian.setCaptainUUID(guardian.getUUID());
        guardian.setCaptain(true);
        guardian.clearMourning();

        for (DeepGuardian ally : nearbyAllies) {
            if (ally == guardian || !ally.isAlive()) {
                continue;
            }
            ally.setTeamId(team);
            ally.setCaptainUUID(guardian.getUUID());
            ally.clearMourning();
        }
    }

    /**
     * Captain-only: re-evaluates the targets of every squad member and focuses
     * fire on the most threatening one.
     */
    public static void electTarget(DeepGuardian captain) {
        if (captain.getTeamId() == null) {
            return;
        }
        if (captain.tickCount % SQUAD_TARGET_REEVALUATE_INTERVAL != 0) {
            return;
        }

        List<DeepGuardian> members = getSquadMembers(captain, CAPTAIN_EXISTENCE_RADIUS, true);
        LivingEntity bestTarget = null;
        double bestThreat = -1.0D;
        for (DeepGuardian member : members) {
            LivingEntity target = member.getTarget();
            if (target == null || !target.isAlive()) {
                continue;
            }
            double threat = ThreatTargetSelector.computeThreat(target);
            if (threat > bestThreat) {
                bestThreat = threat;
                bestTarget = target;
            }
        }

        if (bestTarget != null && bestTarget != captain.getTarget()) {
            captain.setTarget(bestTarget);
        }
    }

    /**
     * Member-only: keeps this guardian's target synchronized with its captain's target.
     */
    public static void syncCaptainTarget(DeepGuardian member) {
        DeepGuardian captain = member.getCaptainEntity();
        if (captain == null || !captain.isAlive()) {
            return;
        }
        LivingEntity captainTarget = captain.getTarget();
        if (captainTarget != null && captainTarget != member.getTarget()) {
            member.setTarget(captainTarget);
        }
    }

    /**
     * Called when a captain dies. Removes its captain visuals, clears the squad's
     * captain reference, and puts members into mourning.
     */
    public static void onCaptainDeath(DeepGuardian deadCaptain) {
        if (!deadCaptain.isCaptain() || deadCaptain.getTeamId() == null) {
            return;
        }

        UUID teamId = deadCaptain.getTeamId();
        deadCaptain.setGlowingTag(false);
        deadCaptain.removeCaptainLightBlock();

        long mourningUntil = deadCaptain.level().getGameTime() + MOURNING_DURATION;
        for (DeepGuardian member : getSquadMembers(deadCaptain, CAPTAIN_EXISTENCE_RADIUS, false)) {
            if (member.isCaptain()) {
                continue;
            }
            member.setCaptainUUID(null);
            member.setMourningUntilTick(mourningUntil);
        }
    }

    @Nullable
    public static DeepGuardian findNearbyCaptain(DeepGuardian guardian, double radius) {
        List<DeepGuardian> captains = guardian.level().getEntitiesOfClass(DeepGuardian.class,
                guardian.getBoundingBox().inflate(radius),
                e -> e != guardian && e.isAlive() && e.isCaptain());
        return captains.isEmpty() ? null : captains.getFirst();
    }

    /**
     * Returns all living squad members within the given radius around the requester.
     *
     * @param includeSelf whether the requester itself should be included in the list
     */
    public static List<DeepGuardian> getSquadMembers(DeepGuardian guardian, double radius, boolean includeSelf) {
        UUID teamId = guardian.getTeamId();
        if (teamId == null) {
            return new ArrayList<>();
        }
        AABB box = guardian.getBoundingBox().inflate(radius);
        List<DeepGuardian> members = guardian.level().getEntitiesOfClass(DeepGuardian.class, box,
                e -> e.isAlive() && teamId.equals(e.getTeamId()));
        if (!includeSelf) {
            members.remove(guardian);
        }
        members.sort(Comparator.comparing(e -> e.getUUID()));
        return members;
    }
}
