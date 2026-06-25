// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class deep_guardian<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "deep_guardian"), "main");
	private final ModelPart all;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart arm-l;
	private final ModelPart arm-r;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart bone;

	public deep_guardian(ModelPart root) {
		this.all = root.getChild("all");
		this.head = this.all.getChild("head");
		this.body = this.all.getChild("body");
		this.arm-l = this.body.getChild("arm-l");
		this.arm-r = this.body.getChild("arm-r");
		this.tail = this.body.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.bone = this.tail2.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, -12.0F));

		PartDefinition head = all.addOrReplaceChild("head", CubeListBuilder.create().texOffs(128, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(136, 32).addBox(-8.0F, -24.0F, -4.0F, 16.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition arm-l = body.addOrReplaceChild("arm-l", CubeListBuilder.create().texOffs(104, 32).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, -20.0F, 2.0F));

		PartDefinition arm-r = body.addOrReplaceChild("arm-r", CubeListBuilder.create().texOffs(184, 32).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, -20.0F, 2.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(136, 64).addBox(-8.0F, -2.0F, -4.0F, 16.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(136, 84).addBox(-8.3F, -6.3F, -3.7F, 16.0F, 12.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.25F, 14.8827F, 1.491F, 0.2618F, 0.0F, 0.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 4.0F));

		PartDefinition cube_r2 = tail2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(144, 144).addBox(-6.0F, -8.0F, -2.0F, 12.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5F, 23.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r3 = tail2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(136, 124).addBox(-9.2F, -7.2F, -2.8F, 16.0F, 12.0F, 8.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(1.25F, 3.5F, 10.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r4 = tail2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(136, 104).addBox(-8.6F, -6.6F, -3.4F, 16.0F, 12.0F, 8.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.5F, 1.8692F, 2.7473F, 1.0908F, 0.0F, 0.0F));

		PartDefinition bone = tail2.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(136, 160).addBox(-10.0F, -2.0F, -15.0F, 20.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(128, 168).addBox(-12.0F, -2.0F, -11.0F, 24.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(124, 176).addBox(2.0F, -2.0F, -3.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(156, 176).addBox(-14.0F, -2.0F, -3.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(152, 176).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.2F, 38.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}