package net.spookly.moanimals;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.core.CommonPlatformHelper;
import net.spookly.moanimals.entity.*;
import net.spookly.moanimals.item.MoAnimalItems;
import net.spookly.moanimals.item.MoAnimalsItemGroups;
import net.spookly.moanimals.network.syncher.MoAnimalsEntityDataSerializers;
import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.world.entity.SpawnPlacementTypes;
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
        MoAnimalsTags.init();
    }

    public static void registerSpawnPlacements() {
//        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.DUCK.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Duck::checkDuckSpawnRules);
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.DUCK.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Duck::checkDuckSpawnRules);

//        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.CROCODILE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Crocodile::checkSpawnRules);
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.CROCODILE.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Crocodile::checkSpawnRules);


        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.BUTTERFLY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Butterfly::checkSpawnRules);
        CommonPlatformHelper.registerSpawnPlacement(MoAnimalEntityTypes.RACOON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Racoon::checkSpawnRules);
    }

}
