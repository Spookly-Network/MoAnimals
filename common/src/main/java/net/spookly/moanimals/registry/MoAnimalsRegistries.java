package net.spookly.moanimals.registry;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import dev.architectury.registry.registries.DeferredRegister;

public interface MoAnimalsRegistries<T extends MoAnimalsRegistries<?>> {
    DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITYS = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registries.ENTITY_TYPE);
}