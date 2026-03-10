package net.spookly.moanimals.client.model;


import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.client.animations.PenguinAnimations;
import net.spookly.moanimals.client.renderer.entity.state.PenguinRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PenguinModel extends EntityModel<PenguinRenderState> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Moanimals.MOD_ID, "penguin"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;

    public PenguinModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.head = this.body.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -11.65F, -4.0F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-3.5F, -6.65F, -3.0F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
            .texOffs(29, 27).addBox(-1.5F, -1.65F, -5.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -11.0F, -1.0F));
        body.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(28, 13).addBox(-1.0F, -0.65F, -2.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -10.0F, -0.5F));
        body.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(28, 0).addBox(0.0F, -0.65F, -2.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -10.0F, -0.5F));

        PartDefinition hip = root.addOrReplaceChild("hip", CubeListBuilder.create(), PartPose.offset(1.5F, 1.0F, -1.0F));
        PartDefinition leg_left = hip.addOrReplaceChild("leg_left", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
        leg_left.addOrReplaceChild("leg_left_r1", CubeListBuilder.create().texOffs(1, 31).addBox(0.5F, -2.65F, 1.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.2167F, -0.9773F, -0.2182F, 0.0F, 0.0F));
        leg_left.addOrReplaceChild("foot_left", CubeListBuilder.create().texOffs(28, 30).addBox(-1.5F, -0.6334F, -2.8485F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_right = hip.addOrReplaceChild("leg_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
        leg_right.addOrReplaceChild("leg_right_r1", CubeListBuilder.create().texOffs(1, 31).addBox(0.5F, -2.65F, 1.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.2167F, -0.9773F, -0.2182F, 0.0F, 0.0F));
        leg_right.addOrReplaceChild("foot_right", CubeListBuilder.create().texOffs(0, 31).addBox(-1.5F, -0.6334F, -2.8485F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(PenguinRenderState entityRenderState) {
        super.setupAnim(entityRenderState);
        this.applyHeadRotation(entityRenderState.xRot, entityRenderState.yRot);
        //Args: MaxAnimationSpeed, AnimationScaleFactor
        this.animateWalk(PenguinAnimations.walk, entityRenderState.walkAnimationPos, entityRenderState.walkAnimationSpeed, 9f, 9f);
        this.animate(entityRenderState.idleAnimationState, PenguinAnimations.idle, entityRenderState.ageInTicks);
        this.animate(entityRenderState.flapAnimationState, PenguinAnimations.flap, entityRenderState.ageInTicks);
    }

    private void applyHeadRotation(float xRot, float yRot) {
        xRot = Mth.clamp(xRot, -30f, 30f);
        yRot = Mth.clamp(yRot, -25f, 45f);
        this.head.yRot = xRot * ((float)Math.PI / 180F);
        this.head.xRot = yRot * ((float)Math.PI / 180F);
    }
}
