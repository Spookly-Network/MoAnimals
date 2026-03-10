package net.spookly.moanimals.util;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class MoAnimalsTags {
    public interface BiomeTags {

        TagKey<Biome> DUCK_SPAWNABLE_IN = createTag("spawn_duck_in");
        TagKey<Biome> RACCOON_SPAWNABLE_IN = createTag("spawn_raccoon_in");
        TagKey<Biome> CROCODILE_SPAWNABLE_IN = createTag("spawn_crocodile_in");
        TagKey<Biome> BUTTERFLY_SPAWNABLE_IN = createTag("spawn_butterfly_in");
        TagKey<Biome> SNAIL_SPAWNABLE_IN = createTag("spawn_snail_in");
        TagKey<Biome> PENGUIN_SPAWNABLE_IN = createTag("spawn_penguin_in");
        TagKey<Biome> OSTRICH_SPAWNABLE_IN = createTag("spawn_ostrich_in");

        TagKey<Biome> PLACE_DUCKWEED_IN = createTag("place_duckweed_in");

        TagKey<Biome> HAS_BUTTERFLY_VARIANT_WHITE = createTag("has_butterfly_variant/white");
        TagKey<Biome> HAS_BUTTERFLY_VARIANT_ROSE = createTag("has_butterfly_variant/rose");
        TagKey<Biome> HAS_BUTTERFLY_VARIANT_MONARCH = createTag("has_butterfly_variant/monarch");
        TagKey<Biome> HAS_BUTTERFLY_VARIANT_EMPOROR = createTag("has_butterfly_variant/emporor");
        TagKey<Biome> HAS_BUTTERFLY_VARIANT_AGRIAS_CLAUDIA = createTag("has_butterfly_variant/agrias_claudia");
        TagKey<Biome> HAS_BUTTERFLY_VARIANT_AGRIAS = createTag("has_butterfly_variant/agrias");

        TagKey<Biome> HAS_DUCK_VARIANT_MALLARD = createTag("has_duck_variant/mallard");
        TagKey<Biome> HAS_DUCK_VARIANT_CALL = createTag("has_duck_variant/call");
        TagKey<Biome> HAS_DUCK_VARIANT_BLACK_SCOTER = createTag("has_duck_variant/black_scoter");

        private static TagKey<Biome> createTag(@NotNull String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }

    public interface BlockTags {
        TagKey<Block> DUCKS_SPAWNABLE_ON = tag("ducks_spawnable_on");
        TagKey<Block> CROCODILE_SPAWNABLE_ON = tag("crocodile_spawnable_on");
        TagKey<Block> BUTTERFLY_SPAWNABLE_ON = tag("butterfly_spawnable_on");
        TagKey<Block> RACCOON_SPAWNABLE_ON = tag("raccoon_spawnable_on");
        TagKey<Block> SNAIL_SPAWNABLE_ON = tag("snail_spawnable_on");
        TagKey<Block> PENGUIN_SPAWNABLE_ON = tag("penguin_spawnable_on");
        TagKey<Block> OSTRICH_SPAWNABLE_ON = tag("ostrich_spawnable_on");

        TagKey<Block> EGG_LAYABLE_ON = tag("egg_layable_on");

        TagKey<Block> IS_WARM = tag("is_warm");

        private static TagKey<Block> tag(@NotNull String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }

    public interface ItemTags {
        TagKey<Item> RAW_FISHES = createTag("raw_fishes");

        private static TagKey<Item> createTag(@NotNull String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }

    public static void init() {}
}
