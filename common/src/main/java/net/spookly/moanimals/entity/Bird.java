package net.spookly.moanimals.entity;

import java.util.Optional;

import net.spookly.moanimals.util.MoAnimalsVariantUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.spookly.moanimals.entity.animal.BirdVariants;
import net.spookly.moanimals.entity.variant.BirdVariant;
import net.spookly.moanimals.network.syncher.MoAnimalsEntityDataSerializers;
import net.spookly.moanimals.registry.MoAnimalsRegistries;
import net.spookly.moanimals.sounds.MoAnimalsSoundEvents;
import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.client.animation.definitions.ArmadilloAnimation;
import net.minecraft.client.animation.definitions.CamelAnimation;
import net.minecraft.client.animation.definitions.SnifferAnimation;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

public class Bird extends MoAnimal implements VariantHolder<Holder<BirdVariant>>, FlyingAnimal {
    private static final EntityDataAccessor<Holder<BirdVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Bird.class, MoAnimalsEntityDataSerializers.BIRD_VARIANT);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState chirpAnimationState = new AnimationState();
    public final AnimationState dyingAnimationState = new AnimationState();

    protected Bird(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 15, false);

        this.idleAnimationTimeout = this.random.nextInt(50) + 50;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.15));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        RegistryAccess registryAccess = this.registryAccess();
        Registry<BirdVariant> registry = registryAccess.registryOrThrow(MoAnimalsRegistries.BIRD_VARIANT);
        builder.define(DATA_VARIANT_ID, registry.getHolder(BirdVariants.DEFAULT).or(registry::getAny).orElseThrow());
    }

    @Override
    public void setVariant(Holder<BirdVariant> object) {
        this.entityData.set(DATA_VARIANT_ID, object);
    }

    @Override
    public @NotNull Holder<BirdVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.getVariant().unwrapKey().ifPresent((resourceKey) -> compoundTag.putString("variant", resourceKey.location().toString()));
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        Optional.ofNullable(ResourceLocation.tryParse(compoundTag.getString("variant"))).map((resourceLocation) -> ResourceKey.create(MoAnimalsRegistries.BIRD_VARIANT, resourceLocation)).flatMap((resourceKey) -> this.registryAccess().registryOrThrow(MoAnimalsRegistries.BIRD_VARIANT).getHolder(resourceKey)).ifPresent(this::setVariant);
    }

    public ResourceLocation getTexture() {
        return this.getVariant().value().texture();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
            .add(Attributes.MAX_HEALTH, 8d)
            .add(Attributes.MOVEMENT_SPEED, 0.20)
            .add(Attributes.FLYING_SPEED, 0.35)
            .add(Attributes.FOLLOW_RANGE, 24d);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanPassDoors(true);
        navigation.setCanFloat(true);
        return navigation;
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
    protected @Nullable SoundEvent getAmbientSound() {
        return MoAnimalsSoundEvents.BIRD_CHIRP.get();
    }

    public static boolean checkSpawnRules(EntityType<? extends Bird> pType, @NotNull ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBlockState(pPos.below()).is(MoAnimalsTags.BlockTags.BIRD_SPAWNABLE_ON);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    public @NotNull SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        Registry<BirdVariant> registry = this.registryAccess().registryOrThrow(MoAnimalsRegistries.BIRD_VARIANT);
        ResourceKey<BirdVariant> defaultVariantKey = BirdVariants.DEFAULT;

        Holder<Biome> biomeHolder = serverLevelAccessor.getBiome(this.blockPosition());
        Holder<BirdVariant> holder = MoAnimalsVariantUtils.getSpawnVariantForBiome(registry, biomeHolder, defaultVariantKey);
        spawnGroupData = new Bird.BirdGroupData(holder);
        this.setVariant(holder);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    public void die(DamageSource damageSource) {
        this.dyingAnimationState.startIfStopped(this.tickCount);
        return;
//        super.die(damageSource);
    }

    private void setupAnimationStates() {
        if (this.onGround()) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = this.random.nextInt(300) + 90;
                this.idleAnimationState.start(this.tickCount);
            }
        }

        this.idleAnimationTimeout--;
    }

    public static class BirdGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<BirdVariant> type;

        public BirdGroupData(Holder<BirdVariant> holder) {
            super(false);
            this.type = holder;
        }
    }
}
