package net.spookly.moanimals.client.renderer;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.client.model.PenguinModel;
import net.spookly.moanimals.client.renderer.entity.state.PenguinRenderState;
import net.spookly.moanimals.entity.Penguin;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PenguinRenderer extends MobRenderer<Penguin, PenguinRenderState, PenguinModel> {
    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel(context.bakeLayer(PenguinModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public @NotNull PenguinRenderState createRenderState() {
        return new PenguinRenderState();
    }

    @Override
    public void render(PenguinRenderState livingEntityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (livingEntityRenderState.isBaby) {
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }
        super.render(livingEntityRenderState, poseStack, multiBufferSource, i);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(PenguinRenderState livingEntityRenderState) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/penguin.png");
    }

    @Override
    public void extractRenderState(Penguin livingEntity, PenguinRenderState livingEntityRenderState, float f) {
        super.extractRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.flapAnimationState.copyFrom(livingEntity.flapAnimationState);
        livingEntityRenderState.idleAnimationState.copyFrom(livingEntity.idleAnimationState);
    }
}
