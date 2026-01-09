package net.spookly.moanimals.client.model;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.spookly.moanimals.client.animations.DuckAnimations;
import net.spookly.moanimals.client.renderer.entity.state.DuckRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DuckModel extends EntityModel<DuckRenderState> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "duck"), "main");
    private final ModelPart body;
    private final ModelPart head;

    public DuckModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));

        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(10, 0).addBox(-2.0F, -2.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, 5.0F, -0.7854F, 0.0F, 0.0F));
        body.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(12, 10).addBox(-1.0F, 0.0F, -2.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -2.0F, 1.5F));
        body.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, 0.0F, -2.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -2.0F, 1.5F));

        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-1.5F, -2.0F, -3.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(9, 9).addBox(0.0F, -5.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -2.0F, -0.5F));

        PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));
        legs.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(4, 18).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 1.0F, 2.0F));
        legs.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(0, 18).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 1.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(DuckRenderState entityRenderState) {
        super.setupAnim(entityRenderState);
        this.applyHeadRotation(entityRenderState.xRot, entityRenderState.yRot);
        this.animateWalk(DuckAnimations.walk, entityRenderState.walkAnimationPos, entityRenderState.walkAnimationSpeed, 2f, 1f);
    }

    private void applyHeadRotation(float xRot, float yRot) {
        xRot = Mth.clamp(xRot, -30f, 30f);
        yRot = Mth.clamp(yRot, -25f, 45f);

        this.head.yRot = xRot * ((float)Math.PI / 180F);
        this.head.xRot = yRot * ((float)Math.PI / 180F);
    }
}
