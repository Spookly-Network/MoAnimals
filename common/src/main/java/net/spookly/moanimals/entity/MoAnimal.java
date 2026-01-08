package net.spookly.moanimals.entity;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public abstract class MoAnimal extends Animal {

    public final AnimationState idleAnimationState = new AnimationState();

    protected MoAnimal(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }
}
