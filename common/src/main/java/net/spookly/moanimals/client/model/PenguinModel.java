package net.spookly.moanimals.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.client.animations.PenguinAnimations;
import net.spookly.moanimals.entity.Penguin;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PenguinModel<T extends Penguin> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Moanimals.MOD_ID, "penguin"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart wing_right;
    private final ModelPart wing_left;
    private final ModelPart hip;
    private final ModelPart leg_left;
    private final ModelPart foot_left;
    private final ModelPart leg_right;
    private final ModelPart foot_right;

    public PenguinModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.head = this.body.getChild("head");
        this.wing_right = this.body.getChild("wing_right");
        this.wing_left = this.body.getChild("wing_left");
        this.hip = this.root.getChild("hip");
        this.leg_left = this.hip.getChild("leg_left");
        this.foot_left = this.leg_left.getChild("foot_left");
        this.leg_right = this.hip.getChild("leg_right");
        this.foot_right = this.leg_right.getChild("foot_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -11.65F, -4.0F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-3.5F, -6.65F, -3.0F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
            .texOffs(29, 27).addBox(-1.5F, -1.65F, -5.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -11.0F, -1.0F));

        PartDefinition wing_right = body.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(28, 13).addBox(-1.0F, -0.65F, -2.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -10.0F, -0.5F));

        PartDefinition wing_left = body.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(28, 0).addBox(0.0F, -0.65F, -2.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -10.0F, -0.5F));

        PartDefinition hip = root.addOrReplaceChild("hip", CubeListBuilder.create(), PartPose.offset(1.5F, 1.0F, -1.0F));

        PartDefinition leg_left = hip.addOrReplaceChild("leg_left", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition leg_left_r1 = leg_left.addOrReplaceChild("leg_left_r1", CubeListBuilder.create().texOffs(1, 31).addBox(0.5F, -2.65F, 1.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.2167F, -0.9773F, -0.2182F, 0.0F, 0.0F));

        PartDefinition foot_left = leg_left.addOrReplaceChild("foot_left", CubeListBuilder.create().texOffs(28, 30).addBox(-1.5F, -0.6334F, -2.8485F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_right = hip.addOrReplaceChild("leg_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition leg_right_r1 = leg_right.addOrReplaceChild("leg_right_r1", CubeListBuilder.create().texOffs(1, 31).addBox(0.5F, -2.65F, 1.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.2167F, -0.9773F, -0.2182F, 0.0F, 0.0F));

        PartDefinition foot_right = leg_right.addOrReplaceChild("foot_right", CubeListBuilder.create().texOffs(0, 31).addBox(-1.5F, -0.6334F, -2.8485F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    public @NotNull ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(Penguin entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);
        this.animate(entity.idleAnimationState, PenguinAnimations.idle, ageInTicks);
        this.animate(entity.flapAnimationState, PenguinAnimations.flap, ageInTicks);
        //Args: MaxAnimationSpeed, AnimationScaleFactor
        this.animateWalk(PenguinAnimations.walk, limbSwing, limbSwingAmount, 9f, 9f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45f);

        this.head.yRot = headYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
    }
}
