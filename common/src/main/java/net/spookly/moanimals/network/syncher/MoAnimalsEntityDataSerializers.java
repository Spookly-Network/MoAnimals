package net.spookly.moanimals.network.syncher;

import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

import net.spookly.moanimals.entity.RacoonVariant;

public class MoAnimalsEntityDataSerializers {
    public static final EntityDataSerializer<Holder<RacoonVariant>> RACOON_VARIANT = EntityDataSerializer.forValueType(RacoonVariant.STREAM_CODEC);

    static {
        EntityDataSerializers.registerSerializer(RACOON_VARIANT);
    }
}
