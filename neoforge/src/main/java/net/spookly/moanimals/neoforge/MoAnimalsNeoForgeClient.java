package net.spookly.moanimals.neoforge;

import net.spookly.moanimals.MoAnimalsClient;
import net.spookly.moanimals.Moanimals;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Moanimals.MOD_ID, value = Dist.CLIENT)
public class MoAnimalsNeoForgeClient {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        MoAnimalsClient.init();
        event.enqueueWork(() -> {
            MoAnimalsClient.registerEntityRenderers(new MoAnimalsClient.EntityRendererRegistrar() {
                @Override
                public <T extends net.minecraft.world.entity.Entity> void register(net.minecraft.world.entity.EntityType<T> type, net.minecraft.client.renderer.entity.EntityRendererProvider<T> provider) {
                    EntityRenderers.register(type, provider);
                }
            });
            MoAnimalsClient.registerCutoutBlocks(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout()));
        });
    }

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        MoAnimalsClient.registerBlockColorHandlers((color, blocks) -> event.register(color, blocks));
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        MoAnimalsClient.registerLayerDefinitions(event::registerLayerDefinition);
    }
}
