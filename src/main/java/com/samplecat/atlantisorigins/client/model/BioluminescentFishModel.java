package com.samplecat.atlantisorigins.client.model;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.entity.BioluminescentFish;

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

public class BioluminescentFishModel extends HierarchicalModel<BioluminescentFish> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "bioluminescent_fish"), "main");

    private final ModelPart root;

    public BioluminescentFishModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-2.0F, -4.0F, -1.0F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, 24.0F, 0.0F));

        PartDefinition bone4 = partdefinition.addOrReplaceChild("bone4",
                CubeListBuilder.create().texOffs(0, 6)
                        .addBox(-3.5F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.5F, 22.5F, 0.0F));

        PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2",
                CubeListBuilder.create().texOffs(10, 6)
                        .addBox(1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.0F, 22.0F, 0.0F));

        PartDefinition bone = partdefinition.addOrReplaceChild("bone",
                CubeListBuilder.create().texOffs(8, 11).addBox(2.0F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 11).addBox(3.0F, -2.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 11).addBox(2.0F, 1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(4.0F, 22.0F, 0.0F));

        PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5",
                CubeListBuilder.create().texOffs(6, 11).addBox(-3.0F, -2.0F, -2.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(6, 11).addBox(-3.0F, -2.0F, 1.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition bone6 = partdefinition.addOrReplaceChild("bone6",
                CubeListBuilder.create().texOffs(10, 12)
                        .addBox(-1.0F, -1.0F, 0.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 20.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(BioluminescentFish entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.swimAnimationState, BioluminescentFishAnimation.SWIM, ageInTicks);
        this.animate(entity.hitAnimationState, BioluminescentFishAnimation.HIT, ageInTicks);
        this.animate(entity.fleeAnimationState, BioluminescentFishAnimation.FLEE, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
