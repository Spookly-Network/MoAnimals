package net.spookly.moanimals.util;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.NotNull;

public class MoAnimalsTags {
    public final class BiomeTags {

        public static TagKey<Biome> DUCK_SPAWNABLE_IN = createTag("duck_spawnable_in");
        public static TagKey<Biome> RACCOON_SPAWNABLE_IN = createTag("raccoon_spawnable_in");
        public static TagKey<Biome> CROCODILE_SPAWNABLE_IN = createTag("crocodile_spawnable_in");

        private static TagKey<Biome> createTag(@NotNull String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }

    public interface BlockTags {
        public static final TagKey<Block> DUCKS_SPAWNABLE_ON = tag("ducks_spawnable_on");


        private static TagKey<Block> tag(@NotNull String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }
}
