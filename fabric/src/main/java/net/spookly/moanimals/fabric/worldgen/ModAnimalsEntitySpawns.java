package net.spookly.moanimals.fabric.worldgen;

import net.spookly.moanimals.entity.*;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;


public class ModAnimalsEntitySpawns {
    public static void init() {
        registerSpawnPlacements();
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(MoAnimalEntityTypes.DUCK.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Duck::checkDuckSpawnRules);
        SpawnPlacements.register(MoAnimalEntityTypes.RACOON.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Racoon::checkSpawnRules);
        SpawnPlacements.register(MoAnimalEntityTypes.CROCODILE.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Crocodile::checkSpawnRules);
        SpawnPlacements.register(MoAnimalEntityTypes.BUTTERFLY.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Butterfly::checkSpawnRules);
    }
}
