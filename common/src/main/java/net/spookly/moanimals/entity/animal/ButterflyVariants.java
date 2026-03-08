package net.spookly.moanimals.entity.animal;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.Optional;

import net.spookly.moanimals.entity.variant.ButterflyVariant;
import net.spookly.moanimals.registry.MoAnimalsRegistries;

import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;

public class ButterflyVariants {

    public static final ResourceKey<ButterflyVariant> MONARCH = createKey("monarch");
    public static final ResourceKey<ButterflyVariant> EMPEROR = createKey("emperor");
    public static final ResourceKey<ButterflyVariant> DEFAULT = MONARCH;

    private static ResourceKey<ButterflyVariant> createKey(String string) {
        return ResourceKey.create(MoAnimalsRegistries.BUTTERFLY_VARIANT, ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }

    private static void register(
        BootstrapContext<ButterflyVariant> bootstrapContext, ResourceKey<ButterflyVariant> resourceKey, String string, ResourceKey<Biome> resourceKey2) {
        register(bootstrapContext, resourceKey, string, highPrioBiome(HolderSet.direct(bootstrapContext.lookup(Registries.BIOME).getOrThrow(resourceKey2))));
    }

    private static void register(BootstrapContext<ButterflyVariant> bootstrapContext, ResourceKey<ButterflyVariant> resourceKey, String string, TagKey<Biome> tagKey) {
        register(bootstrapContext, resourceKey, string, highPrioBiome(bootstrapContext.lookup(Registries.BIOME).getOrThrow(tagKey)));
    }

    private static SpawnPrioritySelectors highPrioBiome(HolderSet<Biome> holderSet) {
        return SpawnPrioritySelectors.single(new BiomeCheck(holderSet), 1);
    }


    private static void register(
        BootstrapContext<ButterflyVariant> bootstrapContext, ResourceKey<ButterflyVariant> resourceKey, String string, SpawnPrioritySelectors spawnPrioritySelectors
    ) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, "entity/butterfly/" + string);

        bootstrapContext.register(
            resourceKey,
            new ButterflyVariant(
                new ClientAsset(resourceLocation),
                spawnPrioritySelectors
            )
        );
    }

    public static Optional<? extends Holder<ButterflyVariant>> selectVariantToSpawn(RandomSource randomSource, RegistryAccess registryAccess, SpawnContext spawnContext) {
        return PriorityProvider.pick(registryAccess.lookupOrThrow(MoAnimalsRegistries.BUTTERFLY_VARIANT).listElements(), Holder::value, randomSource, spawnContext);
    }
}
