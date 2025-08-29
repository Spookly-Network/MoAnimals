package net.spookly.moanimals.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.spookly.moanimals.client.model.ButterflyModel;
import net.spookly.moanimals.entity.Butterfly;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class ButterflyRenderer extends MobRenderer<Butterfly, ButterflyModel<Butterfly>> {
    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel<>(context.bakeLayer(ButterflyModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Butterfly entity) {
        return entity.getTexture();
    }

    @Override
    public void render(Butterfly livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }
}
