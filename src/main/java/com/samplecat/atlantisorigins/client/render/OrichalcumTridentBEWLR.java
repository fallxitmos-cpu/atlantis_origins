package com.samplecat.atlantisorigins.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.samplecat.atlantisorigins.client.model.OrichalcumTridentModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Renders the orichalcum trident as a 3D model in hand, on the ground, in item
 * frames and in the inventory GUI.
 *
 * <p>The item model JSON ({@code orichalcum_trident.json}) uses
 * {@code parent: builtin/entity} and supplies the display transforms, so this
 * renderer only needs to draw the model at the entity-model origin.</p>
 */
public class OrichalcumTridentBEWLR extends BlockEntityWithoutLevelRenderer {

    private OrichalcumTridentModel model;

    public OrichalcumTridentBEWLR() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
    }

    private OrichalcumTridentModel getModel() {
        if (this.model == null) {
            this.model = new OrichalcumTridentModel(
                    Minecraft.getInstance().getEntityModels().bakeLayer(OrichalcumTridentModel.LAYER_LOCATION));
        }
        return this.model;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext,
                             PoseStack poseStack, MultiBufferSource buffer,
                             int packedLight, int packedOverlay) {
        poseStack.pushPose();

        // Flip Y and Z to match how vanilla renders the trident model in hand / GUI.
        // The model origin stays at the grip/crossbar area.
        poseStack.scale(1.0F, -1.0F, -1.0F);

        OrichalcumTridentModel model = this.getModel();
        VertexConsumer vertexConsumer;
        if (stack.hasFoil()) {
            // Combine the base texture buffer with the enchantment glint buffer.
            vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer,
                    RenderType.entityCutoutNoCull(OrichalcumTridentRenderer.ORICHALCUM_TEXTURE), false, true);
        } else {
            vertexConsumer = buffer.getBuffer(
                    RenderType.entityCutoutNoCull(OrichalcumTridentRenderer.ORICHALCUM_TEXTURE));
        }
        model.renderToBuffer(poseStack, vertexConsumer, packedLight,
                OverlayTexture.NO_OVERLAY, -1);

        poseStack.popPose();
    }
}
