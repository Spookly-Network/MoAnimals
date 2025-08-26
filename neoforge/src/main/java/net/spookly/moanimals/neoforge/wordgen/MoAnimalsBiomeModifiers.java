package net.spookly.moanimals.neoforge.wordgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

import com.mojang.serialization.Lifecycle;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.util.MoAnimalsTags;
import net.spookly.moanimals.worldgen.MoAnimalsPlacedFeatures;

import java.util.List;

import static net.spookly.moanimals.Moanimals.MOD_ID;

public class MoAnimalsBiomeModifiers {
    public static final ResourceKey<BiomeModifier> SPAWN_DUCK = registerKey("spawn_duck");
    public static final ResourceKey<BiomeModifier> SPAWN_RACCOON = registerKey("spawn_raccoon");
    public static final ResourceKey<BiomeModifier> SPAWN_CROCODILE = registerKey("spawn_crocodile");

    public static final ResourceKey<BiomeModifier> PLACE_DUCKWEED = registerKey("place_duckweed");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        //i = weight
        //j = min
        //k = max

        context.register(SPAWN_DUCK, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(MoAnimalsTags.BiomeTags.DUCK_SPAWNABLE_IN),
                List.of(new MobSpawnSettings.SpawnerData(MoAnimalEntityTypes.DUCK.get(), 30, 2, 5))));
        context.register(SPAWN_CROCODILE, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(MoAnimalsTags.BiomeTags.CROCODILE_SPAWNABLE_IN),
                List.of(new MobSpawnSettings.SpawnerData(MoAnimalEntityTypes.CROCODILE.get(), 5, 1, 2))));
        context.register(SPAWN_RACCOON, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(MoAnimalsTags.BiomeTags.RACCOON_SPAWNABLE_IN),
                List.of(new MobSpawnSettings.SpawnerData(MoAnimalEntityTypes.RACOON.get(), 7, 1, 3))));

        context.register(PLACE_DUCKWEED, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(MoAnimalsTags.BiomeTags.PLACE_DUCKWEED_IN),
                HolderSet.direct(placedFeatures.getOrThrow(MoAnimalsPlacedFeatures.DUCKWEED_PATCH_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }
}
