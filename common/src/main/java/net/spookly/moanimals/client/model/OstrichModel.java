package net.spookly.moanimals.client.model;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.spookly.moanimals.client.animations.OstrichAnimations;
import net.spookly.moanimals.client.renderer.entity.state.OstrichRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class OstrichModel extends EntityModel<OstrichRenderState> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ostrich"), "main");
    private final ModelPart root;
    private final ModelPart torso;
    private final ModelPart neck;
    private final ModelPart skull;

    public OstrichModel(ModelPart root) {
        super(root);

        this.root = root.getChild("root");
        this.torso = this.root.getChild("torso");
        this.neck = this.torso.getChild("neck");
        this.skull = this.neck.getChild("skull");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition torso = root.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -12.0F, -8.0F, 12.0F, 12.0F, 18.0F, new CubeDeformation(0.0F))
            .texOffs(0, 30).addBox(-6.0F, 0.0F, -8.0F, 12.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 2.0F));

        torso.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(16, 50).addBox(0.0F, -4.0F, 0.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(60, 45).addBox(1.0F, -4.0F, 10.0F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -20.0F, -1.0F));
        torso.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(38, 50).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(60, 33).addBox(-1.0F, -4.0F, 10.0F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -20.0F, -1.0F));

        PartDefinition neck = torso.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.0F, -6.0F));
        neck.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(60, 57).addBox(-2.0F, -18.0F, -5.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(0, 50).addBox(-2.0F, -20.0F, -2.0F, 4.0F, 25.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -5.0F));

        PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, 12.0F));
        tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(60, 13).addBox(-7.0F, -6.0F, -1.0F, 8.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
        .texOffs(60, 24).addBox(-7.0F, -6.0F, 4.0F, 8.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 3.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition hip = root.addOrReplaceChild("hip", CubeListBuilder.create(), PartPose.offset(0.0F, -14.0F, 2.0F));
        PartDefinition leg_right = hip.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(60, 62).addBox(-9.0F, 0.0F, 0.0F, 2.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 0.0F));
        leg_right.addOrReplaceChild("foot_right", CubeListBuilder.create().texOffs(30, 68).addBox(-10.0F, 14.0F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition leg_left = hip.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(64, 62).addBox(7.0F, 0.0F, 0.0F, 2.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, 0.0F));
        leg_left.addOrReplaceChild("foot_left", CubeListBuilder.create().texOffs(16, 68).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 14.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(OstrichRenderState entityRenderState) {
        super.setupAnim(entityRenderState);
        this.applyHeadRotation(entityRenderState.xRot, entityRenderState.yRot);
        this.animateWalk(OstrichAnimations.walk, entityRenderState.walkAnimationPos, entityRenderState.walkAnimationSpeed, 2f, 2.5f);
    }

    private void applyHeadRotation(float xRot, float yRot) {
        xRot = Mth.clamp(xRot, -30f, 30f);
        yRot = Mth.clamp(yRot, -25f, 45f);

        this.skull.yRot = xRot * ((float)Math.PI / 180F);
        this.skull.xRot = yRot * ((float)Math.PI / 180F);
    }
}
