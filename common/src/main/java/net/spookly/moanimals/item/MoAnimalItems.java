package net.spookly.moanimals.item;

import static net.spookly.moanimals.Moanimals.LOGGER;
import static net.spookly.moanimals.Moanimals.MOD_ID;
import static net.spookly.moanimals.registry.MoAnimalsRegistries.ITEMS;

import java.awt.*;
import java.util.function.Supplier;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrySupplier;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.core.CommonPlatformHelper;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.mixin.ComposterBlockMixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

public class MoAnimalItems {

    public static final RegistrySupplier<Item> BREADCRUMBS = registerItem("breadcrumbs", () -> new Item(new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB)));
    public static final Supplier<Item> DUCK_EGG = CommonPlatformHelper.registerItem("duck_egg", () -> new EggItem(new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB)));
    public static final Supplier<SpawnEggItem> RACCOON_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("raccoon_spawn_egg", MoAnimalEntityTypes.RACOON, 1, 256418);
    public static final Supplier<SpawnEggItem> DUCK_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("duck_spawn_egg", MoAnimalEntityTypes.DUCK, 11629312, 9026603);
    public static final Supplier<SpawnEggItem> CROCODILE_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("crocodile_spawn_egg", MoAnimalEntityTypes.CROCODILE, 3883039, 7042359);
    public static final Supplier<SpawnEggItem> BUTTERFLY_SPAWNEGG = CommonPlatformHelper.registerSpawnEggItem("butterfly_spawn_egg", MoAnimalEntityTypes.BUTTERFLY, 13673122, 16758783);

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
