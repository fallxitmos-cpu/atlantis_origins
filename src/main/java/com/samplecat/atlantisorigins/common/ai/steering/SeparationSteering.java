package com.samplecat.atlantisorigins.common.ai.steering;

import com.samplecat.atlantisorigins.common.entity.DeepGuardian;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

/**
 * Utility for computing a repulsion vector that keeps entities apart.
 */
public final class SeparationSteering {

    private SeparationSteering() {
    }

    /**
     * Computes a repulsion vector for {@code self} away from nearby Deep Guardians.
     *
     * @param self            the entity to compute separation for
     * @param radius          detection radius for neighbors
     * @param minSeparation   distance below which repulsion starts to apply
     * @param strength        overall strength multiplier
     * @param extraFilter     additional filter for valid neighbors
     * @return a vector to add to the desired destination
     */
    public static Vec3 compute(DeepGuardian self, double radius, double minSeparation,
                               double strength, Predicate<DeepGuardian> extraFilter) {
        if (self.level().isClientSide()) {
            return Vec3.ZERO;
        }

        Vec3 position = self.position();
        Vec3 repulse = Vec3.ZERO;
        AABB box = self.getBoundingBox().inflate(radius);
        for (DeepGuardian other : self.level().getEntitiesOfClass(DeepGuardian.class, box,
                e -> e != self && e.isAlive() && extraFilter.test(e))) {
            Vec3 away = position.subtract(other.position());
            double dist = away.length();
            if (dist < minSeparation && dist > 0.001D) {
                repulse = repulse.add(away.normalize().scale((minSeparation - dist) / minSeparation));
            }
        }
        return repulse.scale(strength);
    }

    /**
     * Convenience overload that repels from all nearby Deep Guardians.
     */
    public static Vec3 compute(DeepGuardian self, double radius, double minSeparation, double strength) {
        return compute(self, radius, minSeparation, strength, e -> true);
    }
}
