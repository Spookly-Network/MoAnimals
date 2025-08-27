package net.spookly.moanimals.entity;

import java.util.Objects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.spookly.moanimals.registry.MoAnimalsRegistries;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class RacoonVariant {
    public static final Codec<RacoonVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("wild_texture").forGetter((arg) -> arg.wildTexture),
            ResourceLocation.CODEC.fieldOf("sleep_texture").forGetter((arg) -> arg.sleepTexture),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(RacoonVariant::biomes)
    ).apply(instance, RacoonVariant::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, RacoonVariant> DIRECT_STREAM_CODEC;
    public static final Codec<Holder<RacoonVariant>> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<RacoonVariant>> STREAM_CODEC;
    private final ResourceLocation wildTexture;
    private final ResourceLocation sleepTexture;
    private final ResourceLocation wildTextureFull;
    private final ResourceLocation sleepTextureFull;
    private final HolderSet<Biome> biomes;

    public RacoonVariant(ResourceLocation wildTexture, ResourceLocation sleepTexture, HolderSet<Biome> biomes) {
        this.wildTexture = wildTexture;
        this.sleepTexture = sleepTexture;
        this.wildTextureFull = fullTextureId(wildTexture);
        this.sleepTextureFull = fullTextureId(sleepTexture);
        this.biomes = biomes;
    }

    private static ResourceLocation fullTextureId(ResourceLocation arg) {
        return arg.withPath((string) -> "textures/" + string + ".png");
    }

    public ResourceLocation wildTexture() {
        return this.wildTextureFull;
    }

    public ResourceLocation sleepTexture() {
        return this.sleepTextureFull;
    }

    public HolderSet<Biome> biomes() {
        return this.biomes;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof RacoonVariant raccoonVariant)) {
            return false;
        } else {
            return Objects.equals(this.wildTexture, raccoonVariant.wildTexture) && Objects.equals(this.sleepTexture, raccoonVariant.sleepTexture) && Objects.equals(this.biomes, raccoonVariant.biomes);
        }
    }

    public int hashCode() {
        int i = 1;
        i = 31 * i + this.wildTexture.hashCode();
        i = 31 * i + this.sleepTexture.hashCode();
        i = 31 * i + this.biomes.hashCode();
        return i;
    }

    static {
        DIRECT_STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC, RacoonVariant::wildTexture,
                ResourceLocation.STREAM_CODEC, RacoonVariant::sleepTexture,
                ByteBufCodecs.holderSet(Registries.BIOME), RacoonVariant::biomes,
                RacoonVariant::new);
        CODEC = RegistryFileCodec.create(MoAnimalsRegistries.RACOON_VARIANT, DIRECT_CODEC);
        STREAM_CODEC = ByteBufCodecs.holder(MoAnimalsRegistries.RACOON_VARIANT, DIRECT_STREAM_CODEC);
    }
}
