package net.spookly.moanimals.entity.animal;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.Objects;
import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import net.spookly.moanimals.entity.BasicAnimalVariant;
import net.spookly.moanimals.registry.MoAnimalsRegistries;

public class ButterflyVariants {

    public static final ResourceKey<BasicAnimalVariant> MONARCH = createKey("monarch");
    public static final ResourceKey<BasicAnimalVariant> EMPEROR = createKey("emperor");
    public static final ResourceKey<BasicAnimalVariant> DEFAULT;

    private static ResourceKey<BasicAnimalVariant> createKey(String string) {
        return ResourceKey.create(MoAnimalsRegistries.BASIC_ANIMAL_VARIANT, ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }

    static void register(BootstrapContext<BasicAnimalVariant> bootstrapContext, ResourceKey<BasicAnimalVariant> resourceKey, String string, ResourceKey<Biome> resourceKey2) {
        register(bootstrapContext, resourceKey, string, HolderSet.direct(bootstrapContext.lookup(Registries.BIOME).getOrThrow(resourceKey2)));
    }

    static void register(BootstrapContext<BasicAnimalVariant> bootstrapContext, ResourceKey<BasicAnimalVariant> resourceKey, String string, HolderSet<Biome> holderSet) {
        ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("entity/butterfly/" + string);
        bootstrapContext.register(resourceKey, new BasicAnimalVariant(resourceLocation, holderSet));
    }

    public static Holder<BasicAnimalVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> holder) {
        Registry<BasicAnimalVariant> registry = registryAccess.registryOrThrow(MoAnimalsRegistries.BASIC_ANIMAL_VARIANT);
        Optional var10000 = registry.holders().filter((reference) -> ((BasicAnimalVariant) reference.value()).biomes().contains(holder)).findFirst().or(() -> registry.getHolder(DEFAULT));
        Objects.requireNonNull(registry);
        return (Holder) var10000.or(registry::getAny).orElseThrow();
    }

    static {
        DEFAULT = MONARCH;
    }
}
