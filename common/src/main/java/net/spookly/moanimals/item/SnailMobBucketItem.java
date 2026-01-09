package net.spookly.moanimals.item;

import org.jetbrains.annotations.NotNull;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class SnailMobBucketItem extends MobBucketItem {

    public SnailMobBucketItem(EntityType<?> entityType, Fluid fluid, SoundEvent soundEvent, Properties properties) {
        super(entityType, fluid, soundEvent, properties);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return super.use(level, player, interactionHand);
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return super.use(level, player, interactionHand);
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos blockPos2 = blockPos.relative(direction);

            if (!level.mayInteract(player, blockPos) || !player.mayUseItemAt(blockPos2, direction, itemStack)) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockState = level.getBlockState(blockPos);
                this.checkExtraContent(player, level, itemStack, blockPos2);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockPos2, itemStack);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
                ItemStack itemStack2 = ItemUtils.createFilledResult(itemStack, player, getEmptySuccessItem(itemStack, player));
                return InteractionResult.SUCCESS.heldItemTransformedTo(itemStack2);
            }
        }
    }
}
