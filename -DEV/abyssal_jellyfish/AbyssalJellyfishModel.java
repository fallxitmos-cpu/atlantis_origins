// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class AbyssalJellyfishModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "abyssaljellyfishmodel"), "main");
	private final ModelPart body;
	private final ModelPart tentacle;
	private final ModelPart tentacle1;
	private final ModelPart tentacle2;
	private final ModelPart tentacle3;
	private final ModelPart tentacle4;
	private final ModelPart tentacle5;
	private final ModelPart tentacle6;
	private final ModelPart tentacle7;
	private final ModelPart tentacle8;
	private final ModelPart Wfoot5;
	private final ModelPart foot;
	private final ModelPart foot1;
	private final ModelPart foot2;
	private final ModelPart foot3;
	private final ModelPart foot4;

	public AbyssalJellyfishModel(ModelPart root) {
		this.body = root.getChild("body");
		this.tentacle = root.getChild("tentacle");
		this.tentacle1 = this.tentacle.getChild("tentacle1");
		this.tentacle2 = this.tentacle.getChild("tentacle2");
		this.tentacle3 = this.tentacle.getChild("tentacle3");
		this.tentacle4 = this.tentacle.getChild("tentacle4");
		this.tentacle5 = this.tentacle.getChild("tentacle5");
		this.tentacle6 = this.tentacle.getChild("tentacle6");
		this.tentacle7 = this.tentacle.getChild("tentacle7");
		this.tentacle8 = this.tentacle.getChild("tentacle8");
		this.Wfoot5 = root.getChild("Wfoot5");
		this.foot = root.getChild("foot");
		this.foot1 = this.foot.getChild("foot1");
		this.foot2 = this.foot.getChild("foot2");
		this.foot3 = this.foot.getChild("foot3");
		this.foot4 = this.foot.getChild("foot4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 11).addBox(-6.0F, -4.5F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 11.5F, 0.0F));

		PartDefinition tentacle = partdefinition.addOrReplaceChild("tentacle", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition tentacle1 = tentacle.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 5.0F));

		PartDefinition tentacle2 = tentacle.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 8.0F, 5.0F));

		PartDefinition tentacle3 = tentacle.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 8.0F, 0.0F));

		PartDefinition tentacle4 = tentacle.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 8.0F, -5.0F));

		PartDefinition tentacle5 = tentacle.addOrReplaceChild("tentacle5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, -5.0F));

		PartDefinition tentacle6 = tentacle.addOrReplaceChild("tentacle6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 8.0F, -5.0F));

		PartDefinition tentacle7 = tentacle.addOrReplaceChild("tentacle7", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 8.0F, 0.0F));

		PartDefinition tentacle8 = tentacle.addOrReplaceChild("tentacle8", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 8.0F, 5.0F));

		PartDefinition Wfoot5 = partdefinition.addOrReplaceChild("Wfoot5", CubeListBuilder.create().texOffs(4, 1).addBox(-3.0F, 0.0F, -3.0F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(-3.0F, 0.0F, 2.0F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(2.0F, 0.0F, 2.0F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 1).addBox(2.0F, 0.0F, -3.0F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition foot = partdefinition.addOrReplaceChild("foot", CubeListBuilder.create(), PartPose.offset(0.0F, 19.75F, 0.0F));

		PartDefinition foot1 = foot.addOrReplaceChild("foot1", CubeListBuilder.create().texOffs(4, 1).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.25F, 5.0F));

		PartDefinition foot2 = foot.addOrReplaceChild("foot2", CubeListBuilder.create().texOffs(4, 1).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.25F, -5.0F));

		PartDefinition foot3 = foot.addOrReplaceChild("foot3", CubeListBuilder.create().texOffs(4, 1).addBox(0.0F, 0.0F, -1.0F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.25F, -5.0F));

		PartDefinition foot4 = foot.addOrReplaceChild("foot4", CubeListBuilder.create().texOffs(4, 1).addBox(0.0F, 0.0F, -1.0F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.25F, 6.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		tentacle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Wfoot5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		foot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}