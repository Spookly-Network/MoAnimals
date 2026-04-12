package net.spookly.moanimals.entity.animal;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.spookly.moanimals.entity.variant.BirdVariant;
import net.spookly.moanimals.registry.MoAnimalsRegistries;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class BirdVariants {

    public static final ResourceKey<BirdVariant> ROBIN = createKey("robin");
    public static final ResourceKey<BirdVariant> DEFAULT = ROBIN;

    private static ResourceKey<BirdVariant> createKey(String string) {
        return ResourceKey.create(MoAnimalsRegistries.BIRD_VARIANT, ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }
}
