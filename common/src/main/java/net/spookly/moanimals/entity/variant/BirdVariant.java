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

    private ResourceLocation fullTextureId(ResourceLocation arg) {
        return arg.withPath((string) -> "textures/" + string + ".png");
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

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof BirdVariant DuckVariant)) {
            return false;
        } else {
            return Objects.equals(this.texture, DuckVariant.texture) && Objects.equals(this.biomes, DuckVariant.biomes) && Objects.equals(this.sounds, DuckVariant.sounds);
        }
    }

    public int hashCode() {
        int i = 1;
        i = 31 * i + this.texture.hashCode();
        i = 31 * i + this.biomes.hashCode();
        i = 31 * i + this.sounds.hashCode();
        return i;
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
