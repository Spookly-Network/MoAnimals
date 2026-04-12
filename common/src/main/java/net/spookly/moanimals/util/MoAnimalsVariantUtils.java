package net.spookly.moanimals.util;

import java.util.concurrent.ThreadLocalRandom;

import net.spookly.moanimals.entity.variant.BiomeVariant;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class MoAnimalsVariantUtils {

    /**
     * Gets a random spawn variant for a biome.
     * @param registry The registry to search for variants
     * @param holder The biome to search for variants
     * @param defaultVariant The default variant to return if no variants are found
     * @return A random variant for the biome
     * @param <T> The type of variant
     */
    public static <T extends BiomeVariant> Holder<T> getSpawnVariantForBiome(Registry<T> registry, Holder<Biome> holder, ResourceKey<T> defaultVariant) {
        var matches = new java.util.ArrayList<Holder<T>>();
        registry.holders().forEach(ref -> {
            if (ref.value().biomes().contains(holder)) {
                matches.add(ref);
            }
        });

        if (matches.isEmpty()) {
            return registry.getHolder(defaultVariant).or(registry::getAny).orElseThrow();
        }
        if (matches.size() == 1) {
            return matches.getFirst();
        }

        int i = ThreadLocalRandom.current().nextInt(matches.size());
        return matches.get(i);

    }
}
