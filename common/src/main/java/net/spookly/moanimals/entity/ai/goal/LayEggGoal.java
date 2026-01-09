package net.spookly.moanimals.entity.ai.goal;

import net.spookly.moanimals.entity.struc.AbstractEggLayingAnimal;
import net.spookly.moanimals.entity.struc.EggLayingAnimal;
import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class LayEggGoal extends MoveToBlockGoal {
    private final EggLayingAnimal<? extends Animal> owner;

    public LayEggGoal(EggLayingAnimal<? extends Animal> owner, int searchDistance) {
        super(owner.getOwner(), 1.0, searchDistance);
        this.owner = owner;
    }

    @Override
    public boolean canUse() {
        return this.owner.hasEgg() && this.owner.getHomePos().closerToCenterThan(this.owner.getOwner().position(), 9.0) && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.owner.hasEgg() && this.owner.getHomePos().closerToCenterThan(this.owner.getOwner().position(), 9.0);
    }

    @Override
    public void tick() {
        super.tick();
        Animal entity = this.owner.getOwner();
        BlockPos blockPos = entity.blockPosition();

        if (!entity.isInWater() && this.isReachedTarget()) {
            if (!this.owner.isLayingEgg()) {
                this.owner.setLayingEgg(true);
            } else if (entity instanceof AbstractEggLayingAnimal eggAnimal && eggAnimal.layEggCounter > this.adjustedTickDelay(200)) {
                Level level = entity.level();
                level.playSound(null, blockPos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);

                BlockPos eggPos = this.blockPos.above();
                Block eggBlock = this.owner.getEggBlock();
                BlockState blockState = eggBlock.defaultBlockState();

                // Handle blocks with egg count (like turtle eggs)
                if (blockState.hasProperty(TurtleEggBlock.EGGS)) {
                    blockState = blockState.setValue(TurtleEggBlock.EGGS, entity.getRandom().nextInt(4) + 1);
                }

                level.setBlock(eggPos, blockState, 3);
                level.gameEvent(GameEvent.BLOCK_PLACE, eggPos, GameEvent.Context.of(entity, blockState));

                this.owner.setHasEgg(false);
                this.owner.setLayingEgg(false);
                entity.setInLoveTime(600);
            }

            if (this.owner.isLayingEgg() && entity instanceof AbstractEggLayingAnimal eggAnimal) {
                eggAnimal.layEggCounter++;
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        return !levelReader.isEmptyBlock(blockPos.above()) ? false : levelReader.getBlockState(blockPos).is(MoAnimalsTags.BlockTags.EGG_LAYABLE_ON);
    }
}
