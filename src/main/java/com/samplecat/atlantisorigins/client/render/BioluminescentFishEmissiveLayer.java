package com.samplecat.atlantisorigins.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.model.BioluminescentFishModel;
import com.samplecat.atlantisorigins.common.entity.BioluminescentFish;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BioluminescentFishEmissiveLayer extends RenderLayer<BioluminescentFish, BioluminescentFishModel> {

    private static final ResourceLocation EMISSIVE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/entity/bioluminescent_fish_e.png");

    public BioluminescentFishEmissiveLayer(BioluminescentFishRenderer renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       BioluminescentFish entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }

        VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(EMISSIVE_TEXTURE));
        this.getParentModel().renderToBuffer(poseStack, consumer, LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY, -1);
    }
}
