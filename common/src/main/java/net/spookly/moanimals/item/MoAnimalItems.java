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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;

public class MoAnimalItems {

    public static final Supplier<Item> BREADCRUMBS = CommonPlatformHelper.registerItem("breadcrumb", () -> new Item(new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB)));
    public static final Supplier<Item> DUCK_EGG = CommonPlatformHelper.registerItem("duck_egg", () -> new EggItem(new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB)));
    public static final Supplier<SpawnEggItem> RACCOON_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("raccoon_spawn_egg", MoAnimalEntityTypes.RACOON, 1, 256418, new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB));
    public static final Supplier<SpawnEggItem> DUCK_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("duck_spawn_egg", MoAnimalEntityTypes.DUCK, 11629312, 9026603, new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB));
    public static final Supplier<SpawnEggItem> CROCODILE_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("crocodile_spawn_egg", MoAnimalEntityTypes.CROCODILE, 3883039, 7042359, new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB));
    public static final Supplier<SpawnEggItem> BUTTERFLY_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("butterfly_spawn_egg", MoAnimalEntityTypes.BUTTERFLY, 13673122, 16758783, new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB));
    public static final Supplier<SpawnEggItem> SNAIL_SPAWN_EGG = CommonPlatformHelper.registerSpawnEggItem("snail_spawn_egg", MoAnimalEntityTypes.SNAIL, 7301692, 7027994, new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB));

    public static final Supplier<Item> SNAIL_BUCKET = CommonPlatformHelper.registerItem("snail_bucket",
            () -> new SnailMobBucketItem(
                    MoAnimalEntityTypes.SNAIL.get(),
                    Fluids.WATER,
                    SoundEvents.BUCKET_EMPTY,
                    new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
                            .arch$tab(MoAnimalsItemGroups.MOD_TAB)
            ));

    // Block Items
    public static final RegistrySupplier<Item> DUCKWEED = registerItem("duckweed", () -> new PlaceOnWaterBlockItem(MoAnimalBlocks.DUCKWEED.get(), new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB)));

    public static void registerBlockItem(final String path, Block block) {
        registerItem(path, () -> new BlockItem(block, new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB.get())));
    }

    public static void registerBlockItem(final String path, BlockItem blockItem) {
        registerItem(path, blockItem::asItem);
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
}
