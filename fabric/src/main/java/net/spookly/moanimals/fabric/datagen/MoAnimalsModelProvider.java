package net.spookly.moanimals.fabric.datagen;

import java.util.Optional;

import dev.architectury.registry.registries.RegistrySupplier;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.item.MoAnimalItems;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class MoAnimalsModelProvider extends FabricModelProvider {


    private static final ModelTemplate SPAWN_EGG = new ModelTemplate(Optional.of(ResourceLocation.parse("item/egg")), Optional.empty());

    public MoAnimalsModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
//        blockItem(MoAnimalBlocks.OSTRICH_EGG, blockModelGenerators);
        createDuckweed(blockModelGenerators);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(MoAnimalItems.BREADCRUMBS.get(), ModelTemplates.FLAT_ITEM);
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

    private void createDuckweed(BlockModelGenerators blockModels) {
        Item DUCKWEED_ITEM = MoAnimalItems.DUCKWEED.get();
        Block DUCKWEED_BLOCK = MoAnimalBlocks.DUCKWEED.get();

        ResourceLocation resourcelocation = blockModels.createFlatItemModelWithBlockTexture(DUCKWEED_ITEM, DUCKWEED_BLOCK);
        blockModels.registerSimpleTintedItemModel(DUCKWEED_BLOCK, resourcelocation, ItemModelUtils.constantTint(-9321636));
        net.minecraft.client.renderer.block.model.Variant variant = BlockModelGenerators.plainModel(ModelLocationUtils.getModelLocation(DUCKWEED_BLOCK));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(DUCKWEED_BLOCK, BlockModelGenerators.createRotatedVariants(variant)));
    }

    private void blockItem(RegistrySupplier<Block> block, BlockModelGenerators blockModels) {
        blockModels.registerSimpleItemModel(block.get(), block.getId());
    }

}
