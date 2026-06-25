package com.samplecat.atlantisorigins.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.samplecat.atlantisorigins.AtlantisOrigins;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class OrichalcumTridentModel extends EntityModel<net.minecraft.world.entity.projectile.ThrownTrident> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "orichalcum_trident"), "main");

    private final ModelPart bone4;
    private final ModelPart bone5;
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone3;
    private final ModelPart bone6;
    private final ModelPart bb_main;

    public OrichalcumTridentModel(ModelPart root) {
        this.bone4 = root.getChild("bone4");
        this.bone5 = root.getChild("bone5");
        this.bone = root.getChild("bone");
        this.bone2 = root.getChild("bone2");
        this.bone3 = root.getChild("bone3");
        this.bone6 = root.getChild("bone6");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone4 = partdefinition.addOrReplaceChild("bone4",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(0.0F, -20.0F, -1.0F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.5F, 24.0F, 0.5F));

        PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5",
                CubeListBuilder.create().texOffs(12, 4)
                        .addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.5F, 3.0F, -0.5F));

        PartDefinition bone = partdefinition.addOrReplaceChild("bone",
                CubeListBuilder.create().texOffs(12, 7)
                        .addBox(1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.5F, 24.0F, 0.5F));

        PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2",
                CubeListBuilder.create().texOffs(8, 12)
                        .addBox(-3.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.5F, 24.0F, 0.5F));

        PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3",
                CubeListBuilder.create().texOffs(12, 9)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.5F, 0.0F));

        PartDefinition bone6 = partdefinition.addOrReplaceChild("bone6",
                CubeListBuilder.create().texOffs(8, 2)
                        .addBox(-2.0F, -2.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 5.0F, 0.5F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main",
                CubeListBuilder.create().texOffs(4, 0)
                        .addBox(-4.0F, -24.0F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 4).addBox(3.0F, -31.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 11).addBox(-4.0F, -31.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 2).addBox(-0.5F, -32.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 11).addBox(-1.5F, -25.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 13).addBox(0.5F, -25.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(net.minecraft.world.entity.projectile.ThrownTrident entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
                               int packedOverlay, int color) {
        bone4.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        bone5.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        bone6.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
