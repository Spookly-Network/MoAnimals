package net.spookly.moanimals.client.model;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.spookly.moanimals.client.animations.RacoonAnimations;
import net.spookly.moanimals.entity.Racoon;

public class RacoonModel<T extends Racoon> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "raccoon"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart upper_body;
    private final ModelPart head2;
    private final ModelPart tail;
    private final ModelPart leg_right_back;
    private final ModelPart leg_right_front;
    private final ModelPart leg_left_back;
    private final ModelPart leg_left_front;

    public RacoonModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.upper_body = this.body.getChild("upper_body");
        this.head2 = this.upper_body.getChild("head2");
        this.tail = this.upper_body.getChild("tail");
        this.leg_right_back = this.body.getChild("leg_right_back");
        this.leg_right_front = this.body.getChild("leg_right_front");
        this.leg_left_back = this.body.getChild("leg_left_back");
        this.leg_left_front = this.body.getChild("leg_left_front");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -5.3333F, 0.5F));

        PartDefinition upper_body = body.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.0F, -5.5F, 8.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(36, 26).addBox(-3.5F, 0.0F, -2.5F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(30, 36).addBox(3.5F, 0.0F, -2.5F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.3333F, 0.0F));

        PartDefinition head2 = upper_body.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -6.0F, -6.0F, 9.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(8, 40).addBox(-1.5F, -2.5F, -8.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 36).addBox(1.5F, -8.0F, -5.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 36).addBox(-3.5F, -8.0F, -5.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.0F, -4.5F));

        PartDefinition cube_r1 = head2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(22, 35).addBox(0.0F, -3.5F, 0.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -2.5F, -4.0F, 0.0F, 0.2182F, 0.0F));

        PartDefinition cube_r2 = head2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(22, 35).addBox(0.0F, -3.5F, 0.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -2.5F, -4.0F, 0.0F, -0.2182F, 0.0F));

        PartDefinition tail = upper_body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 29).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(22, 29).addBox(-2.0F, -2.0F, 7.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 5.5F));

        PartDefinition leg_right_back = body.addOrReplaceChild("leg_right_back", CubeListBuilder.create().texOffs(0, 40).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.9F, 0.3333F, 3.5F));

        PartDefinition leg_right_front = body.addOrReplaceChild("leg_right_front", CubeListBuilder.create().texOffs(38, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.9F, 0.3333F, -3.5F));

        PartDefinition leg_left_back = body.addOrReplaceChild("leg_left_back", CubeListBuilder.create().texOffs(38, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.9F, 0.3333F, 3.5F));

        PartDefinition leg_left_front = body.addOrReplaceChild("leg_left_front", CubeListBuilder.create().texOffs(38, 8).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.9F, 0.3333F, -3.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Racoon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);
        this.animateWalk(RacoonAnimations.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, RacoonAnimations.idle, ageInTicks, 1f);
        this.animate(entity.sleepAnimationState, RacoonAnimations.toSleep, ageInTicks, 1f);
        this.animate(entity.breathAnimationState, RacoonAnimations.sleep, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45f);

        this.head2.yRot = headYaw * ((float) Math.PI / 180F);
        this.head2.xRot = headPitch * ((float) Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
