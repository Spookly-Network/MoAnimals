package net.spookly.moanimals.client.renderer;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.client.model.SnailModel;
import net.spookly.moanimals.client.renderer.entity.state.SnailRenderState;
import net.spookly.moanimals.entity.Snail;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnailRenderer extends MobRenderer<Snail, SnailRenderState, SnailModel> {
    public SnailRenderer(EntityRendererProvider.Context context) {
        super(context, new SnailModel(context.bakeLayer(SnailModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public @NotNull SnailRenderState createRenderState() {
        return new SnailRenderState();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SnailRenderState snailRenderState) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/snail.png");
    }
}
