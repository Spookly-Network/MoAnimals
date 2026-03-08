package net.spookly.moanimals.entity.animal;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.Optional;

import net.spookly.moanimals.entity.variant.RacoonVariant;
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
import net.minecraft.world.level.biome.Biomes;

public class RacoonVariants {
    public static final ResourceKey<RacoonVariant> RUSTY = createKey("rusty");
    public static final ResourceKey<RacoonVariant> TANUKI = createKey("tanuki");
    public static final ResourceKey<RacoonVariant> GRAY = createKey("gray");
    public static final ResourceKey<RacoonVariant> GOLDEN = createKey("golden");
    public static final ResourceKey<RacoonVariant> DEFAULT = RUSTY;


    private static ResourceKey<RacoonVariant> createKey(String string) {
        return ResourceKey.create(MoAnimalsRegistries.RACOON_VARIANT, ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }

    private static void register(
        BootstrapContext<RacoonVariant> bootstrapContext, ResourceKey<RacoonVariant> resourceKey, String string, ResourceKey<Biome> resourceKey2
    ) {
        register(bootstrapContext, resourceKey, string, highPrioBiome(HolderSet.direct(bootstrapContext.lookup(Registries.BIOME).getOrThrow(resourceKey2))));
    }

    private static void register(BootstrapContext<RacoonVariant> bootstrapContext, ResourceKey<RacoonVariant> resourceKey, String string, TagKey<Biome> tagKey) {
        register(bootstrapContext, resourceKey, string, highPrioBiome(bootstrapContext.lookup(Registries.BIOME).getOrThrow(tagKey)));
    }

    static void register(BootstrapContext<RacoonVariant> bootstrapContext, ResourceKey<RacoonVariant> resourceKey, String string, SpawnPrioritySelectors spawnPrioritySelectors) {
        ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("entity/racoon/" + string);
        ResourceLocation resourceLocation2 = ResourceLocation.withDefaultNamespace("entity/racoon/" + string + "_sleep");
        bootstrapContext.register(
            resourceKey,
            new RacoonVariant(
                new RacoonVariant.AssetInfo(
                    new ClientAsset(resourceLocation),
                    new ClientAsset(resourceLocation2)
                ),
                spawnPrioritySelectors
            ));
    }

    private static SpawnPrioritySelectors highPrioBiome(HolderSet<Biome> holderSet) {
        return SpawnPrioritySelectors.single(new BiomeCheck(holderSet), 1);
    }

    public static Optional<? extends Holder<RacoonVariant>> selectVariantToSpawn(RandomSource randomSource, RegistryAccess registryAccess, SpawnContext spawnContext) {
        return PriorityProvider.pick(registryAccess.lookupOrThrow(MoAnimalsRegistries.RACOON_VARIANT).listElements(), Holder::value, randomSource, spawnContext);
    }

    public static void bootstrap(BootstrapContext<RacoonVariant> bootstrapContext) {
        register(bootstrapContext, RUSTY, "raccoon", Biomes.FOREST);
        register(bootstrapContext, TANUKI, "racoon_tanuki", Biomes.PLAINS);
        register(bootstrapContext, GRAY, "racoon_gray", Biomes.SWAMP);
        register(bootstrapContext, GOLDEN, "racoon_golden", Biomes.JUNGLE);
    }
}
