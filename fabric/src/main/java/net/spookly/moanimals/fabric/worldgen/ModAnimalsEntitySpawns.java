package net.spookly.moanimals.fabric.worldgen;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.spookly.moanimals.entity.Duck;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.util.MoAnimalsTags;


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
    }
}