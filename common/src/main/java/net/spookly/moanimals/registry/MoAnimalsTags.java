package net.spookly.moanimals.registry;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MoAnimalsTags {
    public interface BlockTags {
        TagKey<Block> DUCKS_SPAWNABLE_ON = tag("ducks_spawnable_on");


        private static TagKey<Block> tag(@NotNull String name) {
            return TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }
}
