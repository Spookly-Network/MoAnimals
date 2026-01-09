package net.spookly.moanimals.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.client.model.RacoonModel;
import net.spookly.moanimals.client.renderer.entity.state.RacoonRenderState;
import net.spookly.moanimals.entity.Racoon;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RacoonRenderer extends MobRenderer<Racoon, RacoonRenderState, RacoonModel> {

    public RacoonRenderer(EntityRendererProvider.Context context) {
        super(context, new RacoonModel(context.bakeLayer(RacoonModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(RacoonRenderState livingEntityRenderState) {
        return livingEntityRenderState.texture;
    }

    @Override
    public void render(RacoonRenderState livingEntityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        super.render(livingEntityRenderState, poseStack, multiBufferSource, i);
        if (livingEntityRenderState.isBaby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
    }

    @Override
    public @NotNull RacoonRenderState createRenderState() {
        return new RacoonRenderState();
    }

    @Override
    public void extractRenderState(Racoon livingEntity, RacoonRenderState livingEntityRenderState, float f) {
        super.extractRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.breathAnimationState.copyFrom(livingEntity.breathAnimationState);
        livingEntityRenderState.idleAnimationState.copyFrom(livingEntity.idleAnimationState);
        livingEntityRenderState.sleepAnimationState.copyFrom(livingEntity.sleepAnimationState);
        livingEntityRenderState.texture = livingEntity.getTexture();
    }
}
