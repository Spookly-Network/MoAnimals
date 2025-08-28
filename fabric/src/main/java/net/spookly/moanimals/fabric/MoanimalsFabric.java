package net.spookly.moanimals.fabric;

import static net.spookly.moanimals.registry.MoAnimalsRegistries.RACOON_VARIANT;

import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.entity.*;
import net.spookly.moanimals.fabric.worldgen.MoAnimalsBiomeModifiers;
import net.spookly.moanimals.fabric.worldgen.ModAnimalsEntitySpawns;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public final class MoanimalsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        //Init Common
        Moanimals.init();

        //Init Fabric
        registerEntityAttributes();
        registerSyncedRegistries();
        ModAnimalsEntitySpawns.init();
        MoAnimalsBiomeModifiers.modifyBiomes();
    }

    //Register Entity Attributes
    private void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(MoAnimalEntityTypes.DUCK.get(), Duck.createAttributes());
        FabricDefaultAttributeRegistry.register(MoAnimalEntityTypes.CROCODILE.get(), Crocodile.createAttributes());
        FabricDefaultAttributeRegistry.register(MoAnimalEntityTypes.RACOON.get(), Racoon.createAttributes());
    }

    // Register dynamic datapack variants
    private void registerSyncedRegistries() {
        DynamicRegistries.registerSynced(RACOON_VARIANT, RacoonVariant.DIRECT_CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
    }
}
