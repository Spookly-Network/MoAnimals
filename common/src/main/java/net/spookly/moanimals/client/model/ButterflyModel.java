package net.spookly.moanimals.client.model;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.spookly.moanimals.client.animations.ButterflyAnimations;
import net.spookly.moanimals.client.renderer.entity.state.ButterflyRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ButterflyModel extends EntityModel<ButterflyRenderState> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "butterfly"), "main");
    private final ModelPart root;
    private final ModelPart body;

    public ButterflyModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -4.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition main = body.addOrReplaceChild("main", CubeListBuilder.create().texOffs(4, 10).addBox(-0.5F, -1.0F, 1.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wing_left = body.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(-10, 0).mirror().addBox(-9.5F, 0.0F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, -0.5F, 4.0F));

        PartDefinition wing_right = body.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(-10, 0).addBox(-0.5F, 0.0F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.5F, 4.0F));

        PartDefinition sensor = body.addOrReplaceChild("sensor", CubeListBuilder.create().texOffs(-4, 11).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 1.5F, -0.1309F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(ButterflyRenderState entityRenderState) {
        super.setupAnim(entityRenderState);
        this.animateWalk(ButterflyAnimations.walk, entityRenderState.walkAnimationPos, entityRenderState.walkAnimationSpeed, 2f, 1f);
        this.animate(entityRenderState.idleAnimationState, ButterflyAnimations.idle, entityRenderState.ageInTicks, 1f);
        this.animate(entityRenderState.sitAnimationState, ButterflyAnimations.sit, entityRenderState.ageInTicks, 1f);
        this.animate(entityRenderState.flyAnimationState, ButterflyAnimations.walk, entityRenderState.ageInTicks, 1f);
    }

//    @Override
//    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
//        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
//    }

//    @Override
//    public ModelPart root() {
//        return root;
//    }
}
