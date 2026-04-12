package net.spookly.moanimals.client.model;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.client.animations.BirdAnimations;
import net.spookly.moanimals.entity.Bird;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class BirdModel<T extends Bird> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "bird"), "main");
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

    public BirdModel(ModelPart root) {
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

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(10, 13).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(0, 22).addBox(-1.0F, -1.5F, -3.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(16, 7).addBox(0.0F, -3.0F, 1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(16, 0).addBox(0.0F, -7.0F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.8F, -0.6F, -0.1745F, 0.0F, 0.0F));

        PartDefinition wing_right = body.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.01F, -3.25F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition wing_left = body.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9999F, -3.25F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.5F));

        PartDefinition leg_right = root.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(22, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -2.0F, 0.5F));

        PartDefinition foot_right = leg_right.addOrReplaceChild("foot_right", CubeListBuilder.create().texOffs(10, 19).addBox(1.5F, 2.0F, -3.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 1.5F));

        PartDefinition leg_left = root.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(22, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -2.0F, 0.5F));

        PartDefinition foot_left = leg_left.addOrReplaceChild("foot_left", CubeListBuilder.create().texOffs(18, 19).addBox(-0.5F, 1.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -0.5F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(Bird entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idleAnimationState, BirdAnimations.idle, ageInTicks, 1f);
        this.animate(entity.dyingAnimationState, BirdAnimations.death, ageInTicks, 1f);
        this.animateWalk(BirdAnimations.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public @NotNull ModelPart root() {
        return root;
    }
}
