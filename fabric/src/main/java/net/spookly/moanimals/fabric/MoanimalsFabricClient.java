package net.spookly.moanimals.fabric;

import net.spookly.moanimals.MoAnimalsClient;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public final class MoanimalsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MoAnimalsClient.init();

        MoAnimalsClient.registerLayerDefinitions(EntityModelLayerRegistry::registerModelLayer);
        MoAnimalsClient.registerEntityRenderers((type, provider) ->
                EntityRendererRegistry.register(type, context -> provider.create((EntityRendererProvider.Context) context)));
        MoAnimalsClient.registerBlockColorHandlers((color, blocks) -> ColorProviderRegistry.BLOCK.register(color, blocks));
        MoAnimalsClient.registerCutoutBlocks(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout()));
    }
}
