package net.spookly.moanimals.registry;

import java.util.function.BiConsumer;

import com.mojang.serialization.Codec;

import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.entity.*;
import net.spookly.moanimals.entity.variant.ButterflyVariant;
import net.spookly.moanimals.entity.variant.DuckVariant;
import net.spookly.moanimals.entity.variant.RacoonVariant;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public final class MoAnimalsRegistrations {
    private MoAnimalsRegistrations() {
    }

    public static void registerEntityAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> registerer) {
        registerer.accept(MoAnimalEntityTypes.DUCK.get(), Duck.createAttributes());
        registerer.accept(MoAnimalEntityTypes.CROCODILE.get(), Crocodile.createAttributes());
        registerer.accept(MoAnimalEntityTypes.RACOON.get(), Racoon.createAttributes());
        registerer.accept(MoAnimalEntityTypes.BUTTERFLY.get(), Butterfly.createAttributes());
        registerer.accept(MoAnimalEntityTypes.SNAIL.get(), Snail.createAttributes());
        registerer.accept(MoAnimalEntityTypes.PENGUIN.get(), Penguin.createAttributes());
        registerer.accept(MoAnimalEntityTypes.OSTRICH.get(), Ostrich.createAttributes());
    }

    public static void registerDataPackRegistries(DataPackRegistryRegisterer registerer) {
        Moanimals.LOGGER.atInfo().log("Registering data pack registries");

        registerer.register(MoAnimalsRegistries.RACOON_VARIANT, RacoonVariant.DIRECT_CODEC);
        registerer.register(MoAnimalsRegistries.BUTTERFLY_VARIANT, ButterflyVariant.DIRECT_CODEC);
        registerer.register(MoAnimalsRegistries.DUCK_VARIANT, DuckVariant.DIRECT_CODEC);
    }

    @FunctionalInterface
    public interface DataPackRegistryRegisterer {
        <T> void register(ResourceKey<Registry<T>> key, Codec<T> codec);
    }
}
