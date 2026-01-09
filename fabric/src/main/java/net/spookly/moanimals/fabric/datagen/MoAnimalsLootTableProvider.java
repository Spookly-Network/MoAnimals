package net.spookly.moanimals.fabric.datagen;

import java.util.concurrent.CompletableFuture;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.item.MoAnimalItems;

import net.minecraft.core.HolderLookup;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class MoAnimalsLootTableProvider extends FabricBlockLootTableProvider {
    protected MoAnimalsLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        add(MoAnimalBlocks.DUCKWEED.get(), createShearsOnlyDrop(MoAnimalItems.DUCKWEED.get()));
        add(MoAnimalBlocks.OSTRICH_EGG.get(), createSilkTouchOnlyTable(MoAnimalItems.OSTRICH_EGG.get()));

    }
}
