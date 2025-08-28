package net.spookly.moanimals.entity;


import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.spookly.moanimals.item.MoAnimalItems;
import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;


//https://www.ducks.org/hunting/waterfowl-id
//https://info.pangovet.com/pet-breeds/birds/duck-breeds/
//https://birdwatchinghq.com/ducks-of-germany/
public class Duck extends Animal {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    // Einfaches Herden-Flag
    private boolean groupLeader = false;
    private int leaderReevalCooldown = 0;


    public Duck(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        // Kleine Chance, initial Anführer zu sein
        this.groupLeader = this.getRandom().nextFloat() < 0.2f;
        this.moveControl = new FlyingMoveControl(this, /*maxTurn*/ 20, /*hoversInPlace*/ false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5d)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FLYING_SPEED, 0.35)
                .add(Attributes.FOLLOW_RANGE, 24d);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(false); // darf schweben
        return nav;
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(MoAnimalItems.BREADCRUMBS), true));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));

        // Nicht-Anführer folgen dem nächsten Anführer in der Nähe
        this.goalSelector.addGoal(5, new FollowLeaderGoal(this, 1.15, 3.0F, 10.0F));

        // Bodenbewegung (nur wenn am Boden)
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0) {
            @Override
            public boolean canUse() {
                return super.canUse() && Duck.this.onGround();
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && Duck.this.onGround();
            }
        });

        // Schwimmen (nur wenn im Wasser)
        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0, 40) {
            @Override
            public boolean canUse() {
                return Duck.this.isInWaterOrBubble();
            }

            @Override
            public boolean canContinueToUse() {
                return Duck.this.isInWaterOrBubble();
            }
        });


        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        super.registerGoals();
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        return false; // Enten gleiten, kein Fallschaden
    }


    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(MoAnimalItems.BREADCRUMBS.get());
    }

    //TODO: make
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return MoAnimalEntityTypes.DUCK.get().create(serverLevel);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80; //Animation leanght
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isInWaterOrBubble()) {
            // spiele Schwimm-Animation
        } else if (!this.onGround()) {
            // spiele Flatter-Animation
        } else {
            // spiele Geh-/Idle-Animation abhängig von Bewegung
        }

    }

    public static boolean checkDuckSpawnRules(EntityType<? extends Duck> pType, @NotNull ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBlockState(pPos.below()).is(MoAnimalsTags.BlockTags.DUCKS_SPAWNABLE_ON);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();


        } else {
            // Ab und zu die Leader-Verteilung neu bewerten, um natürlichere Gruppen zu erhalten
            if (--leaderReevalCooldown <= 0) {
                leaderReevalCooldown = 200 + this.getRandom().nextInt(200); // 10–20 Sekunden
                reassignLeaderIfNeeded();
            }
        }

    }

    private void reassignLeaderIfNeeded() {
        // Wenn in 12 Blöcken kein anderer Leader da ist, kleine Chance, selbst Leader zu werden
        List<Duck> nearby = this.level().getEntitiesOfClass(Duck.class, this.getBoundingBox().inflate(12.0),
                d -> d != this && d.isAlive());
        boolean hasLeaderNearby = nearby.stream().anyMatch(Duck::isGroupLeader);

        if (!hasLeaderNearby && this.getRandom().nextFloat() < 0.25f) {
            this.groupLeader = true;
        } else if (hasLeaderNearby) {
            // Bei zu vielen Leadern in Nähe: nur der mit kleinster UUID bleibt Leader
            nearby.add(this);
            Duck primary = nearby.stream()
                    .filter(Duck::isGroupLeader)
                    .min(Comparator.comparing(Entity::getUUID))
                    .orElse(this);
            this.groupLeader = this == primary;
        }
    }

    public boolean isGroupLeader() {
        return groupLeader;
    }

    private static class FollowLeaderGoal extends Goal {
        private final Duck duck;
        private final double speed;
        private final float stopDistance;
        private final float areaSize;
        private Duck leader;
        private int recalcCooldown;

        FollowLeaderGoal(Duck duck, double speed, float stopDistance, float areaSize) {
            this.duck = duck;
            this.speed = speed;
            this.stopDistance = stopDistance;
            this.areaSize = areaSize;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (duck.isBaby()) return false; // Babys folgen Eltern separat
            if (duck.isGroupLeader()) return false;

            this.leader = findNearestLeader();
            if (this.leader == null) return false;

            double dist = this.duck.distanceToSqr(this.leader);
            return dist > (stopDistance * stopDistance) && dist < (areaSize * areaSize);
        }

        @Override
        public boolean canContinueToUse() {
            if (leader == null || !leader.isAlive() || duck.isGroupLeader()) return false;
            double dist = duck.distanceToSqr(leader);
            return dist > (stopDistance * stopDistance) && dist < (areaSize * areaSize) && !duck.getNavigation().isDone();
        }

        @Override
        public void start() {
            recalcCooldown = 0;
            moveTowardLeader();
        }

        @Override
        public void stop() {
            leader = null;
            duck.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (--recalcCooldown <= 0) {
                recalcCooldown = 10; // alle 0,5s neu planen
                // Leader neu bestimmen, falls sinnvoll (wechselnde Gruppen)
                Duck nearest = findNearestLeader();
                if (nearest != null && nearest != leader) {
                    leader = nearest;
                }
                moveTowardLeader();
            }
        }

        private void moveTowardLeader() {
            if (leader != null) {
                duck.getNavigation().moveTo(leader, speed);
            }
        }

        @Nullable private Duck findNearestLeader() {
            AABB box = duck.getBoundingBox().inflate(areaSize);
            List<Duck> leaders = duck.level().getEntitiesOfClass(Duck.class, box,
                    d -> d != duck && d.isAlive() && d.isGroupLeader());
            return leaders.stream()
                    .min(Comparator.comparingDouble(duck::distanceToSqr))
                    .orElse(null);
        }
    }


}
