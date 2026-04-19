package net.spookly.moanimals.fabric.datagen;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.Optional;

import dev.architectury.registry.registries.RegistrySupplier;
import net.spookly.moanimals.item.MoAnimalItems;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

public class MoAnimalsModelProvider extends FabricModelProvider {


    private static final ModelTemplate SPAWN_EGG = new ModelTemplate(Optional.of(ResourceLocation.parse("item/template_spawn_egg")), Optional.empty());

    public MoAnimalsModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(MoAnimalItems.DUCKWEED.get(), ModelTemplates.FLAT_ITEM);
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
        itemModelGenerators.generateFlatItem(MoAnimalItems.BIRD_SPAWN_EGG.get(), SPAWN_EGG);
        itemModelGenerators.generateFlatItem(MoAnimalItems.SEAGULL_SPAWN_EGG.get(), SPAWN_EGG);
    }
}
