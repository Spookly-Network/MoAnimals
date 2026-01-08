
package net.spookly.moanimals.entity.struc;

import net.spookly.moanimals.entity.MoAnimal;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * Abstract base class for animals that lay eggs.
 * This eliminates boilerplate by handling entity data and persistence automatically.
 */
public abstract class AbstractEggLayingAnimal extends MoAnimal implements EggLayingAnimal<AbstractEggLayingAnimal> {
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(AbstractEggLayingAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LAYING_EGG = SynchedEntityData.defineId(AbstractEggLayingAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Long> HOME_POS_PACKED = SynchedEntityData.defineId(AbstractEggLayingAnimal.class, EntityDataSerializers.LONG);

    public int layEggCounter;

    protected AbstractEggLayingAnimal(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HAS_EGG, false);
        builder.define(LAYING_EGG, false);
        builder.define(HOME_POS_PACKED, BlockPos.ZERO.asLong());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("HasEgg", this.hasEgg());
        compoundTag.putLong("HomePos", this.getHomePos().asLong());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setHasEgg(compoundTag.getBoolean("HasEgg"));
        if (compoundTag.contains("HomePos")) {
            this.setHomePos(BlockPos.of(compoundTag.getLong("HomePos")));
        }
    }

    @Override
    public boolean hasEgg() {
        return this.entityData.get(HAS_EGG);
    }

    @Override
    public void setHasEgg(boolean hasEgg) {
        this.entityData.set(HAS_EGG, hasEgg);
    }

    @Override
    public boolean isLayingEgg() {
        return this.entityData.get(LAYING_EGG);
    }

    @Override
    public void setLayingEgg(boolean layingEgg) {
        this.layEggCounter = layingEgg ? 1 : 0;
        this.entityData.set(LAYING_EGG, layingEgg);
    }

    @Override
    public BlockPos getHomePos() {
        return BlockPos.of(this.entityData.get(HOME_POS_PACKED));
    }

    @Override
    public void setHomePos(BlockPos blockPos) {
        this.entityData.set(HOME_POS_PACKED, blockPos.asLong());
    }

    @Override
    public AbstractEggLayingAnimal getOwner() {
        return this;
    }

    /**
     * Override this method to use a different egg block.
     * Default returns turtle eggs.
     */
    @Override
    public Block getEggBlock() {
        return Blocks.TURTLE_EGG;
    }
}
