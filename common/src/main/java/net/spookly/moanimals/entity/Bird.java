package net.spookly.moanimals.entity;

import java.util.Optional;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.spookly.moanimals.entity.animal.BirdVariants;
import net.spookly.moanimals.entity.variant.BirdVariant;
import net.spookly.moanimals.network.syncher.MoAnimalsEntityDataSerializers;
import net.spookly.moanimals.registry.MoAnimalsRegistries;
import net.spookly.moanimals.sounds.MoAnimalsSoundEvents;
import net.spookly.moanimals.util.MoAnimalsTags;
import net.spookly.moanimals.util.MoAnimalsVariantUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;

public class Bird extends MoAnimal implements VariantHolder<Holder<BirdVariant>>, FlyingAnimal {
    private static final EntityDataAccessor<Holder<BirdVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Bird.class, MoAnimalsEntityDataSerializers.BIRD_VARIANT);
    public static final Predicate<LivingEntity> PREDATOR_SELECTOR = livingEntity -> {
        EntityType<?> entityType = livingEntity.getType();
        return entityType.is(MoAnimalsTags.EntityTags.BIRD_PREDATORS);
    };

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState chirpAnimationState = new AnimationState();
    public final AnimationState dyingAnimationState = new AnimationState();

    //Flaping
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;

    protected Bird(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 15, false);

        this.idleAnimationTimeout = this.random.nextInt(50) + 50;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));


        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Animal.class, 8.0F, 2.3D, 2.2D, PREDATOR_SELECTOR));
//        this.goalSelector.addGoal(2, new BreedGoal(this, 1));
//        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.15));
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
    public void aiStep() {
        super.aiStep();
        this.calculateFlapping();
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = this.flapSpeed + (!this.onGround() && !this.isPassenger() ? 4 : -1) * 0.5F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.8, 1.0));
        }

        this.flap = this.flap + this.flapping * 2.0F;
    }

    @Override
    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    @Override
    protected void onFlap() {
        this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
        this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 0.8, this.getZ(), 0.0D, 0.0D, 0.0D);
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
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
