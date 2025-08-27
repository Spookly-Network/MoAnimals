package net.spookly.moanimals.item;

import static net.spookly.moanimals.Moanimals.LOGGER;
import static net.spookly.moanimals.Moanimals.MOD_ID;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class MoAnimalsItemGroups {

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MOD_TAB = TABS.register(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "items"), // Tab ID
            () -> CreativeTabRegistry.create(
                    Component.translatable("itemGroup.moanimals"), // Tab Name
                    () -> new ItemStack(Blocks.IRON_ORE) // Icon
            )
    );

    public static void init() {
        LOGGER.info("Registering creative tabs");
        TABS.register();
    }

}
