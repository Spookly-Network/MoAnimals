package net.spookly.moanimals.neoforge;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.FoliageColor;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.block.MoAnimalBlocks;
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

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        // Parameters are the block's state, the level the block is in, the block's position, and the tint index.
        // The level and position may be null.
        event.register((state, level, pos, tintIndex) -> level != null && pos != null
                        ? BiomeColors.getAverageFoliageColor(level, pos)
                        : FoliageColor.getDefaultColor(),
                // A varargs of blocks to apply the tinting to
                MoAnimalBlocks.DUCKWEED.get());
    }
}
