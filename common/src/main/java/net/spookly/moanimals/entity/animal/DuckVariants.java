package net.spookly.moanimals.entity.animal;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.spookly.moanimals.entity.variant.DuckVariant;
import net.spookly.moanimals.registry.MoAnimalsRegistries;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class DuckVariants {

    public static final ResourceKey<DuckVariant> MALLARD = createKey("mallard");
    public static final ResourceKey<DuckVariant> DEFAULT = MALLARD;

    private static ResourceKey<DuckVariant> createKey(String string) {
        return ResourceKey.create(MoAnimalsRegistries.DUCK_VARIANT, ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }

    static void register(BootstrapContext<DuckVariant> bootstrapContext, ResourceKey<DuckVariant> resourceKey, String string, ResourceKey<Biome> resourceKey2) {
        register(bootstrapContext, resourceKey, string, HolderSet.direct(bootstrapContext.lookup(Registries.BIOME).getOrThrow(resourceKey2)));
    }

    static void register(BootstrapContext<DuckVariant> bootstrapContext, ResourceKey<DuckVariant> resourceKey, String string, HolderSet<Biome> holderSet) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, "entity/duck/" + string);
        bootstrapContext.register(resourceKey, new DuckVariant(resourceLocation, holderSet));
    }
}
