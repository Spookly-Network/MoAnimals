package net.spookly.moanimals.neoforge.datagen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import dev.architectury.registry.registries.RegistrySupplier;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.item.MoAnimalItems;
import net.spookly.moanimals.registry.MoAnimalsRegistries;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public class MoAnimalsBlockLootTableProvider extends BlockLootSubProvider {

    protected MoAnimalsBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(MoAnimalBlocks.DUCKWEED.get(), createShearsOnlyDrop(MoAnimalItems.DUCKWEED.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return () -> {
            Iterator<RegistrySupplier<Block>> iterator = MoAnimalsRegistries.BLOCKS.iterator();
            List<Block> blocks = new ArrayList<>();

            while (iterator.hasNext()) {
                blocks.add(iterator.next().get());
            }

            return blocks.iterator();
        };

    }
}
