package net.spookly.moanimals.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Penguin extends Animal {

    public static final int TOTAL_AIR_SUPPLY = 4800;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flapAnimationState = new AnimationState();

    protected Penguin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
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
        super.registerGoals();

        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PolarBear.class, 10.0f, 0.8f, 1.3f));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(MoAnimalsTags.ItemTags.RAW_FISHES), true));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));

        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0, 1));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0));

        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0) {
            @Override
            public boolean canUse() {
                return super.canUse() && Penguin.this.onGround();
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && Penguin.this.onGround();
            }
        });

        // Schwimmen (nur wenn im Wasser)
        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0, 40) {
            @Override
            public boolean canUse() {
                return Penguin.this.isInWaterOrBubble();
            }

            @Override
            public boolean canContinueToUse() {
                return Penguin.this.isInWaterOrBubble();
            }
        });

        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new FlapHappyGoal(this));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Override
    public int getMaxAirSupply() {
        return TOTAL_AIR_SUPPLY;
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
            this.setupAnimationStates();
        }

        super.tick();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 24d);
    }

    public static boolean checkSpawnRules(EntityType<? extends Penguin> pType, @NotNull ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBlockState(pPos.below()).is(MoAnimalsTags.BlockTags.PENGUIN_SPAWNABLE_ON);
    }

    private void setupAnimationStates() {
            this.idleAnimationState.startIfStopped(this.tickCount);

        if (this.isInWaterOrBubble()) {
            // spiele Schwimm-Animation
        } else if (!this.onGround()) {
            // spiele Flatter-Animation
        } else {
            // spiele Geh-/Idle-Animation abhängig von Bewegung
        }

    }

    private static class FlapHappyGoal extends Goal {
        private final Penguin penguin;

        private FlapHappyGoal(Penguin penguin) {
            this.penguin = penguin;
        }

        @Override
        public boolean canUse() {
            return false;
        }

        @Override
        public void start() {
            penguin.flapAnimationState.start(penguin.tickCount);
            penguin.doWaterSplashEffect();
        }
    }
}
