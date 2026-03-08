package net.spookly.moanimals.neoforge.datagen;


import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import dev.architectury.registry.registries.RegistrySupplier;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.item.MoAnimalItems;
import net.spookly.moanimals.registry.MoAnimalsRegistries;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MoAnimalsModelProvider extends ModelProvider {

    private static final ModelTemplate SPAWN_EGG = new ModelTemplate(Optional.of(ResourceLocation.parse("item/egg")), Optional.empty());

    public MoAnimalsModelProvider(PackOutput arg) {
        super(arg, MOD_ID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        registerItemModels(itemModels);
        registerBlockModels(blockModels);
    }

    private void registerItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(MoAnimalItems.BREADCRUMBS.get(), ModelTemplates.FLAT_ITEM);
//        itemModelGenerators.generateFlatItem(MoAnimalItems.DUCKWEED.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(MoAnimalItems.DUCK_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(MoAnimalItems.SNAIL_BUCKET.get(), ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(MoAnimalItems.CROCODILE_SPAWNEGG.get(), SPAWN_EGG);
        itemModelGenerators.generateFlatItem(MoAnimalItems.RACCOON_SPAWNEGG.get(), SPAWN_EGG);
        itemModelGenerators.generateFlatItem(MoAnimalItems.DUCK_SPAWNEGG.get(), SPAWN_EGG);
        itemModelGenerators.generateFlatItem(MoAnimalItems.BUTTERFLY_SPAWNEGG.get(), SPAWN_EGG);
        itemModelGenerators.generateFlatItem(MoAnimalItems.SNAIL_SPAWN_EGG.get(), SPAWN_EGG);
        itemModelGenerators.generateFlatItem(MoAnimalItems.PENGUIN_SPAWN_EGG.get(), SPAWN_EGG);
        itemModelGenerators.generateFlatItem(MoAnimalItems.OSTRICH_SPAWN_EGG.get(), SPAWN_EGG);
    }

    private void registerBlockModels(BlockModelGenerators blockModels) {
        blockItem(MoAnimalBlocks.OSTRICH_EGG, blockModels);
        this.createDuckweed(blockModels);
    }

    private void blockItem(RegistrySupplier<Block> block, BlockModelGenerators blockModels) {
        blockModels.registerSimpleItemModel(block.get(), block.getId());
    }

    public void createDuckweed(BlockModelGenerators blockModels) {
        Item DUCKWEED_ITEM = MoAnimalItems.DUCKWEED.get();
        Block DUCKWEED_BLOCK = MoAnimalBlocks.DUCKWEED.get();
        
        ResourceLocation resourcelocation = blockModels.createFlatItemModelWithBlockTexture(DUCKWEED_ITEM, DUCKWEED_BLOCK);
        blockModels.registerSimpleTintedItemModel(DUCKWEED_BLOCK, resourcelocation, ItemModelUtils.constantTint(-9321636));
        net.minecraft.client.renderer.block.model.Variant variant = BlockModelGenerators.plainModel(ModelLocationUtils.getModelLocation(DUCKWEED_BLOCK));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(DUCKWEED_BLOCK, BlockModelGenerators.createRotatedVariants(variant)));
    }

    @Override
    protected @NotNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return toStream(MoAnimalsRegistries.BLOCKS.iterator()).filter(x -> !x.is(MoAnimalBlocks.OSTRICH_EGG));
    }

    @Override
    protected @NotNull Stream<? extends Holder<Item>> getKnownItems() {
        return toStream(MoAnimalsRegistries.ITEMS.iterator()).filter(x -> !x.is(MoAnimalItems.OSTRICH_EGG));
    }

    private <T> Stream<T> toStream(Iterator<T> iterator) {
        final var input = Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED);
        return StreamSupport.stream(input, false);
    }

    //    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
//        return withExistingParent(item.getId().getPath(),
//                ResourceLocation.parse("item/handheld")).texture("layer0",
//                ResourceLocation.fromNamespaceAndPath(MOD_ID,"item/" + item.getId().getPath()));
//    }
}
