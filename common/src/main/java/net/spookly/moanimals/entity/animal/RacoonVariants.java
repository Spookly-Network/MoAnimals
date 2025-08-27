package net.spookly.moanimals.entity.animal;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.Objects;
import java.util.Optional;

import net.spookly.moanimals.entity.RacoonVariant;
import net.spookly.moanimals.registry.MoAnimalsRegistries;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class RacoonVariants {
    public static final ResourceKey<RacoonVariant> RUSTY = createKey("rusty");
    public static final ResourceKey<RacoonVariant> TANUKI = createKey("tanuki");
    public static final ResourceKey<RacoonVariant> GRAY = createKey("gray");
    public static final ResourceKey<RacoonVariant> GOLDEN = createKey("golden");
    public static final ResourceKey<RacoonVariant> DEFAULT;


    private static ResourceKey<RacoonVariant> createKey(String string) {
        return ResourceKey.create(MoAnimalsRegistries.RACOON_VARIANT, ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }

    static void register(BootstrapContext<RacoonVariant> bootstrapContext, ResourceKey<RacoonVariant> resourceKey, String string, ResourceKey<Biome> resourceKey2) {
        register(bootstrapContext, resourceKey, string, HolderSet.direct(bootstrapContext.lookup(Registries.BIOME).getOrThrow(resourceKey2)));
    }

    static void register(BootstrapContext<RacoonVariant> bootstrapContext, ResourceKey<RacoonVariant> resourceKey, String string, TagKey<Biome> tagKey) {
        register(bootstrapContext, resourceKey, string, bootstrapContext.lookup(Registries.BIOME).getOrThrow(tagKey));
    }

    static void register(BootstrapContext<RacoonVariant> bootstrapContext, ResourceKey<RacoonVariant> resourceKey, String string, HolderSet<Biome> holderSet) {
        ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("entity/racoon/" + string);
        ResourceLocation resourceLocation2 = ResourceLocation.withDefaultNamespace("entity/racoon/" + string + "_sleep");
        bootstrapContext.register(resourceKey, new RacoonVariant(resourceLocation, resourceLocation2, holderSet));
    }

    public static Holder<RacoonVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> holder) {
        Registry<RacoonVariant> registry = registryAccess.registryOrThrow(MoAnimalsRegistries.RACOON_VARIANT);
        Optional var10000 = registry.holders().filter((reference) -> ((RacoonVariant)reference.value()).biomes().contains(holder)).findFirst().or(() -> registry.getHolder(DEFAULT));
        Objects.requireNonNull(registry);
        return (Holder)var10000.or(registry::getAny).orElseThrow();
    }

    public static void bootstrap(BootstrapContext<RacoonVariant> bootstrapContext) {
        register(bootstrapContext, RUSTY, "raccoon", Biomes.FOREST);
        register(bootstrapContext, TANUKI, "racoon_tanuki", Biomes.PLAINS);
        register(bootstrapContext, GRAY, "racoon_gray", Biomes.SWAMP);
        register(bootstrapContext, GOLDEN, "racoon_golden", Biomes.JUNGLE);
    }

    static {
        DEFAULT = RUSTY;
    }
}
