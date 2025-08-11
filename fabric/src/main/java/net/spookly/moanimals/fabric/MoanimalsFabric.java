package net.spookly.moanimals.fabric;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.spookly.moanimals.Moanimals;
import net.fabricmc.api.ModInitializer;
import net.spookly.moanimals.entity.Crocodile;
import net.spookly.moanimals.entity.Duck;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;

public final class MoanimalsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Moanimals.init();
        FabricDefaultAttributeRegistry.register(MoAnimalEntityTypes.DUCK.get(), Duck.createAttributes());
        FabricDefaultAttributeRegistry.register(MoAnimalEntityTypes.CROCODILE.get(), Crocodile.createAttributes());
    }
}
