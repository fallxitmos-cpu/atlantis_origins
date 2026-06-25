package com.samplecat.atlantisorigins.client.model;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.entity.DeepGuardian;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class DeepGuardianModel extends HierarchicalModel<DeepGuardian> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "deep_guardian"), "main");

    private final ModelPart root;

    public DeepGuardianModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Shift the model so the head sits at the entity origin. After the
        // renderer's -90° X rotation and the standard mob origin shift, this
        // offset places the head's bottom/front at the entity position.
        PartDefinition all = partdefinition.addOrReplaceChild("all",
                CubeListBuilder.create(), PartPose.offset(0.0F, 48.0F, 8.0F));

        all.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(128, 0)
                        .addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -16.0F, 0.0F));

        PartDefinition body = all.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(136, 32)
                        .addBox(-8.0F, -24.0F, -4.0F, 16.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 8.0F, 0.0F));

        body.addOrReplaceChild("arm-l",
                CubeListBuilder.create().texOffs(104, 32)
                        .addBox(-4.0F, -4.0F, -6.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-12.0F, -20.0F, 2.0F));

        body.addOrReplaceChild("arm-r",
                CubeListBuilder.create().texOffs(184, 32)
                        .addBox(-4.0F, -4.0F, -6.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(12.0F, -20.0F, 2.0F));

        PartDefinition tail = body.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(136, 64)
                        .addBox(-8.0F, -2.0F, -4.0F, 16.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        tail.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(136, 84)
                        .addBox(-8.3F, -6.3F, -3.7F, 16.0F, 12.0F, 8.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 14.8827F, 1.491F, 0.2618F, 0.0F, 0.0F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2",
                CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 4.0F));

        tail2.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(144, 144)
                        .addBox(-6.0F, -8.0F, -2.0F, 12.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 3.5F, 23.0F, 1.5708F, 0.0F, 0.0F));

        tail2.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(136, 124)
                        .addBox(-9.2F, -7.2F, -2.8F, 16.0F, 12.0F, 8.0F, new CubeDeformation(-0.4F)),
                PartPose.offsetAndRotation(0.5F, 3.5F, 10.5F, 1.5708F, 0.0F, 0.0F));

        tail2.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(136, 104)
                        .addBox(-8.6F, -6.6F, -3.4F, 16.0F, 12.0F, 8.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(0.0F, 1.8692F, 2.7473F, 1.0908F, 0.0F, 0.0F));

        tail2.addOrReplaceChild("bone",
                CubeListBuilder.create().texOffs(136, 160)
                        .addBox(-10.0F, -2.0F, -15.0F, 20.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(128, 168).addBox(-12.0F, -2.0F, -11.0F, 24.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(124, 176).addBox(2.0F, -2.0F, -3.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(156, 176).addBox(-14.0F, -2.0F, -3.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(152, 176).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 5.2F, 38.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(DeepGuardian entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idleAnimationState, DeepGuardianAnimation.IDLE, ageInTicks);
        this.animate(entity.attackAnimationState, DeepGuardianAnimation.ATTACK, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
