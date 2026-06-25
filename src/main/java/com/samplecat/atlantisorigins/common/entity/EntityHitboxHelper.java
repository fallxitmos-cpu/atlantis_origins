package com.samplecat.atlantisorigins.common.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Helpers for building entity bounding boxes that better match custom models.
 *
 * <p>Minecraft entity collision boxes are always axis-aligned (AABB), so they
 * cannot truly rotate with the model. What we <em>can</em> do is rotate the
 * box's center with the entity's yaw and, for horizontally long mobs, return
 * the smallest axis-aligned box that fully contains the yaw-rotated cuboid.
 * This makes the hitbox follow the creature's length, but at diagonal yaw
 * values it will be slightly wider than the model because the AABB edges must
 * stay parallel to the world axes.</p>
 */
public final class EntityHitboxHelper {

    private EntityHitboxHelper() {
    }

    /**
     * Builds an axis-aligned box whose center is offset from the entity position
     * and rotated by the entity's yaw.
     *
     * <p>Use this for creatures where the orientation does not matter (e.g.
     * upright jellyfish) or where the desired box is roughly cubic.</p>
     *
     * @param entity       the entity
     * @param centerOffset offset from {@code entity.position()} to the box
     *                     center. {@code z} is forward (towards yaw 0 = +Z),
     *                     {@code x} is right, {@code y} is up.
     * @param width        box width along the entity's right axis (blocks)
     * @param height       box height (blocks)
     * @param depth        box depth along the entity's forward axis (blocks)
     */
    public static AABB makeBox(LivingEntity entity, Vec3 centerOffset,
                               double width, double height, double depth) {
        double yaw = Math.toRadians(entity.getYRot());
        double sin = Math.sin(yaw);
        double cos = Math.cos(yaw);

        // Minecraft yaw: 0 = +Z (south), 90 = -X (west), 180 = -Z, 270 = +X.
        double offsetX = centerOffset.x * cos - centerOffset.z * sin;
        double offsetZ = centerOffset.x * sin + centerOffset.z * cos;

        Vec3 center = entity.position().add(offsetX, centerOffset.y, offsetZ);
        double halfW = width / 2.0D;
        double halfH = height / 2.0D;
        double halfD = depth / 2.0D;

        return new AABB(
                center.x - halfW, center.y - halfH, center.z - halfD,
                center.x + halfW, center.y + halfH, center.z + halfD
        );
    }

    /**
     * Builds the smallest axis-aligned box that contains a yaw-rotated cuboid.
     *
     * <p>The cuboid's local axes are:</p>
     * <ul>
     *   <li>forward: entity look direction (length {@code length})</li>
     *   <li>right:   perpendicular in the XZ plane (length {@code width})</li>
     *   <li>up:      world Y axis (length {@code height})</li>
     * </ul>
     *
     * <p>This is the closest we can get to a "rotating rectangular hitbox"
     * using vanilla AABBs. At cardinal yaw values the box equals the model
     * cuboid; at diagonal yaw values it is slightly enlarged to stay axis-aligned.</p>
     *
     * @param entity        the entity
     * @param centerOffset  offset from {@code entity.position()} to the cuboid
     *                      center, in the same local axes as above
     * @param length        cuboid length along the forward axis (blocks)
     * @param width         cuboid width along the right axis (blocks)
     * @param height        cuboid height (blocks)
     */
    public static AABB makeOrientedBox(LivingEntity entity, Vec3 centerOffset,
                                       double length, double width, double height) {
        double yaw = Math.toRadians(entity.getYRot());
        double sin = Math.sin(yaw);
        double cos = Math.cos(yaw);

        // Minecraft yaw: 0 = +Z (south), 90 = -X (west), 180 = -Z, 270 = +X.
        double offsetX = centerOffset.x * cos - centerOffset.z * sin;
        double offsetZ = centerOffset.x * sin + centerOffset.z * cos;

        Vec3 center = entity.position().add(offsetX, centerOffset.y, offsetZ);
        double halfL = length / 2.0D;
        double halfW = width / 2.0D;
        double halfH = height / 2.0D;

        // Project the rotated half-extents onto the world axes.
        double halfX = Math.abs(halfW * cos) + Math.abs(halfL * sin);
        double halfZ = Math.abs(halfW * sin) + Math.abs(halfL * cos);

        return new AABB(
                center.x - halfX, center.y - halfH, center.z - halfZ,
                center.x + halfX, center.y + halfH, center.z + halfZ
        );
    }
}
