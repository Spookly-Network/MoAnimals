package net.spookly.moanimals.entity.struc;

import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.entity.MoAnimal;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class BirdLikeAnimal extends MoAnimal implements FlyingAnimal {
    protected BirdLikeAnimal(EntityType<? extends Animal> entityType, Level level, boolean hoversInPlace) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 15, hoversInPlace);
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

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
            .add(Attributes.MAX_HEALTH, 8d)
            .add(Attributes.MOVEMENT_SPEED, 0.20)
            .add(Attributes.FLYING_SPEED, 0.4F)
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
    public boolean isFlying() {
        return !this.onGround();
    }
}
