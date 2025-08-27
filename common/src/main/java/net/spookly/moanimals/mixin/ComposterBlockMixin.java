package net.spookly.moanimals.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

@Mixin(ComposterBlock.class)
public interface ComposterBlockMixin {
    @Invoker("add")
    static void invokeAdd(float f, ItemLike itemLike) {
        throw new AssertionError();
    }
}
