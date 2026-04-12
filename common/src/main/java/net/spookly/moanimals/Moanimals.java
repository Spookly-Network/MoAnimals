package net.spookly.moanimals;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.block.MoAnimalsBlockEntityTypes;
import net.spookly.moanimals.core.CommonPlatformHelper;
import net.spookly.moanimals.entity.*;
import net.spookly.moanimals.item.MoAnimalItems;
import net.spookly.moanimals.item.MoAnimalsItemGroups;
import net.spookly.moanimals.network.syncher.MoAnimalsEntityDataSerializers;
import net.spookly.moanimals.sounds.MoAnimalsSoundEvents;
import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;


public final class Moanimals {
    public static final String MOD_ID = "moanimals";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        // Write common init code here.
        MoAnimalEntityTypes.init();
        MoAnimalsBlockEntityTypes.init();
        MoAnimalBlocks.init();

        //Needs to be behind Blocks, because it will generate itemmodels
        MoAnimalItems.init();
        MoAnimalsItemGroups.init();
        MoAnimalsEntityDataSerializers.init();
        MoAnimalsTags.init();
        MoAnimalsSoundEvents.init();
    }

    public static void registerSpawnPlacements() {
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.DUCK.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Duck::checkDuckSpawnRules);
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.CROCODILE.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Crocodile::checkSpawnRules);
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.SNAIL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Snail::checkSpawnRules);
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.BUTTERFLY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Butterfly::checkSpawnRules);
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.RACOON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Racoon::checkSpawnRules);
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.PENGUIN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Penguin::checkSpawnRules);
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.OSTRICH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ostrich::checkSpawnRules);
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.BIRD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Bird::checkSpawnRules);
    }

}
