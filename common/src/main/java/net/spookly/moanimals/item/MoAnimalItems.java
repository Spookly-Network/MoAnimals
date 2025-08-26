package net.spookly.moanimals.item;

import static net.spookly.moanimals.Moanimals.MOD_ID;
import static net.spookly.moanimals.Moanimals.LOGGER;
import static net.spookly.moanimals.registry.MoAnimalsRegistries.ITEMS;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.Block;

import dev.architectury.injectables.targets.ArchitecturyTarget;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.ArchitecturyConstants;
import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.mixin.ComposterBlockMixin;

public class MoAnimalItems {

    public static final RegistrySupplier<Item> BREADCRUMBS = registerItem("breadcrumbs", () -> new Item(new Item.Properties().arch$tab(MoAnimalsItemGroups.MOD_TAB)));

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
