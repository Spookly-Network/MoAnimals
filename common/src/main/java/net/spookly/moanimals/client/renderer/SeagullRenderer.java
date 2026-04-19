package net.spookly.moanimals.client.renderer;

import net.spookly.moanimals.client.model.SeagullModel;
import net.spookly.moanimals.entity.Seagull;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SeagullRenderer extends MobRenderer<Seagull, SeagullModel<Seagull>> {
    public SeagullRenderer(EntityRendererProvider.Context context) {
        super(context, new SeagullModel<>(context.bakeLayer(SeagullModel.LAYER_LOCATION)), .5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Seagull entity) {
        return ResourceLocation.fromNamespaceAndPath("moanimals", "textures/entity/seagull.png");
    }
}
