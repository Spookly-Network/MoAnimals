package net.spookly.moanimals.entity.variants;

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
    public static final Codec<ButterflyVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter((arg) -> arg.texture),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(ButterflyVariant::biomes)
    ).apply(instance, ButterflyVariant::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ButterflyVariant> DIRECT_STREAM_CODEC;
    public static final Codec<Holder<ButterflyVariant>> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ButterflyVariant>> STREAM_CODEC;
    private final ResourceLocation texture;
    private final HolderSet<Biome> biomes;

    public ButterflyVariant(ResourceLocation texture, HolderSet<Biome> biomes) {
        this.texture = fullTextureId(texture);
        this.biomes = biomes;
    }

    private static ResourceLocation fullTextureId(ResourceLocation arg) {
        return arg.withPath((string) -> "textures/" + string + ".png");
    }

    public ResourceLocation texture() {
        return this.texture;
    }


    public HolderSet<Biome> biomes() {
        return this.biomes;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof ButterflyVariant ButterflyVariant)) {
            return false;
        } else {
            return Objects.equals(this.texture, ButterflyVariant.texture) && Objects.equals(this.biomes, ButterflyVariant.biomes);
        }
    }

    public int hashCode() {
        int i = 1;
        i = 31 * i + this.texture.hashCode();
        i = 31 * i + this.biomes.hashCode();
        return i;
    }

    static {
        DIRECT_STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC, ButterflyVariant::texture,
                ByteBufCodecs.holderSet(Registries.BIOME), ButterflyVariant::biomes,
                ButterflyVariant::new);
        CODEC = RegistryFileCodec.create(MoAnimalsRegistries.BUTTERFLY_VARIANT, DIRECT_CODEC);
        STREAM_CODEC = ByteBufCodecs.holder(MoAnimalsRegistries.BUTTERFLY_VARIANT, DIRECT_STREAM_CODEC);
    }
}
