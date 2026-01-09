package net.spookly.moanimals.client.renderer;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.client.model.CrocodileModel;
import net.spookly.moanimals.client.renderer.entity.state.CrocodileRenderState;
import net.spookly.moanimals.entity.Crocodile;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CrocodileRenderer extends MobRenderer<Crocodile, CrocodileRenderState, CrocodileModel> {
    public CrocodileRenderer(EntityRendererProvider.Context context) {
        super(context, new CrocodileModel(context.bakeLayer(CrocodileModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull CrocodileRenderState createRenderState() {
        return new CrocodileRenderState();
    }

    @Override
    public void render(CrocodileRenderState livingEntityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (livingEntityRenderState.isBaby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(livingEntityRenderState, poseStack, multiBufferSource, i);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(CrocodileRenderState livingEntityRenderState) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/crocodile.png");
    }

    @Override
    public void extractRenderState(Crocodile livingEntity, CrocodileRenderState livingEntityRenderState, float f) {
        super.extractRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.attackAnimation.copyFrom(livingEntity.attackAnimation);
        livingEntityRenderState.idleAnimationState.copyFrom(livingEntity.idleAnimationState);
        livingEntityRenderState.swimAnimation.copyFrom(livingEntity.swimAnimation);
    }
}
