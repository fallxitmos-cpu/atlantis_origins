package com.samplecat.atlantisorigins.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.model.DeepGuardianModel;
import com.samplecat.atlantisorigins.common.entity.DeepGuardian;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class DeepGuardianRenderer extends MobRenderer<DeepGuardian, DeepGuardianModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/entity/deep_guardian.png");

    public DeepGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new DeepGuardianModel(context.bakeLayer(DeepGuardianModel.LAYER_LOCATION)), 0.5F);
        this.shadowRadius = 0.6F;
    }

    @Override
    public ResourceLocation getTextureLocation(DeepGuardian entity) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(DeepGuardian entity, PoseStack poseStack,
                                  float ageInTicks, float rotationYaw, float partialTick, float p) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick, p);
        // 模型默认沿 Y 轴竖直，旋转 -90° 使其头部朝向前方（水平姿态）
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
    }

    @Override
    protected int getBlockLightLevel(DeepGuardian entity, BlockPos pos) {
        return 15;
    }
}
