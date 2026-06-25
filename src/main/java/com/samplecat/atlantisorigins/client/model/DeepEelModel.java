package com.samplecat.atlantisorigins.client.model;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.entity.DeepEel;

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

public class DeepEelModel extends HierarchicalModel<DeepEel> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "deep_eel"), "main");

    private final ModelPart root;

    public DeepEelModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone10 = partdefinition.addOrReplaceChild("bone10",
                CubeListBuilder.create(), PartPose.offset(-6.5F, 22.0F, -0.5F));

        bone10.addOrReplaceChild("bone",
                CubeListBuilder.create().texOffs(0, 6)
                        .addBox(-12.5F, -1.0F, -1.5F, 9.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        bone10.addOrReplaceChild("bone2",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(0.75F, -1.0F, -2.0F, 9.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(-4.5F, 0.0F, 0.5F));

        PartDefinition bone9 = bone10.addOrReplaceChild("bone9",
                CubeListBuilder.create(), PartPose.offset(-4.5F, 0.0F, 0.0F));

        bone9.addOrReplaceChild("bone3",
                CubeListBuilder.create().texOffs(0, 12)
                        .addBox(9.5F, -1.0F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        bone9.addOrReplaceChild("bone8",
                CubeListBuilder.create().texOffs(9, 24)
                        .addBox(23.5F, -1.0F, -1.5F, 8.0F, 3.0F, 3.0F, new CubeDeformation(-0.6F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        bone9.addOrReplaceChild("bone7",
                CubeListBuilder.create().texOffs(0, 24)
                        .addBox(21.75F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        bone9.addOrReplaceChild("bone6",
                CubeListBuilder.create().texOffs(12, 18)
                        .addBox(19.75F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        bone9.addOrReplaceChild("bone5",
                CubeListBuilder.create().texOffs(0, 18)
                        .addBox(17.5F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        bone9.addOrReplaceChild("bone4",
                CubeListBuilder.create().texOffs(16, 12)
                        .addBox(14.0F, -1.0F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(DeepEel entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.swimAnimationState, DeepEelAnimation.SWIM, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
