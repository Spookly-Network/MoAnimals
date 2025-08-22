package net.spookly.moanimals.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.spookly.moanimals.MoAnimalsClient;
import net.spookly.moanimals.client.model.CrocodileModel;
import net.spookly.moanimals.client.model.DuckModel;
import net.spookly.moanimals.client.model.RacoonModel;
import net.spookly.moanimals.client.renderer.CrocodileRenderer;
import net.spookly.moanimals.client.renderer.DuckRenderer;
import net.spookly.moanimals.client.renderer.RacoonRenderer;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;

public final class MoanimalsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MoAnimalsClient.init();

        registerLayerDefinitions();
        registerEntityRenderers();
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }

    void registerLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(DuckModel.LAYER_LOCATION, DuckModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CrocodileModel.LAYER_LOCATION, CrocodileModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(RacoonModel.LAYER_LOCATION, RacoonModel::createBodyLayer);
    }

    void registerEntityRenderers() {
        EntityRendererRegistry.register(MoAnimalEntityTypes.DUCK.get(), DuckRenderer::new);
        EntityRendererRegistry.register(MoAnimalEntityTypes.CROCODILE.get(), CrocodileRenderer::new);
        EntityRendererRegistry.register(MoAnimalEntityTypes.RACOON.get(), RacoonRenderer::new);
    }

}
