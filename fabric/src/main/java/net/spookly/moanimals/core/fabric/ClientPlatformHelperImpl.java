package net.spookly.moanimals.core.fabric;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ClientPlatformHelperImpl {
    public static void setRenderLayer(@NotNull Supplier<Block> block, RenderType type) {
        BlockRenderLayerMap.INSTANCE.putBlock(block.get(), type);
//        BlockRenderLayerMap.INSTANCE.putBlock(NaturalistRegistry.CATTAIL.get(), RenderType.cutout());
    }

    public static <T extends Entity> void registerEntityRenderers(@NotNull Supplier<EntityType<T>> type, EntityRendererProvider<T> renderProvider) {
        EntityRendererRegistry.register(type.get(), renderProvider);
    }
}
