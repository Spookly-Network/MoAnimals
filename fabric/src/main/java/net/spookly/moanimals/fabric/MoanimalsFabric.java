package net.spookly.moanimals.fabric;

import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.fabric.worldgen.MoAnimalsBiomeModifiers;
import net.spookly.moanimals.registry.MoAnimalsRegistrations;

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
        Moanimals.registerSpawnPlacements();

        //Init Fabric
        registerEntityAttributes();
        registerSyncedRegistries();
        MoAnimalsBiomeModifiers.modifyBiomes();
    }

    //Register Entity Attributes
    private void registerEntityAttributes() {
        MoAnimalsRegistrations.registerEntityAttributes(FabricDefaultAttributeRegistry::register);
    }

    // Register dynamic datapack variants
    private void registerSyncedRegistries() {
        MoAnimalsRegistrations.registerDataPackRegistries(new MoAnimalsRegistrations.DataPackRegistryRegisterer() {
            @Override
            public <T> void register(net.minecraft.resources.ResourceKey<net.minecraft.core.Registry<T>> key, com.mojang.serialization.Codec<T> codec) {
                DynamicRegistries.registerSynced(key, codec, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
            }
        });
    }
}
