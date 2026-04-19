package net.spookly.moanimals.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.spookly.moanimals.entity.struc.BirdLikeAnimal;
import net.spookly.moanimals.sounds.MoAnimalsSoundEvents;
import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class Seagull extends BirdLikeAnimal {
    private static final int INLAND_GROUND_TIME_BEFORE_TAKEOFF = 30;
    private static final int COASTAL_GROUND_TIME_BEFORE_TAKEOFF = 120;
    private static final int HIGH_FLIGHT_MIN_HEIGHT = 15;
    private static final int HIGH_FLIGHT_MAX_HEIGHT = 30;
    private static final int HIGH_FLIGHT_SEARCH_ATTEMPTS = 15;
    private static final int COASTAL_LANDING_SEARCH_ATTEMPTS = 12;
    private static final double TAKEOFF_SPEED = 1.2;
    private static final double MIN_TAKEOFF_LIFT = 0.35;
    private static final float HIGH_FLIGHT_CHANCE = 0.85F;

    public final AnimationState dyingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int groundTime = 0;

    protected Seagull(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level, false);
        this.idleAnimationTimeout = this.random.nextInt(50) + 50;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.15));
        this.goalSelector.addGoal(4, new SeagullWanderGoal(this, 1.1));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
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
    public void tick() {
        super.tick();

        if (this.onGround()) {
            this.groundTime++;
            if (!this.level().isClientSide() && this.shouldTakeOff()) {
                this.takeOff();
            }
        } else {
            this.groundTime = 0;
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return MoAnimalsSoundEvents.SEAGULL_CHIRP.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 300;
    }

    @Override
    protected float getSoundVolume() {
        return 2F;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
            @Override
            public boolean isStableDestination(BlockPos blockPos) {
                return this.level.isEmptyBlock(blockPos) && this.level.isEmptyBlock(blockPos.above());
            }
        };

        navigation.setCanOpenDoors(false);
        navigation.setCanPassDoors(true);
        navigation.setCanFloat(true);
        return navigation;
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

    public static boolean checkSpawnRules(EntityType<? extends Seagull> pType, @NotNull ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBlockState(pPos.below()).is(MoAnimalsTags.BlockTags.SEAGULL_SPAWNABLE_ON);
    }

    private boolean shouldTakeOff() {
        if (this.isInWater()) {
            return true;
        }

        if (this.isBaby() || this.isPassenger()) {
            return false;
        }

        int maxGroundTime = this.isCoastalBiome(this.blockPosition()) ? COASTAL_GROUND_TIME_BEFORE_TAKEOFF : INLAND_GROUND_TIME_BEFORE_TAKEOFF;
        return this.groundTime >= maxGroundTime;
    }

    private void takeOff() {
        Vec3 targetPos = this.findHighAirTarget(18);
        if (targetPos != null) {
            this.moveTowards(targetPos, TAKEOFF_SPEED);
        }

        Vec3 movement = this.getDeltaMovement();
        this.setDeltaMovement(movement.x, Math.max(movement.y, MIN_TAKEOFF_LIFT), movement.z);
        this.hasImpulse = true;
    }

    private boolean isCoastalBiome(BlockPos blockPos) {
        return this.level().getBiome(blockPos).is(MoAnimalsTags.BiomeTags.SEAGULL_SPAWNABLE_IN);
    }

    @Nullable private Vec3 findHighAirTarget(int horizontalRange) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < HIGH_FLIGHT_SEARCH_ATTEMPTS; i++) {
            int targetX = Mth.floor(this.getX()) + Mth.nextInt(this.random, -horizontalRange, horizontalRange);
            int targetZ = Mth.floor(this.getZ()) + Mth.nextInt(this.random, -horizontalRange, horizontalRange);
            int terrainHeight = this.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, targetX, targetZ);

            int minTargetY = Math.max(terrainHeight + HIGH_FLIGHT_MIN_HEIGHT, this.level().getSeaLevel() + 4);
            int maxTargetY = Math.min(terrainHeight + HIGH_FLIGHT_MAX_HEIGHT, this.level().getMaxBuildHeight() - 2);
            if (maxTargetY <= minTargetY) {
                continue;
            }

            int targetY = Mth.nextInt(this.random, minTargetY, maxTargetY);
            mutableBlockPos.set(targetX, targetY, targetZ);
            if (this.hasOpenAirAt(mutableBlockPos)) {
                return Vec3.atBottomCenterOf(mutableBlockPos);
            }
        }

        return null;
    }

    @Nullable private Vec3 findCoastalLandingTarget(int horizontalRange) {
        if (!this.isCoastalBiome(this.blockPosition())) {
            return null;
        }

        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < COASTAL_LANDING_SEARCH_ATTEMPTS; i++) {
            int targetX = Mth.floor(this.getX()) + Mth.nextInt(this.random, -horizontalRange, horizontalRange);
            int targetZ = Mth.floor(this.getZ()) + Mth.nextInt(this.random, -horizontalRange, horizontalRange);
            int targetY = this.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, targetX, targetZ);
            mutableBlockPos.set(targetX, targetY, targetZ);

            if (!this.isCoastalBiome(mutableBlockPos)) {
                continue;
            }

            BlockState groundState = this.level().getBlockState(mutableBlockPos.below());
            if (groundState.is(MoAnimalsTags.BlockTags.SEAGULL_SPAWNABLE_ON)
                && this.hasOpenAirAt(mutableBlockPos)) {
                return Vec3.atBottomCenterOf(mutableBlockPos);
            }
        }

        return null;
    }

    private boolean hasOpenAirAt(BlockPos blockPos) {
        return this.level().isEmptyBlock(blockPos) && this.level().isEmptyBlock(blockPos.above());
    }

    private void moveTowards(Vec3 targetPos, double speed) {
        this.getNavigation().moveTo(targetPos.x, targetPos.y, targetPos.z, speed);
        this.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, speed);
    }

    static class SeagullWanderGoal extends WaterAvoidingRandomFlyingGoal {
        public SeagullWanderGoal(PathfinderMob pathfinderMob, double d) {
            super(pathfinderMob, d);
        }

        @Nullable @Override
        protected Vec3 getPosition() {
            Seagull seagull = (Seagull) this.mob;

            if (this.mob.isInWater()) {
                Vec3 vec3 = seagull.findHighAirTarget(16);
                if (vec3 != null) {
                    return vec3;
                }
            }

            if (this.mob.getRandom().nextFloat() < HIGH_FLIGHT_CHANCE) {
                Vec3 vec3 = seagull.findHighAirTarget(18);
                if (vec3 != null) {
                    return vec3;
                }
            }

            Vec3 vec3 = seagull.findCoastalLandingTarget(10);
            if (vec3 != null) {
                return vec3;
            }

            vec3 = this.getTreePos();
            if (vec3 != null && seagull.isCoastalBiome(BlockPos.containing(vec3))) {
                return vec3;
            }

            vec3 = seagull.findHighAirTarget(12);
            return vec3 == null ? super.getPosition() : vec3;
        }

        @Nullable private Vec3 getTreePos() {
            BlockPos blockPos = this.mob.blockPosition();
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();

            for (BlockPos blockPos2 : BlockPos.betweenClosed(
                Mth.floor(this.mob.getX() - 3.0),
                Mth.floor(this.mob.getY() - 6.0),
                Mth.floor(this.mob.getZ() - 3.0),
                Mth.floor(this.mob.getX() + 3.0),
                Mth.floor(this.mob.getY() + 6.0),
                Mth.floor(this.mob.getZ() + 3.0)
            )) {
                if (!blockPos.equals(blockPos2)) {
                    BlockState blockState = this.mob.level().getBlockState(mutableBlockPos2.setWithOffset(blockPos2, Direction.DOWN));
                    boolean isTreePerch = blockState.getBlock() instanceof LeavesBlock || blockState.is(BlockTags.LOGS);
                    if (isTreePerch && this.mob.level().isEmptyBlock(blockPos2) && this.mob.level().isEmptyBlock(mutableBlockPos.setWithOffset(blockPos2, Direction.UP))) {
                        return Vec3.atBottomCenterOf(blockPos2);
                    }
                }
            }

            return null;
        }
    }
}
