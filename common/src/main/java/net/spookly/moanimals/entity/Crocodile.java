package net.spookly.moanimals.entity;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;

public class Crocodile extends WaterAnimal implements NeutralMob {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimation = new AnimationState();
    public final AnimationState swimAnimation = new AnimationState();
    public final AnimationState walkAnimation = new AnimationState();


    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME;
    private static final UniformInt PERSISTENT_ANGER_TIME;
    @Nullable private UUID persistentAngerTarget;

    public Crocodile(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 10, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.setAggressive(true);
//        Dolphin
    }

    @Override
    protected void registerGoals() {
//        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2));
//        this.goalSelector.addGoal(2, new BreedGoal(this, 1));
//        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(Items.CHICKEN), true));

//        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(5, new RestrictSunGoal(this));

        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0, 1));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
        super.registerGoals();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 20d)
                .add(Attributes.MOVEMENT_SPEED, 1)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 2)
                .add(Attributes.FOLLOW_RANGE, 24d)
                .add(Attributes.ATTACK_DAMAGE, 5);

    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
    }

//    @Override
//    public boolean isFood(ItemStack itemStack) {
//        return itemStack.is(Items.CHICKEN);
//    }

    //TODO: make
//    @Override
//    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
//        return null;
//    }

    private void setupAnimationStates() {
        this.idleAnimationState.startIfStopped(this.tickCount);

        this.swimAnimation.animateWhen(this.isInWater(), this.tickCount);
//        if (this.isInWater() && this.getNavigation().isInProgress()) {
//            this.swimAnimation.startIfStopped(this.tickCount);
//        } else {
//            this.swimAnimation.stop();
//        }


        if ((!this.isInWater()) && this.getNavigation().isInProgress()) {
            this.walkAnimation.startIfStopped(this.tickCount);
        } else {
            this.walkAnimation.stop();
        }


//        if (this.idleAnimationTimeout <= 0) {
//            this.idleAnimationTimeout = 80; //Animation leanght
//            this.idleAnimationState.start(this.tickCount);
//        } else {
//            --this.idleAnimationTimeout;
//        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel serverLevel, Entity entity) {
        this.resetAnimations();
        this.attackAnimation.start(this.tickCount);
        return super.doHurtTarget(serverLevel, entity);
    }

    private void resetAnimations() {
        this.idleAnimationState.stop();
        this.attackAnimation.stop();
        this.swimAnimation.stop();
        this.walkAnimation.stop();
    }

    public static boolean checkSpawnRules(EntityType<? extends Crocodile> pType, @NotNull ServerLevelAccessor pLevel, EntitySpawnReason pReason, BlockPos pPos, RandomSource pRandom) {
        var check1 = !pLevel.getLevel().isRaining();
        var biomes = MoAnimalsTags.BlockTags.CROCODILE_SPAWNABLE_ON;
        var blockBelow = pLevel.getBlockState(pPos.below());
        var check = blockBelow.is(biomes);
        var checkFinal = check && check1;
        return checkFinal;
        //        return pLevel.getBlockState(pPos.below()).is(MoAnimalsTags.BlockTags.CROCODILE_SPAWNABLE_ON);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    @Override
    public int getMaxAirSupply() {
        return 860;
    }

    @Override
    protected int increaseAirSupply(int i) {
        return this.getMaxAirSupply();
    }

    public boolean wantsToAttack(LivingEntity livingEntity) {
        var isInAttackablePosition = false;

        //Both are in Water
        if (this.isInWater() && livingEntity.isInWater()) {
            isInAttackablePosition = true;
        }
        //Both not in water
        if (!(this.isInWater()) && !(livingEntity.isInWater())) {
            isInAttackablePosition = true;
        }


        //When attackable check entitys
        if (isInAttackablePosition) {
            if (!(livingEntity instanceof Creeper) && !(livingEntity instanceof Ghast) && !(livingEntity instanceof ArmorStand)) {
                return true;
            }
        }
        return false;
    }


    public int getRemainingPersistentAngerTime() {
        return (Integer) this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int i) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, i);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    static {
        DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Crocodile.class, EntityDataSerializers.INT);
        PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    }
}
