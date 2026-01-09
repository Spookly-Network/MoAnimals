package net.spookly.moanimals.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.entity.Ostrich;
import net.spookly.moanimals.util.MoAnimalsTags;

public class OstrichEggBlock extends Block {
    public static final MapCodec<OstrichEggBlock> CODEC = simpleCodec(OstrichEggBlock::new);
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;

    private static final VoxelShape ONE_EGG_AABB = Block.box(5.0, 0.0, 5.0, 11.0, 8.0, 11.0);

    public OstrichEggBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0));
    }

    @Override
    public MapCodec<OstrichEggBlock> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (this.shouldUpdateHatchLevel(serverLevel) && onWarm(serverLevel, blockPos)) {
            int i = (Integer)blockState.getValue(HATCH);
            if (i < 2) {
                serverLevel.playSound(null, blockPos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + randomSource.nextFloat() * 0.2F);
                serverLevel.setBlock(blockPos, blockState.setValue(HATCH, i + 1), 2);
                serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(blockState));
            } else {
                serverLevel.playSound(null, blockPos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + randomSource.nextFloat() * 0.2F);
                serverLevel.removeBlock(blockPos, false);
                serverLevel.gameEvent(GameEvent.BLOCK_DESTROY, blockPos, GameEvent.Context.of(blockState));
                spawnHatchedEntity(blockState, serverLevel, blockPos);
            }
        }
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return ONE_EGG_AABB;
    }

    private void spawnHatchedEntity(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos) {
        serverLevel.levelEvent(2001, blockPos, Block.getId(blockState));
        Ostrich ostrich = MoAnimalEntityTypes.OSTRICH.get().create(serverLevel);
        if (ostrich != null) {
            ostrich.setAge(-24000);
            ostrich.setHomePos(blockPos);
            ostrich.moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 0.3, 0.0F, 0.0F);
            serverLevel.addFreshEntity(ostrich);
        }
    }

    public static boolean onWarm(BlockGetter blockGetter, BlockPos blockPos) {
        return isWarm(blockGetter, blockPos.below());
    }

    public static boolean isWarm(BlockGetter blockGetter, BlockPos blockPos) {
        return blockGetter.getBlockState(blockPos).is(MoAnimalsTags.BlockTags.IS_WARM);
    }

    @Override
    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (onWarm(level, blockPos) && !level.isClientSide) {
            level.levelEvent(2012, blockPos, 15);
        }
    }

    private boolean shouldUpdateHatchLevel(Level level) {
        float f = level.getTimeOfDay(1.0F);
        return f < 0.69 && f > 0.65 ? true : level.random.nextInt(500) == 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

}
