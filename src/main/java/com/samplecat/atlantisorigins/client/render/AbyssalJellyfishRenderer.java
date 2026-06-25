package com.samplecat.atlantisorigins.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.model.AbyssalJellyfishModel;
import com.samplecat.atlantisorigins.common.entity.AbyssalJellyfish;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AbyssalJellyfishRenderer extends MobRenderer<AbyssalJellyfish, AbyssalJellyfishModel> {

    private static final ResourceLocation TEXTURE_WHITE = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/entity/abyssal_jellyfish.png");
    private static final ResourceLocation TEXTURE_GOLD = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/entity/abyssal_jellyfish_gold.png");
    private static final ResourceLocation TEXTURE_BLUE = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/entity/abyssal_jellyfish_blue.png");
    private static final ResourceLocation TEXTURE_RED = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/entity/abyssal_jellyfish_red.png");

    public AbyssalJellyfishRenderer(EntityRendererProvider.Context context) {
        super(context, new AbyssalJellyfishModel(context.bakeLayer(AbyssalJellyfishModel.LAYER_LOCATION)), 0.7F);
        this.shadowRadius = 0.4F;
        this.addLayer(new AbyssalJellyfishEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(AbyssalJellyfish entity) {
        return switch (entity.getVariant()) {
            case AbyssalJellyfish.VARIANT_GOLD -> TEXTURE_GOLD;
            case AbyssalJellyfish.VARIANT_BLUE -> TEXTURE_BLUE;
            case AbyssalJellyfish.VARIANT_RED -> TEXTURE_RED;
            default -> TEXTURE_WHITE;
        };
    }

    @Override
    protected void setupRotations(AbyssalJellyfish entity, PoseStack poseStack,
                                  float ageInTicks, float rotationYaw, float partialTick, float p) {
        float xBodyRot = Mth.lerp(partialTick, entity.xBodyRotO, entity.xBodyRot);
        float zBodyRot = Mth.lerp(partialTick, entity.zBodyRotO, entity.zBodyRot);

        // The re-exported model's lowest point is at Y = 6 pixels = 0.375 blocks.
        // Translate down by that amount so the model bottom aligns with the entity position.
        poseStack.translate(0.0F, -0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(xBodyRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(zBodyRot));
    }

    @Override
    protected int getBlockLightLevel(AbyssalJellyfish entity, BlockPos pos) {
        return 15;
    }

    @Override
    protected float getBob(AbyssalJellyfish entity, float partialTick) {
        return Mth.lerp(partialTick, entity.oldTentacleAngle, entity.tentacleAngle);
    }
}
