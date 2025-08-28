package net.spookly.moanimals.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import net.spookly.moanimals.entity.animal.ButterflyVariants;
import net.spookly.moanimals.network.syncher.MoAnimalsEntityDataSerializers;
import net.spookly.moanimals.registry.MoAnimalsRegistries;
import net.spookly.moanimals.util.MoAnimalsTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Butterfly extends Animal implements VariantHolder<Holder<BasicAnimalVariant>> {
    private static final EntityDataAccessor<Holder<BasicAnimalVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Butterfly.class, MoAnimalsEntityDataSerializers.BASIC_ANIMAL_VARIANT);
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    protected Butterfly(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        RegistryAccess registryAccess = this.registryAccess();
        Registry<BasicAnimalVariant> registry = registryAccess.registryOrThrow(MoAnimalsRegistries.BASIC_ANIMAL_VARIANT);
        builder.define(DATA_VARIANT_ID, (Holder<BasicAnimalVariant>) registry.getHolder(ButterflyVariants.DEFAULT).or(registry::getAny).orElseThrow());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(9, new FloatGoal(this));

        super.registerGoals();
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80; //Animation leanght
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    public static boolean checkSpawnRules(EntityType<? extends Duck> pType, @NotNull ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBlockState(pPos.below()).is(MoAnimalsTags.BlockTags.BUTTERFLY_SPAWNABLE_ON);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();


        }
    }

    @Override
    public void setVariant(Holder<BasicAnimalVariant> object) {

    }

    @Override
    public Holder<BasicAnimalVariant> getVariant() {
        return null;
    }
}
