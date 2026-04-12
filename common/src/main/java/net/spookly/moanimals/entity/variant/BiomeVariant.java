package net.spookly.moanimals.entity.variant;

import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;

public interface BiomeVariant {
    HolderSet<Biome> biomes();
}
