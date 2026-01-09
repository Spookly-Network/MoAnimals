package net.spookly.moanimals.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.spookly.moanimals.block.MoAnimalBlocks;
import net.spookly.moanimals.entity.ai.goal.EggLayingBreedGoal;
import net.spookly.moanimals.entity.ai.goal.LayEggGoal;
import net.spookly.moanimals.entity.struc.AbstractEggLayingAnimal;
import net.spookly.moanimals.util.MoAnimalsTags;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;

//OwnableEntity, PlayerRideableJumping, Saddleable, Pig
public class Ostrich extends AbstractEggLayingAnimal {

    protected Ostrich(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public Block getEggBlock() {
        // Override this to return a different block (e.g., a custom ostrich egg block)
        return MoAnimalBlocks.OSTRICH_EGG.get(); // Default to turtle eggs
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.OAK_SAPLING);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return MoAnimalEntityTypes.OSTRICH.get().create(serverLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.5));

        this.goalSelector.addGoal(2, new EggLayingBreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LayEggGoal(this, 32));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25, this::isFood, true));

        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));

        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setHomePos(this.blockPosition());
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20d)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 24d)
                .add(Attributes.ATTACK_DAMAGE, 3);
    }


    public static boolean checkSpawnRules(EntityType<? extends Ostrich> pType, @NotNull ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBlockState(pPos.below()).is(MoAnimalsTags.BlockTags.OSTRICH_SPAWNABLE_ON);
    }
}
