package net.spookly.moanimals.entity.animal;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.spookly.moanimals.entity.variant.ButterflyVariant;
import net.spookly.moanimals.registry.MoAnimalsRegistries;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ButterflyVariants {

    public static final ResourceKey<ButterflyVariant> MONARCH = createKey("monarch");
    public static final ResourceKey<ButterflyVariant> EMPEROR = createKey("emperor");
    public static final ResourceKey<ButterflyVariant> DEFAULT;

    private static ResourceKey<ButterflyVariant> createKey(String string) {
        return ResourceKey.create(MoAnimalsRegistries.BUTTERFLY_VARIANT, ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }

    static void register(BootstrapContext<ButterflyVariant> bootstrapContext, ResourceKey<ButterflyVariant> resourceKey, String string, ResourceKey<Biome> resourceKey2) {
        register(bootstrapContext, resourceKey, string, HolderSet.direct(bootstrapContext.lookup(Registries.BIOME).getOrThrow(resourceKey2)));
    }

    static void register(BootstrapContext<ButterflyVariant> bootstrapContext, ResourceKey<ButterflyVariant> resourceKey, String string, HolderSet<Biome> holderSet) {
        ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("entity/butterfly/" + string);
        bootstrapContext.register(resourceKey, new ButterflyVariant(resourceLocation, holderSet));
    }

    /**
     * FIXME: Biome tags and objects not working
     * @param registryAccess RegistryAccess to get biome registry.
     * @param holder Biome to check.
     * @return ButterflyVariant, which is valid for biome.
     */
    public static Holder<ButterflyVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> holder) {
        Registry<ButterflyVariant> registry = registryAccess.lookupOrThrow(MoAnimalsRegistries.BUTTERFLY_VARIANT);
        return (Holder<ButterflyVariant>)registry.listElements()
                .filter(reference -> ((ButterflyVariant)reference.value()).biomes().contains(holder))
                .findFirst()
                .or(() -> registry.get(DEFAULT))
                .or(registry::getAny)
                .orElseThrow();
    }

    static {
        DEFAULT = MONARCH;
    }
}
