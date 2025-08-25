package net.spookly.moanimals.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.spookly.moanimals.neoforge.wordgen.MoAnimalsBiomeModifiers;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static net.spookly.moanimals.Moanimals.MOD_ID;

class MoAnimalsDatapackProvider  extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
//            .add(Registries.TRIM_MATERIAL, ModTrimMaterials::bootstrap)
//            .add(Registries.TRIM_PATTERN, ModTrimPatterns::bootstrap)
//            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)
//
//            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
//            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MoAnimalsBiomeModifiers::bootstrap);

    public MoAnimalsDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MOD_ID));
    }
}