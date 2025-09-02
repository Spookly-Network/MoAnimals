package net.spookly.moanimals.entity;

import java.util.EnumSet;
import java.util.Optional;

import net.spookly.moanimals.entity.variant.RacoonVariant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.spookly.moanimals.entity.animal.RacoonVariants;
import net.spookly.moanimals.network.syncher.MoAnimalsEntityDataSerializers;
import net.spookly.moanimals.registry.MoAnimalsRegistries;
import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class Racoon extends Animal implements VariantHolder<Holder<RacoonVariant>> {

    private static final EntityDataAccessor<Holder<RacoonVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Racoon.class, MoAnimalsEntityDataSerializers.RACOON_VARIANT);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID;

    private static final int FLAG_SITTING = 1;
    private static final int FLAG_SLEEPING = 32;

    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState breathAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    protected Racoon(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        RegistryAccess registryAccess = this.registryAccess();
        Registry<RacoonVariant> registry = registryAccess.registryOrThrow(MoAnimalsRegistries.RACOON_VARIANT);
        builder.define(DATA_VARIANT_ID, (Holder<RacoonVariant>) registry.getHolder(RacoonVariants.DEFAULT).or(registry::getAny).orElseThrow());
        builder.define(DATA_FLAGS_ID, (byte) 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Wolf.class, 8.0F, 1.3D, 1.2D, livingEntity -> !((Wolf) livingEntity).isTame()));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Fox.class, 8.0F, 1.3D, 1.2D));

        this.goalSelector.addGoal(3, new BreedGoal(this, 1));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(Items.SWEET_BERRIES), true));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));

        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.25F, false));

        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new SleepGoal());
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(
                this, AbstractFish.class, 20, false, false, livingEntity -> livingEntity instanceof AbstractSchoolingFish
        ));
        super.registerGoals();
    }

    public ResourceLocation getTexture() {
        RacoonVariant raccoonVariant = (RacoonVariant) this.getVariant().value();
        if (this.isSleeping()) {
            return raccoonVariant.sleepTexture();
        } else {
            return raccoonVariant.wildTexture();
        }
    }

    @Override
    public void setVariant(Holder<RacoonVariant> holder) {
        this.entityData.set(DATA_VARIANT_ID, holder);
    }

    @Override
    public Holder<RacoonVariant> getVariant() {
        return (Holder) this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        if (randomSource.nextFloat() < 0.2F) {
            float f = randomSource.nextFloat();
            ItemStack itemStack;
            if (f < 0.05F) {
                itemStack = new ItemStack(Items.APPLE);
            } else if (f < 0.2F) {
                itemStack = new ItemStack(Items.MELON_SLICE);
            } else if (f < 0.4F) {
                itemStack = randomSource.nextBoolean() ? new ItemStack(Items.COD) : new ItemStack(Items.SALMON);
            } else if (f < 0.6F) {
                itemStack = new ItemStack(Items.CARROT);
            } else if (f < 0.8F) {
                itemStack = new ItemStack(Items.POTATO);
            } else {
                itemStack = new ItemStack(Items.SWEET_BERRIES);
            }

            this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
        }
    }


    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.getVariant().unwrapKey().ifPresent((resourceKey) -> compoundTag.putString("variant", resourceKey.location().toString()));
        compoundTag.putBoolean("Sleeping", this.isSleeping());
        compoundTag.putBoolean("Sitting", this.isSitting());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        Optional.ofNullable(ResourceLocation.tryParse(compoundTag.getString("variant"))).map((resourceLocation) -> ResourceKey.create(MoAnimalsRegistries.RACOON_VARIANT, resourceLocation)).flatMap((resourceKey) -> this.registryAccess().registryOrThrow(MoAnimalsRegistries.RACOON_VARIANT).getHolder(resourceKey)).ifPresent(this::setVariant);
    }

    @Nullable public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        Holder<Biome> holder = serverLevelAccessor.getBiome(this.blockPosition());
        Holder<RacoonVariant> holder2;

        holder2 = RacoonVariants.getSpawnVariant(this.registryAccess(), holder);
        spawnGroupData = new RaccoonGroupData(holder2);

        this.setVariant(holder2);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 24d)
                .add(Attributes.ATTACK_DAMAGE, 1.5);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.SWEET_BERRIES);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return MoAnimalEntityTypes.RACOON.get().create(serverLevel);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80; //Animation leanght
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isSleeping()) {
            if (this.sleepAnimationState.isStarted()) return;
            this.sleepAnimationState.start(this.tickCount);
            this.breathAnimationState.start(this.tickCount);

        } else {
            this.sleepAnimationState.stop();
            this.breathAnimationState.stop();
        }
    }

    public static boolean checkSpawnRules(EntityType<? extends Racoon> pType, @NotNull ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBlockState(pPos.below()).is(MoAnimalsTags.BlockTags.RACCOON_SPAWNABLE_ON) || pLevel.getBlockState(pPos.below()).getFluidState().is(FluidTags.WATER);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setFlag(int i, boolean bl) {
        if (bl) {
            this.entityData.set(DATA_FLAGS_ID, (byte) ((Byte) this.entityData.get(DATA_FLAGS_ID) | i));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte) ((Byte) this.entityData.get(DATA_FLAGS_ID) & ~i));
        }

    }

    private boolean getFlag(int i) {
        return ((Byte) this.entityData.get(DATA_FLAGS_ID) & i) != 0;
    }

    public boolean isSitting() {
        return this.getFlag(FLAG_SITTING);
    }

    void setSleeping(boolean bl) {
        this.setFlag(FLAG_SLEEPING, bl);
    }

    @Override
    public boolean isSleeping() {
        return this.getFlag(32);
    }

    public void setSitting(boolean bl) {
        this.setFlag(1, bl);
    }

    void clearStates() {
        this.setSitting(false);
        this.setSleeping(false);
    }

    static {
        DATA_FLAGS_ID = SynchedEntityData.defineId(Racoon.class, EntityDataSerializers.BYTE);
    }

    public static class RaccoonGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<RacoonVariant> type;

        public RaccoonGroupData(Holder<RacoonVariant> holder) {
            super(false);
            this.type = holder;
        }
    }

    class SleepGoal extends RaccoonBehaviorGoal {
        private static final int WAIT_TIME_BEFORE_SLEEP = reducedTickDelay(140);
        private int countdown;

        public SleepGoal() {
            this.countdown = Racoon.this.random.nextInt(WAIT_TIME_BEFORE_SLEEP);
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        }

        public boolean canUse() {
            if (Racoon.this.xxa == 0.0F && Racoon.this.yya == 0.0F && Racoon.this.zza == 0.0F) {
                return this.canSleep() || Racoon.this.isSleeping();
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return this.canSleep();
        }

        private boolean canSleep() {
            if (this.countdown > 0) {
                --this.countdown;
                return false;
            } else {
                return Racoon.this.level().isDay() && this.hasShelter() && !Racoon.this.isInPowderSnow;
            }
        }

        public void stop() {
            this.countdown = Racoon.this.random.nextInt(WAIT_TIME_BEFORE_SLEEP);
            Racoon.this.clearStates();
        }

        public void start() {
            Racoon.this.setSitting(false);
            Racoon.this.setJumping(false);
            Racoon.this.setSleeping(true);
            Racoon.this.getNavigation().stop();
            Racoon.this.getMoveControl().setWantedPosition(Racoon.this.getX(), Racoon.this.getY(), Racoon.this.getZ(), (double) 0.0F);
        }
    }

    abstract class RaccoonBehaviorGoal extends Goal {
//        private final TargetingConditions alertableTargeting = TargetingConditions.forCombat().range((double)12.0F).ignoreLineOfSight().selector(Racoon.this.new FoxAlertableEntitiesSelector());

        protected boolean hasShelter() {
            BlockPos blockPos = BlockPos.containing(Racoon.this.getX(), Racoon.this.getBoundingBox().maxY, Racoon.this.getZ());
            return !Racoon.this.level().canSeeSky(blockPos) && Racoon.this.getWalkTargetValue(blockPos) >= 0.0F;
        }

//        protected boolean alertable() {
//            return !Racoon.this.level().getNearbyEntities(LivingEntity.class, this.alertableTargeting, Fox.this, Fox.this.getBoundingBox().inflate((double)12.0F, (double)6.0F, (double)12.0F)).isEmpty();
//        }
    }
}
