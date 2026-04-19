package net.spookly.moanimals.client.model;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.spookly.moanimals.client.animations.SeagullAnimation;
import net.spookly.moanimals.entity.Seagull;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SeagullModel<T extends Seagull> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "seagull"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart wing_right;
    private final ModelPart wing_left;
    private final ModelPart tail;
    private final ModelPart leg_right;
    private final ModelPart foot_right;
    private final ModelPart leg_left;
    private final ModelPart foot_left;

    public SeagullModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.head = this.body.getChild("head");
        this.wing_right = this.body.getChild("wing_right");
        this.wing_left = this.body.getChild("wing_left");
        this.tail = this.body.getChild("tail");
        this.leg_right = this.root.getChild("leg_right");
        this.foot_right = this.leg_right.getChild("foot_right");
        this.leg_left = this.root.getChild("leg_left");
        this.foot_left = this.leg_left.getChild("foot_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -3.5F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.5F, -0.4363F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -5.7805F, -1.05F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(12, 23).addBox(-0.5F, -3.2821F, -3.05F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -2.6422F, 0.4363F, 0.0F, 0.0F));

        PartDefinition wing_right = body.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(0, 11).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -4.0F, -2.5F, 0.2094F, 0.0873F, -0.2443F));

        PartDefinition wing_left = body.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(14, 11).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -4.0F, -2.5F, 0.2094F, -0.0873F, 0.2443F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(22, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 2.5F));

        PartDefinition leg_right = root.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(20, 23).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -2.0F, 0.5F));

        PartDefinition foot_right = leg_right.addOrReplaceChild("foot_right", CubeListBuilder.create().texOffs(22, 8).addBox(1.5F, 2.0F, -3.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 1.5F));

        PartDefinition leg_left = root.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(18, 23).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -2.0F, 0.5F));

        PartDefinition foot_left = leg_left.addOrReplaceChild("foot_left", CubeListBuilder.create().texOffs(22, 5).addBox(-0.5F, 1.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -0.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(Seagull entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);
        this.animateWalk(SeagullAnimation.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, SeagullAnimation.idle, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45f);
        this.head.yRot = headYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
    }
}
