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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

public class Butterfly extends Animal implements VariantHolder<Holder<ButterflyVariant>>, FlyingAnimal {
    private static final EntityDataAccessor<Holder<ButterflyVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Butterfly.class, MoAnimalsEntityDataSerializers.BUTTERFLY_VARIANT);
    public static final int TICKS_PER_FLAP = Mth.ceil(1.4959966F);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flapAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();

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
        this.goalSelector.addGoal(1, new SeekShelterIfRainingGoal(1.2));
        this.goalSelector
                .addGoal(1, new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.4, 1.4));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0));
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
        if (this.onGround()) {
            this.sitAnimationState.startIfStopped(this.tickCount);
            this.flyAnimationState.stop();
            this.flapAnimationState.stop();
        } else {
            this.flyAnimationState.startIfStopped(this.tickCount);
            this.sitAnimationState.stop();
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

    @Override
    public boolean isFlapping() {
        return this.isFlying() && this.tickCount % TICKS_PER_FLAP == 0;
    }

    public static class ButterflyGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<ButterflyVariant> type;

        public ButterflyGroupData(Holder<ButterflyVariant> holder) {
            super(false);
            this.type = holder;
        }
    }

    class SeekShelterIfRainingGoal extends FleeSunGoal {
        private int interval = reducedTickDelay(100);

        public SeekShelterIfRainingGoal(final double d) {
            super(Butterfly.this, d);
        }

        @Override
        public boolean canUse() {
            if (Butterfly.this.level().isRaining()) return this.setWantedPos();
            return false;

//            if (!Butterfly.this.isSleeping() && this.mob.getTarget() == null) {
//                if (Butterfly.this.level().isThundering() && Butterfly.this.level().canSeeSky(this.mob.blockPosition())) {
//                    return this.setWantedPos();
//                } else if (this.interval > 0) {
//                    this.interval--;
//                    return false;
//                } else {
//                    this.interval = 100;
//                    BlockPos blockPos = this.mob.blockPosition();
//                    return Butterfly.this.level().isDay() && Butterfly.this.level().canSeeSky(blockPos) && !((ServerLevel)Butterfly.this.level()).isVillage(blockPos) && this.setWantedPos();
//                }
//            } else {
//                return false;
//            }
        }

        @Override
        public void start() {
//            Butterfly.this.clearStates();
            super.start();
        }
    }

    abstract class ButterflyBehaviorGoal extends Goal {
//        private final TargetingConditions alertableTargeting = TargetingConditions.forCombat().range((double)12.0F).ignoreLineOfSight().selector(Racoon.this.new FoxAlertableEntitiesSelector());

        protected boolean hasShelter() {
            BlockPos blockPos = BlockPos.containing(Butterfly.this.getX(), Butterfly.this.getBoundingBox().maxY, Butterfly.this.getZ());
            return !Butterfly.this.level().canSeeSky(blockPos) && Butterfly.this.getWalkTargetValue(blockPos) >= 0.0F;
        }

//        protected boolean alertable() {
//            return !Racoon.this.level().getNearbyEntities(LivingEntity.class, this.alertableTargeting, Fox.this, Fox.this.getBoundingBox().inflate((double)12.0F, (double)6.0F, (double)12.0F)).isEmpty();
//        }
    }
}
