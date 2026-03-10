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

public class DuckVariant {
    public static final Codec<DuckVariant> DIRECT_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter((arg) -> arg.texture),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(DuckVariant::biomes)
        ).apply(instance, DuckVariant::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DuckVariant> DIRECT_STREAM_CODEC;
    public static final Codec<Holder<DuckVariant>> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DuckVariant>> STREAM_CODEC;
    private final ResourceLocation texture;
    private final HolderSet<Biome> biomes;

    public DuckVariant(ResourceLocation texture, HolderSet<Biome> biomes) {
        this.texture = texture;
        this.biomes = biomes;
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

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof DuckVariant DuckVariant)) {
            return false;
        } else {
            return Objects.equals(this.texture, DuckVariant.texture) && Objects.equals(this.biomes, DuckVariant.biomes);
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
            ResourceLocation.STREAM_CODEC,
            DuckVariant::texture,
            ByteBufCodecs.holderSet(Registries.BIOME),
            DuckVariant::biomes,
            DuckVariant::new);

        CODEC = RegistryFileCodec.create(MoAnimalsRegistries.DUCK_VARIANT, DIRECT_CODEC);
        STREAM_CODEC = ByteBufCodecs.holder(MoAnimalsRegistries.DUCK_VARIANT, DIRECT_STREAM_CODEC);
    }
}
