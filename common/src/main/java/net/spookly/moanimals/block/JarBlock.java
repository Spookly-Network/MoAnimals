package net.spookly.moanimals.block;


import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class JarBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<JarBlock> CODEC = simpleCodec(JarBlock::new);
    private static final VoxelShape BOUNDING_BOX = Shapes.or(
        Block.box(4.0, 0.0, 4.0, 12.0, 8, 12.0),
        Block.box(6.0, 8.0, 6.0, 10.0, 9.0, 10.0),
        Block.box(5.0, 9.0, 5.0, 11.0, 11.0, 11.0)
    );
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public JarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    protected BlockState updateShape(
        BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2
    ) {
        if ((Boolean)blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return this.defaultBlockState()
            .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.getBlockEntity(blockPos) instanceof JarBlockEntity jarBlockEntity) {
            if (level.isClientSide) {
                return ItemInteractionResult.CONSUME;
            } else {
                ItemStack itemStack2 = jarBlockEntity.getTheItem();
                if (!itemStack.isEmpty()
                    && (itemStack2.isEmpty() || ItemStack.isSameItemSameComponents(itemStack2, itemStack) && itemStack2.getCount() < itemStack2.getMaxStackSize())) {
                    player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                    ItemStack itemStack3 = itemStack.consumeAndReturn(1, player);
                    float f;
                    if (jarBlockEntity.isEmpty()) {
                        jarBlockEntity.setTheItem(itemStack3);
                        f = (float)itemStack3.getCount() / itemStack3.getMaxStackSize();
                    } else {
                        itemStack2.grow(1);
                        f = (float)itemStack2.getCount() / itemStack2.getMaxStackSize();
                    }

                    level.playSound(null, blockPos, SoundEvents.DECORATED_POT_INSERT, SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * f);
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.DUST_PLUME, blockPos.getX() + 0.5, blockPos.getY() + 1.2, blockPos.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
                    }

                    jarBlockEntity.setChanged();
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                    return ItemInteractionResult.SUCCESS;
                } else {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
            }
        } else {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level.getBlockEntity(blockPos) instanceof JarBlockEntity decoratedPotBlockEntity) {
            //TODO: Get item from jar when right clicked
            level.playSound(null, blockPos, SoundEvents.DECORATED_POT_INSERT_FAIL, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return BOUNDING_BOX;
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new JarBlockEntity(blockPos, blockState);
    }

    @Override
    protected void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        Containers.dropContentsOnDestroy(blockState, blockState2, level, blockPos);
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    protected FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    protected SoundType getSoundType(BlockState blockState) {
        return SoundType.GLASS;
    }
}
