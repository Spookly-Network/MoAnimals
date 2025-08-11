package net.spookly.moanimals.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.client.animations.CrocodileAnimations;
import net.spookly.moanimals.entity.Crocodile;

public class CrocodileModel<T extends Crocodile> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Moanimals.MOD_ID, "crocodile"), "main");
    private final ModelPart body;
    private final ModelPart corspe;
    private final ModelPart main;
    private final ModelPart tail;
    private final ModelPart front;
    private final ModelPart end;
    private final ModelPart head;
    private final ModelPart upper;
    private final ModelPart bottom;
    private final ModelPart leg_left_front;
    private final ModelPart leg_back_front;
    private final ModelPart leg_right_front;
    private final ModelPart leg_left_back;

    public CrocodileModel(ModelPart root) {
        this.body = root.getChild("body");
        this.corspe = this.body.getChild("corspe");
        this.main = this.corspe.getChild("main");
        this.tail = this.corspe.getChild("tail");
        this.front = this.tail.getChild("front");
        this.end = this.tail.getChild("end");
        this.head = this.corspe.getChild("head");
        this.upper = this.head.getChild("upper");
        this.bottom = this.head.getChild("bottom");
        this.leg_left_front = this.body.getChild("leg_left_front");
        this.leg_back_front = this.body.getChild("leg_back_front");
        this.leg_right_front = this.body.getChild("leg_right_front");
        this.leg_left_back = this.body.getChild("leg_left_back");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 7.0F));

        PartDefinition corspe = body.addOrReplaceChild("corspe", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition main = corspe.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -15.0F, 8.0F, 8.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(50, 57).addBox(2.0F, -9.0F, -13.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 57).addBox(-2.0F, -9.0F, -13.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 58).addBox(-2.0F, -9.0F, -10.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 58).addBox(2.0F, -9.0F, -10.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 58).addBox(2.0F, -9.0F, -7.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 58).addBox(-2.0F, -9.0F, -7.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(58, 55).addBox(-2.0F, -9.0F, -4.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(58, 58).addBox(2.0F, -9.0F, -4.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition tail = corspe.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -4.5F, 0.0F));

        PartDefinition front = tail.addOrReplaceChild("front", CubeListBuilder.create().texOffs(34, 23).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(58, 49).addBox(0.0F, -5.0F, 1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(58, 52).addBox(0.0F, -5.0F, 4.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition end = tail.addOrReplaceChild("end", CubeListBuilder.create().texOffs(0, 53).addBox(0.0F, -5.0F, 0.0F, 0.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 7.0F));

        PartDefinition head = corspe.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -15.0F));

        PartDefinition upper = head.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(46, 12).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(18, 37).addBox(-2.5F, -3.0F, -11.0F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(40, 57).addBox(-2.5F, -1.0F, -11.01F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition bottom = head.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -1.0F, -11.0F, 6.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(42, 37).addBox(2.51F, -3.0F, -11.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(46, 0).addBox(-2.51F, -3.0F, -11.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition leg_left_front = body.addOrReplaceChild("leg_left_front", CubeListBuilder.create().texOffs(0, 53).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -3.0F, -13.0F));

        PartDefinition cube_r1 = leg_left_front.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(42, 53).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 4.0F, 0.5F, 0.0F, 0.48F, 0.0F));

        PartDefinition leg_back_front = body.addOrReplaceChild("leg_back_front", CubeListBuilder.create().texOffs(30, 46).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.0F, -1.0F));

        PartDefinition cube_r2 = leg_back_front.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(42, 49).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 4.0F, 0.5F, 0.0F, -1.2217F, 0.0F));

        PartDefinition leg_right_front = body.addOrReplaceChild("leg_right_front", CubeListBuilder.create().texOffs(18, 46).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.0F, -13.0F));

        PartDefinition cube_r3 = leg_right_front.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(46, 19).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 4.0F, 0.5F, 0.0F, -0.2182F, 0.0F));

        PartDefinition leg_left_back = body.addOrReplaceChild("leg_left_back", CubeListBuilder.create().texOffs(12, 54).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -4.0F, -0.5F));

        PartDefinition cube_r4 = leg_left_back.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(24, 54).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 5.0F, 0.0F, 0.0F, 1.3526F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Crocodile entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);
        this.animateWalk(CrocodileAnimations.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45f);

        this.head.yRot = headYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return body;
    }
}
