package net.spookly.moanimals.util;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.NotNull;

public class MoAnimalsTags {
    public interface BiomeTags {

        TagKey<Biome> DUCK_SPAWNABLE_IN = createTag("spawn_duck_in");
        TagKey<Biome> RACCOON_SPAWNABLE_IN = createTag("spawn_raccoon_in");
        TagKey<Biome> CROCODILE_SPAWNABLE_IN = createTag("spawn_crocodile_in");
        TagKey<Biome> BUTTERFLY_SPAWNABLE_IN = createTag("spawn_butterfly_in");

        TagKey<Biome> PLACE_DUCKWEED_IN = createTag("place_duckweed_in");

        private static TagKey<Biome> createTag(@NotNull String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }

    public interface BlockTags {
        TagKey<Block> DUCKS_SPAWNABLE_ON = tag("ducks_spawnable_on");
        TagKey<Block> CROCODILE_SPAWNABLE_ON = tag("crocodile_spawnable_on");
        TagKey<Block> BUTTERFLY_SPAWNABLE_ON = tag("butterfly_spawnable_on");
        TagKey<Block> RACCOON_SPAWNABLE_ON = tag("raccoon_spawnable_on");

        private static TagKey<Block> tag(@NotNull String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }

    public static void init() {}
}
