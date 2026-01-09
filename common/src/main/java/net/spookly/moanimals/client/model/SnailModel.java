package net.spookly.moanimals.client.model;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.spookly.moanimals.client.animations.SnailAnimations;
import net.spookly.moanimals.client.renderer.entity.state.SnailRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class SnailModel extends EntityModel<SnailRenderState> {

    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "snail"), "main");
    private final ModelPart root;
    private final ModelPart shell;
    private final ModelPart body;

    public SnailModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.shell = this.root.getChild("shell");
        this.body = this.root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition shell = root.addOrReplaceChild("shell", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        shell.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -4.7F, -0.328F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.1277F, -1.672F, -0.1309F, 0.0F, 0.0F));
        shell.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, -1.1277F, -1.672F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(16, 8).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition sensor_left = body.addOrReplaceChild("sensor_left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        sensor_left.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 13).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.4837F, -3.5F, 0.0F, 0.0F, -0.4363F));

        PartDefinition sensor_right = body.addOrReplaceChild("sensor_right", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        sensor_right.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 11).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -1.4837F, -3.5F, 0.0F, 0.0F, 0.4363F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(SnailRenderState entityRenderState) {
        super.setupAnim(entityRenderState);
        this.animateWalk(SnailAnimations.walk, entityRenderState.walkAnimationPos, entityRenderState.walkAnimationSpeed, 2f, 2.5f);
    }
}
