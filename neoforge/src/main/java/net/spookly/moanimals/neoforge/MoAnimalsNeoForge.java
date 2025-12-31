package net.spookly.moanimals.neoforge;

import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.core.neoforge.CommonPlatformHelperImpl;
import net.spookly.moanimals.registry.MoAnimalsRegistrations;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@Mod(Moanimals.MOD_ID)
@EventBusSubscriber(modid = Moanimals.MOD_ID)
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
        MoAnimalsRegistrations.registerDataPackRegistries(new MoAnimalsRegistrations.DataPackRegistryRegisterer() {
            @Override
            public <T> void register(net.minecraft.resources.ResourceKey<net.minecraft.core.Registry<T>> key, com.mojang.serialization.Codec<T> codec) {
                event.dataPackRegistry(key, codec, codec);
            }
        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        MoAnimalsRegistrations.registerEntityAttributes((type, builder) -> event.put(type, builder.build()));
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        Moanimals.registerSpawnPlacements();
    }
}
