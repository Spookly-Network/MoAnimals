package net.spookly.moanimals.neoforge.wordgen;

import net.spookly.moanimals.entity.Duck;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public class MoAnimalsEntitySpawns {

    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(MoAnimalEntityTypes.DUCK.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.WORLD_SURFACE,
                Duck::checkDuckSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(MoAnimalEntityTypes.CROCODILE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(MoAnimalEntityTypes.RACOON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

}
