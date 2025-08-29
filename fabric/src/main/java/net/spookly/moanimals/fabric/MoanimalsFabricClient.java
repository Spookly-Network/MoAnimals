package net.spookly.moanimals.fabric;

import net.spookly.moanimals.MoAnimalsClient;
import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.client.model.ButterflyModel;
import net.spookly.moanimals.client.model.CrocodileModel;
import net.spookly.moanimals.client.model.DuckModel;
import net.spookly.moanimals.client.model.RacoonModel;
import net.spookly.moanimals.client.renderer.ButterflyRenderer;
import net.spookly.moanimals.client.renderer.CrocodileRenderer;
import net.spookly.moanimals.client.renderer.DuckRenderer;
import net.spookly.moanimals.client.renderer.RacoonRenderer;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public final class MoanimalsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        MoAnimalsClient.init();

        registerLayerDefinitions();
        registerEntityRenderers();
        registerColorProviders();
    }

    void registerLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(DuckModel.LAYER_LOCATION, DuckModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CrocodileModel.LAYER_LOCATION, CrocodileModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(RacoonModel.LAYER_LOCATION, RacoonModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ButterflyModel.LAYER_LOCATION, ButterflyModel::createBodyLayer);
    }

    void registerEntityRenderers() {
        EntityRendererRegistry.register(MoAnimalEntityTypes.DUCK.get(), DuckRenderer::new);
        EntityRendererRegistry.register(MoAnimalEntityTypes.CROCODILE.get(), CrocodileRenderer::new);
        EntityRendererRegistry.register(MoAnimalEntityTypes.RACOON.get(), RacoonRenderer::new);
        EntityRendererRegistry.register(MoAnimalEntityTypes.BUTTERFLY.get(), ButterflyRenderer::new);
    }

    void registerColorProviders() {
        ColorProviderRegistry.BLOCK.register((blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null
                ? BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos)
                : FoliageColor.getDefaultColor(), MoAnimalBlocks.DUCKWEED.get());
    }
}
