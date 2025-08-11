package net.spookly.moanimals.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.spookly.moanimals.Moanimals;
import net.neoforged.fml.common.Mod;
import net.spookly.moanimals.client.model.CrocodileModel;
import net.spookly.moanimals.client.model.DuckModel;
import net.spookly.moanimals.entity.Duck;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import org.jetbrains.annotations.NotNull;

@Mod(Moanimals.MOD_ID) @EventBusSubscriber(modid = Moanimals.MOD_ID)
public final class MoAnimalsNeoForge {
    public MoAnimalsNeoForge() {
        // Run our common setup.
        Moanimals.init();
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.@NotNull RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DuckModel.LAYER_LOCATION, DuckModel::createBodyLayer);
        event.registerLayerDefinition(CrocodileModel.LAYER_LOCATION, CrocodileModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MoAnimalEntityTypes.DUCK.get(), Duck.createAttributes().build());
        event.put(MoAnimalEntityTypes.CROCODILE.get(), Duck.createAttributes().build());
    }
}
