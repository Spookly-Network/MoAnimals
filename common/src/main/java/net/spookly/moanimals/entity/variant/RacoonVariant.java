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
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public record RacoonVariant(AssetInfo assetInfo, SpawnPrioritySelectors spawnConditions) implements PriorityProvider<SpawnContext, SpawnCondition> {

    public static final Codec<RacoonVariant> DIRECT_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
                RacoonVariant.AssetInfo.CODEC.fieldOf("assets").forGetter(RacoonVariant::assetInfo),
                SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(RacoonVariant::spawnConditions)
            )
            .apply(instance, RacoonVariant::new)
    );

    public static final Codec<RacoonVariant> NETWORK_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(RacoonVariant.AssetInfo.CODEC.fieldOf("assets").forGetter(RacoonVariant::assetInfo)).apply(instance, RacoonVariant::new)
    );
    public static final Codec<Holder<RacoonVariant>> CODEC = RegistryFixedCodec.create(MoAnimalsRegistries.RACOON_VARIANT);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<RacoonVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(MoAnimalsRegistries.RACOON_VARIANT);

    private RacoonVariant(RacoonVariant.AssetInfo assetInfo) {
        this(assetInfo, SpawnPrioritySelectors.EMPTY);
    }

    @Override
    public @NotNull List<Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }

    public record AssetInfo(ClientAsset wild, ClientAsset sleep) {
        public static final Codec<RacoonVariant.AssetInfo> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ClientAsset.CODEC.fieldOf("wild").forGetter(RacoonVariant.AssetInfo::wild),
                    ClientAsset.CODEC.fieldOf("sleep").forGetter(RacoonVariant.AssetInfo::sleep)
                    )
                .apply(instance, RacoonVariant.AssetInfo::new)
        );
    }
}
