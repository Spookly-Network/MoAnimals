package net.spookly.moanimals.client.renderer;

import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.client.model.ButterflyModel;
import net.spookly.moanimals.client.renderer.entity.state.ButterflyRenderState;
import net.spookly.moanimals.entity.Butterfly;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ButterflyRenderer extends MobRenderer<Butterfly, ButterflyRenderState, ButterflyModel> {

    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel(context.bakeLayer(ButterflyModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public @NotNull ButterflyRenderState createRenderState() {
        return new ButterflyRenderState();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ButterflyRenderState livingEntityRenderState) {
        return livingEntityRenderState.texture;
    }

    public void extractRenderState(Butterfly butterfly, ButterflyRenderState butterflyRenderState, float f) {
        super.extractRenderState(butterfly, butterflyRenderState, f);
        butterflyRenderState.texture = butterfly.getTexture();
    }
}
