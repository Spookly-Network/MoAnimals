package net.spookly.moanimals.entity.variant;


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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;

public class BirdVariant implements BiomeVariant {
    public static final Codec<BirdVariant> DIRECT_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter((arg) -> arg.texture),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(BirdVariant::biomes),
            RegistryCodecs.homogeneousList(Registries.SOUND_EVENT).fieldOf("sounds").forGetter(BirdVariant::sounds)
    ).apply(instance, BirdVariant::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BirdVariant> DIRECT_STREAM_CODEC;
    public static final Codec<Holder<BirdVariant>> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BirdVariant>> STREAM_CODEC;
    private final ResourceLocation texture;
    private final HolderSet<Biome> biomes;
    private final HolderSet<SoundEvent> sounds;

    public BirdVariant(ResourceLocation texture, HolderSet<Biome> biomes, HolderSet<SoundEvent> sounds) {
        this.texture = texture;
        this.biomes = biomes;
        this.sounds = sounds;
    }

    private static ResourceLocation fullTextureId(ResourceLocation textureId) {
        return textureId.withPath((path) -> "textures/" + path + ".png");
    }

    public ResourceLocation texture() {
        return fullTextureId(this.texture);
    }


    public HolderSet<Biome> biomes() {
        return this.biomes;
    }

    public HolderSet<SoundEvent> sounds() {
        return this.sounds;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BirdVariant birdVariant)) {
            return false;
        }

        return Objects.equals(this.texture, birdVariant.texture)
            && Objects.equals(this.biomes, birdVariant.biomes)
            && Objects.equals(this.sounds, birdVariant.sounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.texture, this.biomes, this.sounds);
    }

    static {
        DIRECT_STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            BirdVariant::texture,
            ByteBufCodecs.holderSet(Registries.BIOME),
            BirdVariant::biomes,
            ByteBufCodecs.holderSet(Registries.SOUND_EVENT),
            BirdVariant::sounds,
            BirdVariant::new);

        CODEC = RegistryFileCodec.create(MoAnimalsRegistries.BIRD_VARIANT, DIRECT_CODEC);
        STREAM_CODEC = ByteBufCodecs.holder(MoAnimalsRegistries.BIRD_VARIANT, DIRECT_STREAM_CODEC);
    }
}
