package net.spookly.moanimals.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.spookly.moanimals.client.model.RacoonModel;
import net.spookly.moanimals.entity.Racoon;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RacoonRenderer extends MobRenderer<Racoon, RacoonModel<Racoon>> {

    public RacoonRenderer(EntityRendererProvider.Context context) {
        super(context, new RacoonModel<>(context.bakeLayer(RacoonModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(Racoon entity) {
        return entity.getTexture();
    }

    @Override
    public void render(Racoon livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (livingEntity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }
}
