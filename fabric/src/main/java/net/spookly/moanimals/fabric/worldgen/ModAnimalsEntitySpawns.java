package net.spookly.moanimals.fabric.worldgen;

import net.spookly.moanimals.entity.Butterfly;
import net.spookly.moanimals.entity.Duck;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;


public class ModAnimalsEntitySpawns {
    public static void init() {
        registerSpawnPlacements();
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(MoAnimalEntityTypes.DUCK.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Duck::checkDuckSpawnRules);
        SpawnPlacements.register(MoAnimalEntityTypes.RACOON.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(MoAnimalEntityTypes.CROCODILE.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(MoAnimalEntityTypes.BUTTERFLY.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Butterfly::checkSpawnRules);
    }
}
