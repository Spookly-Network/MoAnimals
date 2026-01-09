package net.spookly.moanimals.item;

import static net.spookly.moanimals.Moanimals.LOGGER;
import static net.spookly.moanimals.Moanimals.MOD_ID;
import static net.spookly.moanimals.registry.MoAnimalsRegistries.ITEMS;

import java.util.function.Supplier;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrySupplier;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.core.CommonPlatformHelper;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.mixin.ComposterBlockMixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;

public class MoAnimalItems {

    public static final Supplier<Item> BREADCRUMBS = CommonPlatformHelper.registerItem("breadcrumb", () -> new Item(basicItemProperties("breadcrumb")));
    public static final Supplier<Item> DUCK_EGG = CommonPlatformHelper.registerItem("duck_egg", () -> new EggItem(basicItemProperties("duck_egg")));
    public static final Supplier<SpawnEggItem> RACCOON_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("raccoon_spawn_egg", MoAnimalEntityTypes.RACOON, 1, 256418, basicItemProperties("raccoon_spawn_egg"));
    public static final Supplier<SpawnEggItem> DUCK_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("duck_spawn_egg", MoAnimalEntityTypes.DUCK, 11629312, 9026603, basicItemProperties("duck_spawn_egg"));
    public static final Supplier<SpawnEggItem> CROCODILE_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("crocodile_spawn_egg", MoAnimalEntityTypes.CROCODILE, 3883039, 7042359, basicItemProperties("crocodile_spawn_egg"));
    public static final Supplier<SpawnEggItem> BUTTERFLY_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("butterfly_spawn_egg", MoAnimalEntityTypes.BUTTERFLY, 13673122, 16758783, basicItemProperties("butterfly_spawn_egg"));
    public static final Supplier<SpawnEggItem> SNAIL_SPAWN_EGG = CommonPlatformHelper.registerSpawnEggItem("snail_spawn_egg", MoAnimalEntityTypes.SNAIL, 7301692, 7027994, basicItemProperties("snail_spawn_egg"));
    public static final Supplier<SpawnEggItem> PENGUIN_SPAWN_EGG = CommonPlatformHelper.registerSpawnEggItem("penguin_spawn_egg", MoAnimalEntityTypes.PENGUIN, 874364, 128543, basicItemProperties("penguin_spawn_egg"));
    public static final Supplier<SpawnEggItem> OSTRICH_SPAWN_EGG = CommonPlatformHelper.registerSpawnEggItem("ostrich_spawn_egg", MoAnimalEntityTypes.OSTRICH, 16777215, 16222215, basicItemProperties("ostrich_spawn_egg"));

    public static final Supplier<Item> SNAIL_BUCKET = CommonPlatformHelper.registerItem("snail_bucket",
            () -> new SnailMobBucketItem(
                    MoAnimalEntityTypes.SNAIL.get(),
                    Fluids.WATER,
                    SoundEvents.BUCKET_EMPTY,
                   basicItemProperties("snail_bucket")
                           .stacksTo(1)
                           .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
            ));

    // Block Items
    public static final RegistrySupplier<Item> DUCKWEED = registerItem("duckweed", () -> new PlaceOnWaterBlockItem(MoAnimalBlocks.DUCKWEED.get(), new Item.Properties()
            .arch$tab(MoAnimalsItemGroups.MOD_TAB)
            .setId(moanimalsItemId("duckweed"))
    ));
    public static final RegistrySupplier<Item> OSTRICH_EGG = registerBlockItem("ostrich_egg", MoAnimalBlocks.OSTRICH_EGG);


    public static RegistrySupplier<Item> registerBlockItem(final String path, Supplier<Block> block) {
        return registerItem(path, () -> new BlockItem(block.get(), new Item.Properties()
                .arch$tab(MoAnimalsItemGroups.MOD_TAB)
                .setId(moanimalsItemId(path))
        ));
    }

    public static RegistrySupplier<Item> registerBlockItem(final String path, BlockItem blockItem) {
        return registerItem(path, blockItem::asItem);
    }

    public static RegistrySupplier<Item> registerItem(final String path, Supplier<Item> item) {
        return ITEMS.register(ResourceLocation.fromNamespaceAndPath(MOD_ID, path), item);
    }

    public static void registerComposerItems() {
        ComposterBlockMixin.invokeAdd(0.65F, DUCKWEED.get());
    }

    public static void init() {
        LOGGER.info("Registering items");
        ITEMS.register();

        if (Platform.isFabric()) {
            registerComposerItems();
        }
    }

    private static Item.Properties basicItemProperties(String id) {
        return new Item.Properties()
                .arch$tab(MoAnimalsItemGroups.MOD_TAB)
                .setId(moanimalsItemId(id));
    }
    public static ResourceKey<Item> moanimalsItemId(String string) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }
}
