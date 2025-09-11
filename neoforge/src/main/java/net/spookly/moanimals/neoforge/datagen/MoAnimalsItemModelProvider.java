package net.spookly.moanimals.neoforge.datagen;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.LinkedHashMap;
import java.util.Optional;

import net.spookly.moanimals.item.MoAnimalItems;

import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.armortrim.TrimMaterial;

import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class MoAnimalsItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();

    public MoAnimalsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(MoAnimalItems.BREADCRUMBS.get());
        basicItem(MoAnimalItems.DUCKWEED.get());
        basicItem(MoAnimalItems.DUCK_EGG.get());
        basicItem(MoAnimalItems.SNAIL_BUCKET.get());

        spawnEggItem(MoAnimalItems.CROCODILE_SPAWNEGG.get());
        spawnEggItem(MoAnimalItems.RACCOON_SPAWNEGG.get());
        spawnEggItem(MoAnimalItems.DUCK_SPAWNEGG.get());
        spawnEggItem(MoAnimalItems.BUTTERFLY_SPAWNEGG.get());
        spawnEggItem(MoAnimalItems.SNAIL_SPAWN_EGG.get());
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(MOD_ID,"item/" + item.getId().getPath()));
    }
}
