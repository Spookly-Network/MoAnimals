package net.spookly.moanimals.network.syncher;

import net.spookly.moanimals.core.CommonPlatformHelper;
import net.spookly.moanimals.entity.RacoonVariant;

import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;

public class MoAnimalsEntityDataSerializers {
    public static final EntityDataSerializer<Holder<RacoonVariant>> RACOON_VARIANT = EntityDataSerializer.forValueType(RacoonVariant.STREAM_CODEC);

    public static void init() {
        CommonPlatformHelper.registerEntityDataSerializers("raccoon_variant", () -> RACOON_VARIANT);
    }
}
