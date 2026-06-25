package com.samplecat.atlantisorigins.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.model.BioluminescentFishModel;
import com.samplecat.atlantisorigins.common.entity.BioluminescentFish;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BioluminescentFishRenderer extends MobRenderer<BioluminescentFish, BioluminescentFishModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/entity/bioluminescent_fish.png");

    public BioluminescentFishRenderer(EntityRendererProvider.Context context) {
        super(context, new BioluminescentFishModel(context.bakeLayer(BioluminescentFishModel.LAYER_LOCATION)), 0.25F);
        this.shadowRadius = 0.2F;
        this.addLayer(new BioluminescentFishEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(BioluminescentFish entity) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(BioluminescentFish entity, PoseStack poseStack,
                                  float ageInTicks, float rotationYaw, float partialTick, float p) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTick, p);
        // The Blockbench model is oriented along the X axis. Rotate so it faces forward.
        // MobRenderer already applies the standard Y=-1.501 origin shift for 24px mob models.
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
    }

    @Override
    protected int getBlockLightLevel(BioluminescentFish entity, BlockPos pos) {
        return 15;
    }
}
