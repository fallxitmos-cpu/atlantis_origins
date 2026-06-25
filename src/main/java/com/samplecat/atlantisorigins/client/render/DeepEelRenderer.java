package com.samplecat.atlantisorigins.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.model.DeepEelModel;
import com.samplecat.atlantisorigins.common.entity.DeepEel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class DeepEelRenderer extends MobRenderer<DeepEel, DeepEelModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/entity/deep_eel.png");

    public DeepEelRenderer(EntityRendererProvider.Context context) {
        super(context, new DeepEelModel(context.bakeLayer(DeepEelModel.LAYER_LOCATION)), 0.25F);
        this.shadowRadius = 0.25F;
        this.addLayer(new DeepEelEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(DeepEel entity) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(DeepEel entity, PoseStack poseStack,
                                  float ageInTicks, float rotationYaw, float partialTick, float p) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick, p);
        // Blockbench 模型沿 X 轴朝向，需要旋转 90° 使其朝向前方。
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
    }

    @Override
    protected int getBlockLightLevel(DeepEel entity, BlockPos pos) {
        return 15;
    }
}
