package net.spookly.moanimals.entity;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.spookly.moanimals.entity.animal.ButterflyVariants;
import net.spookly.moanimals.entity.variant.ButterflyVariant;
import net.spookly.moanimals.network.syncher.MoAnimalsEntityDataSerializers;
import net.spookly.moanimals.registry.MoAnimalsRegistries;
import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

public class Butterfly extends Animal implements VariantHolder<Holder<ButterflyVariant>>, FlyingAnimal {
    private static final EntityDataAccessor<Holder<ButterflyVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Butterfly.class, MoAnimalsEntityDataSerializers.BUTTERFLY_VARIANT);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flapsAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int flapAnimationTimeout = 0;

    protected Butterfly(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 7, true);


    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        RegistryAccess registryAccess = this.registryAccess();
        Registry<ButterflyVariant> registry = registryAccess.registryOrThrow(MoAnimalsRegistries.BUTTERFLY_VARIANT);
        builder.define(DATA_VARIANT_ID, (Holder<ButterflyVariant>) registry.getHolder(ButterflyVariants.DEFAULT).or(registry::getAny).orElseThrow());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector
                .addGoal(1, new AvoidEntityGoal(this, Player.class, 16.0F, 1.6, 1.4, livingEntity -> true));
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

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true); // darf schweben
        return nav;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();


        }
    }

    public ResourceLocation getTexture() {
        ButterflyVariant butterflyVariant = (ButterflyVariant) this.getVariant().value();
        return butterflyVariant.texture();
    }

    @Override
    public void setVariant(Holder<ButterflyVariant> holder) {
        this.entityData.set(DATA_VARIANT_ID, holder);
    }

    @Override
    public @NotNull Holder<ButterflyVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    private void setupAnimationStates() {
//        this.idleAnimationState.animateWhen(this.);
        if (this.flapAnimationTimeout <= 0) {
            this.flapAnimationTimeout=60;
            this.flapsAnimationState.start(this.tickCount);
        } else {
            --this.flapAnimationTimeout;
        }

//        if (this.flapsAnimationState.animateWhen(); <= 0) {
//            this.idleAnimationTimeout = 80; //Animation leanght
//            this.idleAnimationState.start(this.tickCount);
//        } else {
//            --this.idleAnimationTimeout;
//        }
    }

    //FIXME: Check always fails
    public static boolean checkSpawnRules(EntityType<? extends Butterfly> pType, @NotNull ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        var check1 = !pLevel.getLevel().isRaining();
        var biomes = MoAnimalsTags.BlockTags.BUTTERFLY_SPAWNABLE_ON;
        var blockBelow = pLevel.getBlockState(pPos.below());
        var check = blockBelow.is(biomes);
        var checkFinal = check && check1;
        return checkFinal;
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.getVariant().unwrapKey().ifPresent((resourceKey) -> compoundTag.putString("variant", resourceKey.location().toString()));
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        Optional.ofNullable(ResourceLocation.tryParse(compoundTag.getString("variant"))).map((resourceLocation) -> ResourceKey.create(MoAnimalsRegistries.BUTTERFLY_VARIANT, resourceLocation)).flatMap((resourceKey) -> this.registryAccess().registryOrThrow(MoAnimalsRegistries.BUTTERFLY_VARIANT).getHolder(resourceKey)).ifPresent(this::setVariant);
    }

    @Nullable public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        Holder<Biome> holder = serverLevelAccessor.getBiome(this.blockPosition());
        Holder<ButterflyVariant> holder2;

        holder2 = ButterflyVariants.getSpawnVariant(this.registryAccess(), holder);
        spawnGroupData = new ButterflyGroupData(holder2);

        this.setVariant(holder2);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.FOLLOW_RANGE, 24d);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    public static class ButterflyGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<ButterflyVariant> type;

        public ButterflyGroupData(Holder<ButterflyVariant> holder) {
            super(false);
            this.type = holder;
        }
    }
}
