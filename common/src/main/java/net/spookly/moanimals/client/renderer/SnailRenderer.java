package net.spookly.moanimals.client.renderer;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.spookly.moanimals.client.model.SnailModel;
import net.spookly.moanimals.entity.Snail;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnailRenderer extends MobRenderer<Snail, SnailModel<Snail>> {
    public SnailRenderer(EntityRendererProvider.Context context) {
        super(context, new SnailModel<>(context.bakeLayer(SnailModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(Snail entity) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/snail.png");
    }

}
