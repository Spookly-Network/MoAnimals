package net.spookly.moanimals.neoforge.datagen;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.List;
import java.util.Set;

import net.spookly.moanimals.neoforge.wordgen.MoAnimalsBiomeModifiers;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.loot.LootTableProvider;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@EventBusSubscriber(modid = MOD_ID)
public class DatapackGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createDatapackRegistryObjects(
            new RegistrySetBuilder()
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MoAnimalsBiomeModifiers::bootstrap)
        );

        event.createProvider(MoAnimalsModelProvider::new);
        event.createProvider((output, lookupProvider) -> new LootTableProvider(
            output,
            Set.of(),
            List.of(),
            lookupProvider
        ));
    }
}
