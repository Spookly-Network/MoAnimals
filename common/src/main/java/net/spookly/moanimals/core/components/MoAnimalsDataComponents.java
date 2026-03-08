package net.spookly.moanimals.core.components;

import static net.spookly.moanimals.registry.MoAnimalsRegistries.DATA_COMPONENTS;

import dev.architectury.registry.registries.RegistrySupplier;

import net.spookly.moanimals.entity.variant.ButterflyVariant;
import net.spookly.moanimals.entity.variant.RacoonVariant;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;

public class MoAnimalsDataComponents {

    public static final RegistrySupplier<DataComponentType<Holder<ButterflyVariant>>> BUTTERFLY_VARIANT = register(
        "butterfly/variant", DataComponentType.<Holder<ButterflyVariant>>builder()
            .persistent(ButterflyVariant.CODEC)
            .networkSynchronized(ButterflyVariant.STREAM_CODEC)
    );

    public static final RegistrySupplier<DataComponentType<Holder<RacoonVariant>>> RACCOON_VARIANT = register(
        "raccoon/variant", DataComponentType.<Holder<RacoonVariant>>builder()
            .persistent(RacoonVariant.CODEC)
            .networkSynchronized(RacoonVariant.STREAM_CODEC)
    );

    public static void init() {
        DATA_COMPONENTS.register();
    }

    private static <T> RegistrySupplier<DataComponentType<T>> register(String string, DataComponentType.Builder<T> builder) {
        return DATA_COMPONENTS.register(string, builder::build);
    }
}
