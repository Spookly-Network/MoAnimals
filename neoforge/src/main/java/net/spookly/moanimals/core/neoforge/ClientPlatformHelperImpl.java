package net.spookly.moanimals.core.neoforge;

import java.util.function.Supplier;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.NotNull;

public class ClientPlatformHelperImpl {
    public static void setRenderLayer(Supplier<Block> block, @NotNull RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), type);
    }

    public static <T extends Entity> void registerEntityRenderers(@NotNull Supplier<EntityType<T>> type, @NotNull EntityRendererProvider<T> renderProvider) {
        EntityRenderers.register(type.get(), renderProvider);
    }
}
