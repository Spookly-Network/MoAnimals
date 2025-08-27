package net.spookly.moanimals.block;

import static net.spookly.moanimals.Moanimals.LOGGER;
import static net.spookly.moanimals.Moanimals.MOD_ID;
import static net.spookly.moanimals.registry.MoAnimalsRegistries.BLOCKS;

import java.util.function.Supplier;

import dev.architectury.registry.registries.RegistrySupplier;

import net.spookly.moanimals.item.MoAnimalItems;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MoAnimalBlocks {

    public static final RegistrySupplier<Block> DUCKWEED = registerBlock("duckweed", () -> new WaterlilyBlock(copyPropertiesOf(Blocks.LILY_PAD).noCollission()));

    public static void init() {
        LOGGER.info("Registering blocks");
        BLOCKS.register();

        BLOCKS.forEach(blockRegistrySupplier -> {
            if (blockRegistrySupplier.getId() == DUCKWEED.getId()) {
                return;
            }

            Block block = blockRegistrySupplier.get();
            MoAnimalItems.registerBlockItem(blockRegistrySupplier.getRegisteredName().split(":")[1], block);
        });
    }

    public static RegistrySupplier<Block> registerBlock(String path, Supplier<Block> block) {
        RegistrySupplier<Block> b = BLOCKS.register(ResourceLocation.fromNamespaceAndPath(MOD_ID, path), block);
        return b;
    }

    public static BlockBehaviour.Properties copyPropertiesOf(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }
}
