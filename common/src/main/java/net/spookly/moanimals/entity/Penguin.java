package net.spookly.moanimals.entity;

import java.util.EnumSet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

//https://mcmobs.fandom.com/wiki/Penguin
public class Penguin extends Animal {

    public static final int TOTAL_AIR_SUPPLY = 200; //1200;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flapAnimationState = new AnimationState();

    public float zBodyRot;
    public float xBodyRot;

    protected Penguin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return super.createNavigation(level);
//        return new AmphibiousPathNavigation(this, level);
//        if (this.isInWaterOrBubble()) {
//            return new WaterBoundPathNavigation(this, level) {
//               @Override
//                protected PathFinder createPathFinder(int i) {
//                    this.nodeEvaluator = new SwimNodeEvaluator(true);
//                    return new PathFinder(this.nodeEvaluator, i);
//                }
//            };
//        }
//        return super.createNavigation(level);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(MoAnimalsTags.ItemTags.RAW_FISHES);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setAirSupply(this.getMaxAirSupply());
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return MoAnimalEntityTypes.PENGUIN.get().create(serverLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreathAirGoal(this) {
            @Override
            public boolean canUse() {
                return Penguin.this.isInWater() && super.canUse();
            }
        });
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PolarBear.class, 10.0f, 0.8f, 1.3f));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25, stack -> stack.is(MoAnimalsTags.ItemTags.RAW_FISHES), true));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25));

        //this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0, 1));
        //this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0));

        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0) {
            @Override
            public boolean canUse() {
                return super.canUse() && !mob.isInWaterOrBubble();
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && !mob.isInWaterOrBubble();
            }

            @Override
            public void tick() {
                super.tick();
                if (mob.isInWaterOrBubble()) return;
                ((Penguin)mob).addParticlesAroundSelf(ParticleTypes.HEART);
            }
        });

        // Schwimmen (nur wenn im Wasser)
        this.goalSelector.addGoal(7, new RandomSwimmingGoal(this, 1.0, 1) {
            @Override
            public boolean canUse() {
                return super.canUse() && mob.isInWaterOrBubble();
            }

            @Override
            public boolean canContinueToUse() {
                return mob.isInWaterOrBubble();
            }
        });

        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new FlapHappyGoal(this));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(
                this, AbstractFish.class, 20, false, false, livingEntity -> livingEntity instanceof AbstractSchoolingFish
        ));
    }

    @Override
    public int getMaxAirSupply() {
        return TOTAL_AIR_SUPPLY;
    }

    public void travel(Vec3 vec3) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add((double)0.0F, -0.005, (double)0.0F));
            }
        } else {
            super.travel(vec3);
        }
    }

    public void aiStep() {
        float rotateSpeed = 1.0F;
        if (this.isInWaterOrBubble()) {
            Vec3 vec3 = this.getDeltaMovement();
            double d = vec3.horizontalDistance();
            this.yBodyRot += (-((float) Mth.atan2(vec3.x, vec3.z)) * (180F / (float) Math.PI) - this.yBodyRot) * 0.1F;
            this.setYRot(this.yBodyRot);
            this.zBodyRot += (float) Math.PI * rotateSpeed * 1.5F;
            this.xBodyRot += (-((float) Mth.atan2(d, vec3.y)) * (180F / (float) Math.PI) - this.xBodyRot) * 0.1F;
        }
        super.aiStep();
    }

    protected void handleAirSupply(int i) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(i - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }

    }

    public void tick() {
        int i = this.getAirSupply();

        if (this.isNoAi()) {
            this.setAirSupply(this.getMaxAirSupply());
        } else {
            this.handleAirSupply(i);
        }

        super.tick();
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 24d)
                .add(Attributes.ATTACK_DAMAGE, 1.5);
    }

    public static boolean checkSpawnRules(EntityType<? extends Penguin> pType, @NotNull ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBlockState(pPos.below()).is(MoAnimalsTags.BlockTags.PENGUIN_SPAWNABLE_ON);
    }

    private void setupAnimationStates() {
        if (this.isInWaterOrBubble()) {
            // spiele Schwimm-Animation
        } else if (!this.onGround()) {
            // spiele Flatter-Animation
        } else {
            this.idleAnimationState.startIfStopped(this.tickCount);
        }

    }

    //TODO remove after Debug
    public void addParticlesAroundSelf(ParticleOptions particleOptions) {
        for(int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.01;
            double e = this.random.nextGaussian() * 0.01;
            double f = this.random.nextGaussian() * 0.01;
            this.level().addParticle(particleOptions, this.getRandomX((double)1.0F), this.getRandomY() + 0.2, this.getRandomZ((double)1.0F), d, e, f);
        }

    }

    class FlapHappyGoal extends Goal {
        private final Penguin penguin;

        private FlapHappyGoal(Penguin penguin) {
            this.penguin = penguin;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void start() {
//            Moanimals.LOGGER.info("FlapHappyGoal started");
            penguin.flapAnimationState.start(Penguin.this.tickCount);
        }

        @Override
        public void tick() {

        }
    }
}
