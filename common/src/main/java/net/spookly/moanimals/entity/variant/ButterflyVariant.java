package net.spookly.moanimals.entity.variant;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.registry.MoAnimalsRegistries;

import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public record ButterflyVariant(ClientAsset assetInfo,
                               SpawnPrioritySelectors spawnConditions) implements PriorityProvider<SpawnContext, SpawnCondition> {

    public static final Codec<ButterflyVariant> DIRECT_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
                ClientAsset.DEFAULT_FIELD_CODEC.forGetter(ButterflyVariant::assetInfo),
                SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(ButterflyVariant::spawnConditions)
            )
            .apply(instance, ButterflyVariant::new)
    );

    public static final Codec<ButterflyVariant> NETWORK_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(ClientAsset.DEFAULT_FIELD_CODEC.forGetter(ButterflyVariant::assetInfo)).apply(instance, ButterflyVariant::new)
    );

    public static final Codec<Holder<ButterflyVariant>> CODEC = RegistryFixedCodec.create(MoAnimalsRegistries.BUTTERFLY_VARIANT);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ButterflyVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(MoAnimalsRegistries.BUTTERFLY_VARIANT);

    private ButterflyVariant(ClientAsset assetInfo) {
        this(assetInfo, SpawnPrioritySelectors.EMPTY);
    }

    @Override
    public @NotNull List<Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }

    private static ResourceLocation fullTextureId(ResourceLocation arg) {
        return arg.withPath((string) -> "textures/" + string + ".png");
    }
}
