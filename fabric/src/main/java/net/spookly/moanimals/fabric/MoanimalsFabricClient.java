package net.spookly.moanimals.fabric;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.spookly.moanimals.MoAnimalsClient;
import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.client.model.*;
import net.spookly.moanimals.client.renderer.*;
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
        registerBlockCutoutLayer();
    }

    void registerLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(DuckModel.LAYER_LOCATION, DuckModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CrocodileModel.LAYER_LOCATION, CrocodileModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(RacoonModel.LAYER_LOCATION, RacoonModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ButterflyModel.LAYER_LOCATION, ButterflyModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SnailModel.LAYER_LOCATION, SnailModel::createBodyLayer);
    }

    void registerEntityRenderers() {
        EntityRendererRegistry.register(MoAnimalEntityTypes.DUCK.get(), DuckRenderer::new);
        EntityRendererRegistry.register(MoAnimalEntityTypes.CROCODILE.get(), CrocodileRenderer::new);
        EntityRendererRegistry.register(MoAnimalEntityTypes.RACOON.get(), RacoonRenderer::new);
        EntityRendererRegistry.register(MoAnimalEntityTypes.BUTTERFLY.get(), ButterflyRenderer::new);
        EntityRendererRegistry.register(MoAnimalEntityTypes.SNAIL.get(), SnailRenderer::new);
    }

    void registerColorProviders() {
        ColorProviderRegistry.BLOCK.register((blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null
                ? BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos)
                : FoliageColor.getDefaultColor(), MoAnimalBlocks.DUCKWEED.get());
    }

    void registerBlockCutoutLayer() {
        // 1.21.6+ BlockRenderLayerMap.putBlock(MoAnimalBlocks.DUCKWEED.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.INSTANCE.putBlock(MoAnimalBlocks.DUCKWEED.get(), RenderType.cutout());
    }
}
