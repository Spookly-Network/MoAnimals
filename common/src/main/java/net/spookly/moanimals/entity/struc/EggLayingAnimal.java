package net.spookly.moanimals.entity.struc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.block.Block;

public interface EggLayingAnimal<T extends PathfinderMob> {
    BlockPos getHomePos();
    void setHomePos(BlockPos blockPos);
    boolean hasEgg();
    void setHasEgg(boolean hasEgg);
    boolean isLayingEgg();
    void setLayingEgg(boolean layingEgg);
    T getOwner();
    Block getEggBlock();
}
