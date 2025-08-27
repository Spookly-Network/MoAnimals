package net.spookly.moanimals.registry;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import dev.architectury.registry.registries.DeferredRegister;

import net.spookly.moanimals.entity.RacoonVariant;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MoAnimalsRegistries {

    public static ResourceKey<Registry<RacoonVariant>> RACOON_VARIANT = createRegistryKey("racoon_variant");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String string) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }

    public final static DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public final static DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public final static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITYS = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public final static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registries.ENTITY_TYPE);
    public static DeferredRegister<RacoonVariant> RACOON_VARIANTS = DeferredRegister.create(MOD_ID, RACOON_VARIANT);
}
