package net.spookly.moanimals.client.model;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.spookly.moanimals.client.animations.ButterflyAnimations;
import net.spookly.moanimals.entity.Butterfly;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class ButterflyModel<T extends Butterfly> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "butterfly"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart wing_right;
    private final ModelPart wing_left;

    public ButterflyModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.wing_right = this.root.getChild("wing_right");
        this.wing_left = this.root.getChild("wing_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wing_right = root.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(-7, 0).mirror().addBox(0.0F, 0.0F, -2.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, -1.0F, 0.0F));

        PartDefinition wing_left = root.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(-7, 0).addBox(-5.0F, 0.0F, -2.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(Butterfly entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(ButterflyAnimations.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, ButterflyAnimations.idle, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}
