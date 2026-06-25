package com.samplecat.atlantisorigins.client.render;

import com.samplecat.atlantisorigins.client.model.OrichalcumArmorModel;
import com.samplecat.atlantisorigins.common.item.OrichalcumArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

/**
 * Renderer for the orichalcum armor set.
 * <p>
 * Uses the standard GeckoLib armor bone names
 * ({@code armorHead}, {@code armorBody}, {@code armorRightArm}, {@code armorLeftArm},
 * {@code armorRightLeg}, {@code armorLeftLeg}, {@code armorRightBoot}, {@code armorLeftBoot}).
 */
public class OrichalcumArmorRenderer extends GeoArmorRenderer<OrichalcumArmorItem> {

    public OrichalcumArmorRenderer() {
        super(new OrichalcumArmorModel());
    }

    /**
     * Use a translucent render type so the orichalcum texture's alpha channel
     * (semi-transparent fins/glow edges) is actually blended instead of being
     * treated as a 1-bit cutout by {@link RenderType#armorCutoutNoCull}.
     */
    @Override
    public RenderType getRenderType(OrichalcumArmorItem animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    /**
     * The Blockbench-exported model's limb local axes already align with vanilla's
     * after {@code GeoArmorRenderer}'s {@code scale(-1,-1,1)}. GeckoLib's
     * {@code matchModelPartRot} assumes the opposite and negates X/Y rotations,
     * which makes arms/legs swing the wrong way for this model. Re-invert X/Y
     * rotations for the limb bones only, leaving position untouched.
     */
    @Override
    protected void applyBaseTransformations(HumanoidModel<?> baseModel) {
        super.applyBaseTransformations(baseModel);

        if (this.rightArm != null) {
            this.rightArm.updateRotation(-this.rightArm.getRotX(), -this.rightArm.getRotY(), this.rightArm.getRotZ());
        }
        if (this.leftArm != null) {
            this.leftArm.updateRotation(-this.leftArm.getRotX(), -this.leftArm.getRotY(), this.leftArm.getRotZ());
        }
        if (this.rightLeg != null) {
            this.rightLeg.updateRotation(-this.rightLeg.getRotX(), -this.rightLeg.getRotY(), this.rightLeg.getRotZ());
        }
        if (this.leftLeg != null) {
            this.leftLeg.updateRotation(-this.leftLeg.getRotX(), -this.leftLeg.getRotY(), this.leftLeg.getRotZ());
        }
        if (this.rightBoot != null) {
            this.rightBoot.updateRotation(-this.rightBoot.getRotX(), -this.rightBoot.getRotY(), this.rightBoot.getRotZ());
        }
        if (this.leftBoot != null) {
            this.leftBoot.updateRotation(-this.leftBoot.getRotX(), -this.leftBoot.getRotY(), this.leftBoot.getRotZ());
        }
    }

}
