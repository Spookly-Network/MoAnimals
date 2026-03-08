package net.spookly.moanimals.entity;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.item.MoAnimalItems;
import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

public class Snail extends PathfinderMob implements Bucketable {

    private int slimeTime;
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.BOOLEAN);

    protected Snail(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.slimeTime = this.pickNextSlimeDropTime();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5d)
                .add(Attributes.MOVEMENT_SPEED, 0.05)
                .add(Attributes.FOLLOW_RANGE, 24d);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        super.registerGoals();
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FROM_BUCKET, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("slime_time", this.slimeTime);
        compoundTag.putBoolean("FromBucket", this.fromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        var slimeTime = compoundTag.getInt("slime_time");
        var fromBucket = compoundTag.getBoolean("FromBucket");

        slimeTime.ifPresent(integer -> this.slimeTime = integer);
        fromBucket.ifPresent(this::setFromBucket);
    }

    @Override
    public void customServerAiStep(ServerLevel serverLevel) {
        if (this.isAlive() && --this.slimeTime <= 0) {
            this.playSound(SoundEvents.SLIME_SQUISH_SMALL, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(serverLevel, Items.SLIME_BALL);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.slimeTime = this.pickNextSlimeDropTime();
        }

        super.customServerAiStep(serverLevel);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        return (InteractionResult) bucketMobPickup(player, interactionHand, this).orElse(super.mobInteract(player, interactionHand));
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return false;
    }

    //Bucktable
    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean bl) {
        this.entityData.set(FROM_BUCKET, bl);
    }

    @Override
    public void saveToBucketTag(ItemStack itemStack) {
        Bucketable.saveDefaultDataToBucketTag(this, itemStack);
    }

    @Override
    public void loadFromBucketTag(CompoundTag compoundTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(MoAnimalItems.SNAIL_BUCKET.get());
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL;
    }

    static <T extends LivingEntity & Bucketable> Optional<InteractionResult> bucketMobPickup(Player player, InteractionHand interactionHand, T livingEntity) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.getItem() == Items.BUCKET && livingEntity.isAlive()) {
            livingEntity.playSound(livingEntity.getPickupSound(), 1.0F, 1.0F);
            ItemStack itemStack2 = livingEntity.getBucketItemStack();
            livingEntity.saveToBucketTag(itemStack2);
            ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, player, itemStack2, false);
            player.setItemInHand(interactionHand, itemStack3);
            Level level = livingEntity.level();
            if (!level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemStack2);
            }

            livingEntity.discard();
            return Optional.of(InteractionResult.SUCCESS);
        } else {
            return Optional.empty();
        }
    }

    private int pickNextSlimeDropTime() {
        return this.random.nextInt(20 * TimeUtil.SECONDS_PER_MINUTE * 5) + 20 * TimeUtil.SECONDS_PER_MINUTE * 5;
    }

    public static boolean checkSpawnRules(EntityType<? extends Snail> pType, @NotNull ServerLevelAccessor pLevel, EntitySpawnReason pReason, BlockPos pPos, RandomSource pRandom) {
        var biomes = MoAnimalsTags.BlockTags.SNAIL_SPAWNABLE_ON;
        var blockBelow = pLevel.getBlockState(pPos.below());
        return blockBelow.is(biomes);
    }
}
