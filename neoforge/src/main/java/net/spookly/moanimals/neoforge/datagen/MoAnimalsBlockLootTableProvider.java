package net.spookly.moanimals.neoforge.datagen;

import java.util.Set;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.item.MoAnimalItems;

public class MoAnimalsBlockLootTableProvider extends BlockLootSubProvider {

    protected MoAnimalsBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(MoAnimalBlocks.DUCKWEED.get(), createShearsOnlyDrop(MoAnimalItems.DUCKWEED.get()));
    }
}
