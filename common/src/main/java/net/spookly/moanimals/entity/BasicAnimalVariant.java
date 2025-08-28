package net.spookly.moanimals.entity;

import java.util.Objects;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.spookly.moanimals.registry.MoAnimalsRegistries;

abstract public class BasicAnimalVariant {
    public static final Codec<BasicAnimalVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter((arg) -> arg.texture),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(BasicAnimalVariant::biomes)
    ).apply(instance, BasicAnimalVariant::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BasicAnimalVariant> DIRECT_STREAM_CODEC;
    public static final Codec<Holder<BasicAnimalVariant>> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BasicAnimalVariant>> STREAM_CODEC;
    private final ResourceLocation texture;
    private final HolderSet<Biome> biomes;

    public BasicAnimalVariant(ResourceLocation texture, HolderSet<Biome> biomes) {
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
        } else if (!(object instanceof BasicAnimalVariant basicAnimalVariant)) {
            return false;
        } else {
            return Objects.equals(this.texture, basicAnimalVariant.texture) && Objects.equals(this.biomes, basicAnimalVariant.biomes);
        }
    }

    public int hashCode() {
        int i = 1;
        i = 31 * i + this.texture.hashCode();
        i = 31 * i + this.biomes.hashCode();
        return i;
    }

    abstract ResourceKey<Registry<BasicAnimalVariant>> getRegistry() {

    }

    static {
        DIRECT_STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC, BasicAnimalVariant::texture,
                ByteBufCodecs.holderSet(Registries.BIOME), BasicAnimalVariant::biomes,
                BasicAnimalVariant::new);
        CODEC = RegistryFileCodec.create(MoAnimalsRegistries.BASIC_ANIMAL_VARIANT, DIRECT_CODEC);
        STREAM_CODEC = ByteBufCodecs.holder(MoAnimalsRegistries.BASIC_ANIMAL_VARIANT, DIRECT_STREAM_CODEC);
    }
}
