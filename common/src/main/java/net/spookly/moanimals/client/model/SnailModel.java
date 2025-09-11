package net.spookly.moanimals.client.model;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.spookly.moanimals.client.animations.SnailAnimations;
import net.spookly.moanimals.entity.Snail;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class SnailModel<T extends Snail> extends HierarchicalModel<T> {

    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "snail"), "main");
    private final ModelPart root;
    private final ModelPart shell;
    private final ModelPart bone;
    private final ModelPart body;
    private final ModelPart sensor_left;
    private final ModelPart sensor_right;

    public SnailModel(ModelPart root) {
        this.root = root.getChild("root");
        this.shell = this.root.getChild("shell");
        this.bone = this.shell.getChild("bone");
        this.body = this.root.getChild("body");
        this.sensor_left = this.body.getChild("sensor_left");
        this.sensor_right = this.body.getChild("sensor_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition shell = root.addOrReplaceChild("shell", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = shell.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -4.7F, -0.328F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.1277F, -1.672F, -0.1309F, 0.0F, 0.0F));

        PartDefinition bone = shell.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, -1.1277F, -1.672F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(16, 8).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition sensor_left = body.addOrReplaceChild("sensor_left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r2 = sensor_left.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 13).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.4837F, -3.5F, 0.0F, 0.0F, -0.4363F));

        PartDefinition sensor_right = body.addOrReplaceChild("sensor_right", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r3 = sensor_right.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 11).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -1.4837F, -3.5F, 0.0F, 0.0F, 0.4363F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(Snail entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(SnailAnimations.walk, limbSwing, limbSwingAmount, 2f, 1f);
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
