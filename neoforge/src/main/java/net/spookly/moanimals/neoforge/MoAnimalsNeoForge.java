package net.spookly.moanimals.neoforge;

import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.client.model.CrocodileModel;
import net.spookly.moanimals.client.model.DuckModel;
import net.spookly.moanimals.client.model.RacoonModel;
import net.spookly.moanimals.core.neoforge.CommonPlatformHelperImpl;
import net.spookly.moanimals.entity.*;
import net.spookly.moanimals.neoforge.wordgen.MoAnimalsEntitySpawns;
import net.spookly.moanimals.registry.MoAnimalsRegistries;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@Mod(Moanimals.MOD_ID) @EventBusSubscriber(modid = Moanimals.MOD_ID)
public final class MoAnimalsNeoForge {
    public MoAnimalsNeoForge(IEventBus modBus) {
        // Run our common setup.
        Moanimals.init();

        CommonPlatformHelperImpl.BLOCKS.register(modBus);
        CommonPlatformHelperImpl.BLOCK_ENTITY_TYPES.register(modBus);
        CommonPlatformHelperImpl.ITEMS.register(modBus);
        CommonPlatformHelperImpl.ENTITY_TYPES.register(modBus);
        CommonPlatformHelperImpl.ENTITY_DATA_SERIALIZER.register(modBus);
        CommonPlatformHelperImpl.SOUND_EVENTS.register(modBus);
        CommonPlatformHelperImpl.POTIONS.register(modBus);
        CommonPlatformHelperImpl.MENU_TYPES.register(modBus);

        CommonPlatformHelperImpl.RECIPE_TYPES.register(modBus);
        CommonPlatformHelperImpl.RECIPE_SERIALIZERS.register(modBus);
    }

    @SubscribeEvent
    public static void onNewDataPackRegistry(final DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                MoAnimalsRegistries.RACOON_VARIANT,
                RacoonVariant.DIRECT_CODEC,
                RacoonVariant.DIRECT_CODEC
        );
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.@NotNull RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DuckModel.LAYER_LOCATION, DuckModel::createBodyLayer);
        event.registerLayerDefinition(CrocodileModel.LAYER_LOCATION, CrocodileModel::createBodyLayer);
        event.registerLayerDefinition(RacoonModel.LAYER_LOCATION, RacoonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MoAnimalEntityTypes.DUCK.get(), Duck.createAttributes().build());
        event.put(MoAnimalEntityTypes.CROCODILE.get(), Crocodile.createAttributes().build());
        event.put(MoAnimalEntityTypes.RACOON.get(), Racoon.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        MoAnimalsEntitySpawns.registerSpawnPlacements(event);
    }
}
