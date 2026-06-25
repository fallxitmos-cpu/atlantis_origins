package com.samplecat.atlantisorigins.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.model.OrichalcumTridentModel;
import com.samplecat.atlantisorigins.common.item.ModItems;

import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;

public class OrichalcumTridentRenderer extends EntityRenderer<ThrownTrident> {

    public static final ResourceLocation ORICHALCUM_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/entity/orichalcum_trident.png");
    private static final ResourceLocation VANILLA_TRIDENT_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/trident.png");

    private final OrichalcumTridentModel orichalcumModel;
    private final TridentModel vanillaModel;

    public OrichalcumTridentRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.orichalcumModel = new OrichalcumTridentModel(context.bakeLayer(OrichalcumTridentModel.LAYER_LOCATION));
        this.vanillaModel = new TridentModel(context.bakeLayer(ModelLayers.TRIDENT));
    }

    @Override
    public void render(ThrownTrident entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.mulPose(Axis.YP.rotationDegrees(
                Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(
                Mth.lerp(partialTick, entity.xRotO, entity.getXRot()) + 90.0F));

        ItemStack weapon = entity.getWeaponItem();
        boolean isOrichalcum = weapon.is(ModItems.ORICHALCUM_TRIDENT.get()) || weapon.is(ModItems.DEEP_GUARDIAN_TRIDENT.get());

        if (isOrichalcum) {
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer,
                    RenderType.entityCutoutNoCull(ORICHALCUM_TEXTURE), false, entity.isFoil());
            this.orichalcumModel.renderToBuffer(poseStack, vertexConsumer, packedLight,
                    OverlayTexture.NO_OVERLAY, -1);
        } else {
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer,
                    RenderType.entityCutoutNoCull(VANILLA_TRIDENT_TEXTURE), false, entity.isFoil());
            this.vanillaModel.renderToBuffer(poseStack, vertexConsumer, packedLight,
                    OverlayTexture.NO_OVERLAY, -1);
        }

        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownTrident entity) {
        ItemStack weapon = entity.getWeaponItem();
        return weapon.is(ModItems.ORICHALCUM_TRIDENT.get()) || weapon.is(ModItems.DEEP_GUARDIAN_TRIDENT.get())
                ? ORICHALCUM_TEXTURE
                : VANILLA_TRIDENT_TEXTURE;
    }
}
