// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class deep_eel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "deep_eel"), "main");
	private final ModelPart bone10;
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone9;
	private final ModelPart bone3;
	private final ModelPart bone8;
	private final ModelPart bone7;
	private final ModelPart bone6;
	private final ModelPart bone5;
	private final ModelPart bone4;

	public deep_eel(ModelPart root) {
		this.bone10 = root.getChild("bone10");
		this.bone = this.bone10.getChild("bone");
		this.bone2 = this.bone10.getChild("bone2");
		this.bone9 = this.bone10.getChild("bone9");
		this.bone3 = this.bone9.getChild("bone3");
		this.bone8 = this.bone9.getChild("bone8");
		this.bone7 = this.bone9.getChild("bone7");
		this.bone6 = this.bone9.getChild("bone6");
		this.bone5 = this.bone9.getChild("bone5");
		this.bone4 = this.bone9.getChild("bone4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone10 = partdefinition.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(-6.5F, 22.0F, -0.5F));

		PartDefinition bone = bone10.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 6).addBox(-12.5F, -1.0F, -1.5F, 9.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone2 = bone10.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 0).addBox(0.75F, -1.0F, -2.0F, 9.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offset(-4.5F, 0.0F, 0.5F));

		PartDefinition bone9 = bone10.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(-4.5F, 0.0F, 0.0F));

		PartDefinition bone3 = bone9.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(0, 12).addBox(9.5F, -1.0F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone8 = bone9.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(9, 24).addBox(23.5F, -1.0F, -1.5F, 8.0F, 3.0F, 3.0F, new CubeDeformation(-0.6F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone7 = bone9.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(0, 24).addBox(21.75F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone6 = bone9.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(12, 18).addBox(19.75F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone5 = bone9.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(0, 18).addBox(17.5F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone4 = bone9.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(16, 12).addBox(14.0F, -1.0F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone10.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}