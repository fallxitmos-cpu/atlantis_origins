package com.samplecat.atlantisorigins.client.model;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.entity.AbyssalJellyfish;

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

public class AbyssalJellyfishModel extends HierarchicalModel<AbyssalJellyfish> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "abyssal_jellyfish"), "main");

    private final ModelPart root;

    public AbyssalJellyfishModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(12, 11)
                        .addBox(-6.0F, -4.5F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(1.0F)),
                PartPose.offset(0.0F, 11.5F, 0.0F));

        PartDefinition tentacle = partdefinition.addOrReplaceChild("tentacle",
                CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        tentacle.addOrReplaceChild("tentacle1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 8.0F, 5.0F));
        tentacle.addOrReplaceChild("tentacle2",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 8.0F, 5.0F));
        tentacle.addOrReplaceChild("tentacle3",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 8.0F, 0.0F));
        tentacle.addOrReplaceChild("tentacle4",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 8.0F, -5.0F));
        tentacle.addOrReplaceChild("tentacle5",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 8.0F, -5.0F));
        tentacle.addOrReplaceChild("tentacle6",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 8.0F, -5.0F));
        tentacle.addOrReplaceChild("tentacle7",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 8.0F, 0.0F));
        tentacle.addOrReplaceChild("tentacle8",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 8.0F, 5.0F));

        PartDefinition wfoot5 = partdefinition.addOrReplaceChild("Wfoot5",
                CubeListBuilder.create().texOffs(4, 1).addBox(-3.0F, 0.0F, -3.0F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 1).addBox(-3.0F, 0.0F, 2.0F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 1).addBox(2.0F, 0.0F, 2.0F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(4, 1).addBox(2.0F, 0.0F, -3.0F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition foot = partdefinition.addOrReplaceChild("foot",
                CubeListBuilder.create(), PartPose.offset(0.0F, 19.75F, 0.0F));

        foot.addOrReplaceChild("foot1",
                CubeListBuilder.create().texOffs(4, 1).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 0.25F, 5.0F));
        foot.addOrReplaceChild("foot2",
                CubeListBuilder.create().texOffs(4, 1).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 0.25F, -5.0F));
        foot.addOrReplaceChild("foot3",
                CubeListBuilder.create().texOffs(4, 1).addBox(0.0F, 0.0F, -1.0F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 0.25F, -5.0F));
        foot.addOrReplaceChild("foot4",
                CubeListBuilder.create().texOffs(4, 1).addBox(0.0F, 0.0F, -1.0F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 0.25F, 6.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(AbyssalJellyfish entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        // Blockbench-exported model; add per-part animation here if desired.
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
