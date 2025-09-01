package net.spookly.moanimals.fabric.worldgen;

import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.util.MoAnimalsTags;
import net.spookly.moanimals.worldgen.MoAnimalsPlacedFeatures;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

public class MoAnimalsBiomeModifiers {
    public static void modifyBiomes() {
        modifyFeatures();
        modifySpawns();
    }

    private static void modifyFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.RIVER, Biomes.PLAINS, Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.SWAMP, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                MoAnimalsPlacedFeatures.DUCKWEED_PATCH_PLACED_KEY);
    }

    private static void modifySpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.PLAINS, Biomes.RIVER),
                MobCategory.CREATURE, MoAnimalEntityTypes.DUCK.get(), 30, 3, 5);
        BiomeModifications.addSpawn(BiomeSelectors.tag(MoAnimalsTags.BiomeTags.RACCOON_SPAWNABLE_IN),
                MobCategory.CREATURE, MoAnimalEntityTypes.RACOON.get(), 5, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(MoAnimalsTags.BiomeTags.CROCODILE_SPAWNABLE_IN),
                MobCategory.CREATURE, MoAnimalEntityTypes.CROCODILE.get(), 2, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(MoAnimalsTags.BiomeTags.BUTTERFLY_SPAWNABLE_IN),
                MobCategory.AMBIENT, MoAnimalEntityTypes.BUTTERFLY.get(), 13, 2, 4);
    }
}
