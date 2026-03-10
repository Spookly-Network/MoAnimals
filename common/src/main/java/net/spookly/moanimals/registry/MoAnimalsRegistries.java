package net.spookly.moanimals.registry;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import dev.architectury.registry.registries.DeferredRegister;

import net.spookly.moanimals.Moanimals;
import net.spookly.moanimals.entity.variant.ButterflyVariant;
import net.spookly.moanimals.entity.variant.DuckVariant;
import net.spookly.moanimals.entity.variant.RacoonVariant;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MoAnimalsRegistries {

    public static ResourceKey<Registry<RacoonVariant>> RACOON_VARIANT = createRegistryKey("racoon_variant");
    public static ResourceKey<Registry<ButterflyVariant>> BUTTERFLY_VARIANT = createRegistryKey("butterfly_variant");
    public static ResourceKey<Registry<DuckVariant>> DUCK_VARIANT = createRegistryKey("duck_variant");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String string) {
        Moanimals.LOGGER.atInfo().log("Creating registry key for: " + string);
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }

    public final static DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public final static DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public final static DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(MOD_ID, Registries.SOUND_EVENT);
    public final static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITYS = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public final static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registries.ENTITY_TYPE);
    public static DeferredRegister<RacoonVariant> RACOON_VARIANTS = DeferredRegister.create(MOD_ID, RACOON_VARIANT);
    public static DeferredRegister<ButterflyVariant> BUTTERFLY_VARIANTS = DeferredRegister.create(MOD_ID, BUTTERFLY_VARIANT);
}
