package net.spookly.moanimals.sounds;

import static net.spookly.moanimals.Moanimals.LOGGER;
import static net.spookly.moanimals.Moanimals.MOD_ID;
import static net.spookly.moanimals.registry.MoAnimalsRegistries.SOUND_EVENTS;

import dev.architectury.registry.registries.RegistrySupplier;
import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class MoAnimalsSoundEvents {

    public static final RegistrySupplier<SoundEvent> DUCK_QUACK = register("entity.duck.quack");

    public static void init() {
        LOGGER.info("Registering Sound Events");
        SOUND_EVENTS.register();
        LOGGER.info("✓ Registering Sound Events");
    }

    private static RegistrySupplier<SoundEvent> register(@NotNull String string) {
        return register(ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }

    private static RegistrySupplier<SoundEvent> register(@NotNull ResourceLocation resourceLocation) {
        return SOUND_EVENTS.register(resourceLocation, () -> SoundEvent.createVariableRangeEvent(resourceLocation));
    }
}
