package net.spookly.moanimals.neoforge;

import net.minecraft.client.renderer.entity.EntityRenderers;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.client.renderer.CrocodileRenderer;
import net.spookly.moanimals.client.renderer.DuckRenderer;
import net.spookly.moanimals.client.renderer.RacoonRenderer;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;

@EventBusSubscriber(modid = Moanimals.MOD_ID, value = Dist.CLIENT)
public class MoAnimalsNeoForgeClient {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        EntityRenderers.register(MoAnimalEntityTypes.DUCK.get(), DuckRenderer::new);
        EntityRenderers.register(MoAnimalEntityTypes.CROCODILE.get(), CrocodileRenderer::new);
        EntityRenderers.register(MoAnimalEntityTypes.RACOON.get(), RacoonRenderer::new);
    }
}
