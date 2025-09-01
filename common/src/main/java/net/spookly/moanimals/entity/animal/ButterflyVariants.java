package net.spookly.moanimals.entity.animal;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import net.spookly.moanimals.entity.variants.ButterflyVariant;
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

import java.util.concurrent.ThreadLocalRandom;

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
        Registry<ButterflyVariant> registry = registryAccess.registryOrThrow(MoAnimalsRegistries.BUTTERFLY_VARIANT);

        var matches = new java.util.ArrayList<Holder<ButterflyVariant>>();
        registry.holders().forEach(ref -> {
            if (ref.value().biomes().contains(holder)) {
                matches.add(ref);
            }
        });

        if (matches.isEmpty()) {
            return registry.getHolder(DEFAULT).or(registry::getAny).orElseThrow();
        }
        if (matches.size() == 1) {
            return matches.getFirst();
        }

        int i = ThreadLocalRandom.current().nextInt(matches.size());
        return matches.get(i);
    }

    static {
        DEFAULT = MONARCH;
    }
}
