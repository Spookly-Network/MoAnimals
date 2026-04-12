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
import net.minecraft.world.level.biome.Biome;

//todo: add size modifier
public class ButterflyVariant {
    public static final Codec<ButterflyVariant> DIRECT_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter((arg) -> arg.texture),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(ButterflyVariant::biomes)
    ).apply(instance, ButterflyVariant::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ButterflyVariant> DIRECT_STREAM_CODEC;
    public static final Codec<Holder<ButterflyVariant>> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ButterflyVariant>> STREAM_CODEC;
    private final ResourceLocation texture;
    private final HolderSet<Biome> biomes;

    public ButterflyVariant(ResourceLocation texture, HolderSet<Biome> biomes) {
        this.texture = texture;
        this.biomes = biomes;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ButterflyVariant butterflyVariant)) {
            return false;
        }

        return Objects.equals(this.texture, butterflyVariant.texture)
            && Objects.equals(this.biomes, butterflyVariant.biomes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.texture, this.biomes);
    }

    static {
        DIRECT_STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            ButterflyVariant::texture,
            ByteBufCodecs.holderSet(Registries.BIOME),
            ButterflyVariant::biomes,
            ButterflyVariant::new);

        CODEC = RegistryFileCodec.create(MoAnimalsRegistries.BUTTERFLY_VARIANT, DIRECT_CODEC);
        STREAM_CODEC = ByteBufCodecs.holder(MoAnimalsRegistries.BUTTERFLY_VARIANT, DIRECT_STREAM_CODEC);
    }
}
