package net.spookly.moanimals;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.entity.Duck;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.item.MoAnimalItems;
import net.spookly.moanimals.item.MoAnimalsItemGroups;
import net.spookly.moanimals.mixin.SpawnPlacementsInvoker;
import net.spookly.moanimals.network.syncher.MoAnimalsEntityDataSerializers;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;

public final class Moanimals {
    public static final String MOD_ID = "moanimals";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        // Write common init code here.
        MoAnimalEntityTypes.init();
        MoAnimalBlocks.init();
        MoAnimalsItemGroups.init();
        MoAnimalItems.init();
        MoAnimalsEntityDataSerializers.init();
    }

    public static void registerSpawnPlacements() {
        SpawnPlacementsInvoker.invokeRegister(MoAnimalEntityTypes.DUCK.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Duck::checkDuckSpawnRules);
        SpawnPlacementsInvoker.invokeRegister(MoAnimalEntityTypes.RACOON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
    }

}
