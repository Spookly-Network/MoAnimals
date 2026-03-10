package net.spookly.moanimals.network.syncher;

import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.core.CommonPlatformHelper;
import net.spookly.moanimals.entity.variant.ButterflyVariant;
import net.spookly.moanimals.entity.variant.DuckVariant;
import net.spookly.moanimals.entity.variant.RacoonVariant;

import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;

public class MoAnimalsEntityDataSerializers {
    public static final EntityDataSerializer<Holder<RacoonVariant>> RACOON_VARIANT = EntityDataSerializer.forValueType(RacoonVariant.STREAM_CODEC);
    public static final EntityDataSerializer<Holder<ButterflyVariant>> BUTTERFLY_VARIANT = EntityDataSerializer.forValueType(ButterflyVariant.STREAM_CODEC);
    public static final EntityDataSerializer<Holder<DuckVariant>> DUCK_VARIANT = EntityDataSerializer.forValueType(DuckVariant.STREAM_CODEC);

    public static void init() {
        Moanimals.LOGGER.atInfo().log("Registering entity data serializers");
        CommonPlatformHelper.registerEntityDataSerializers("raccoon_variant", () -> RACOON_VARIANT);
        CommonPlatformHelper.registerEntityDataSerializers("butterfly_variant", () -> BUTTERFLY_VARIANT);
        CommonPlatformHelper.registerEntityDataSerializers("duck_variant", () -> DUCK_VARIANT);
    }
}
