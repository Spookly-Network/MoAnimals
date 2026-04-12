package net.spookly.moanimals;

import static net.spookly.moanimals.Moanimals.LOGGER;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.client.model.*;
import net.spookly.moanimals.client.renderer.*;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;

public final class MoAnimalsClient {
    private MoAnimalsClient() {
    }

    public static void init() {
        LOGGER.info("Initializing client");
    }

    public static void registerEntityRenderers(EntityRendererRegistrar registerer) {
        registerer.register(MoAnimalEntityTypes.DUCK.get(), DuckRenderer::new);
        registerer.register(MoAnimalEntityTypes.CROCODILE.get(), CrocodileRenderer::new);
        registerer.register(MoAnimalEntityTypes.RACOON.get(), RacoonRenderer::new);
        registerer.register(MoAnimalEntityTypes.BUTTERFLY.get(), ButterflyRenderer::new);
        registerer.register(MoAnimalEntityTypes.SNAIL.get(), SnailRenderer::new);
        registerer.register(MoAnimalEntityTypes.PENGUIN.get(), PenguinRenderer::new);
        registerer.register(MoAnimalEntityTypes.OSTRICH.get(), OstrichRenderer::new);
        registerer.register(MoAnimalEntityTypes.BIRD.get(), BirdRenderer::new);
    }

    public static void registerLayerDefinitions(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> registerer) {
        registerer.accept(DuckModel.LAYER_LOCATION, DuckModel::createBodyLayer);
        registerer.accept(CrocodileModel.LAYER_LOCATION, CrocodileModel::createBodyLayer);
        registerer.accept(RacoonModel.LAYER_LOCATION, RacoonModel::createBodyLayer);
        registerer.accept(ButterflyModel.LAYER_LOCATION, ButterflyModel::createBodyLayer);
        registerer.accept(SnailModel.LAYER_LOCATION, SnailModel::createBodyLayer);
        registerer.accept(PenguinModel.LAYER_LOCATION, PenguinModel::createBodyLayer);
        registerer.accept(OstrichModel.LAYER_LOCATION, OstrichModel::createBodyLayer);
        registerer.accept(BirdModel.LAYER_LOCATION, BirdModel::createBodyLayer);
    }

    public static void registerBlockColorHandlers(BiConsumer<BlockColor, Block[]> registerer) {
        BlockColor duckweedColor = (state, level, pos, tintIndex) -> level != null && pos != null
                ? BiomeColors.getAverageFoliageColor(level, pos)
                : FoliageColor.getDefaultColor();
        registerer.accept(duckweedColor, new Block[]{MoAnimalBlocks.DUCKWEED.get()});
    }

    public static void registerCutoutBlocks(Consumer<Block> registerer) {
        registerer.accept(MoAnimalBlocks.DUCKWEED.get());
        registerer.accept(MoAnimalBlocks.JAR.get());
    }

    @FunctionalInterface
    public interface EntityRendererRegistrar {
        <T extends Entity> void register(EntityType<T> type, EntityRendererProvider<T> provider);
    }
}
