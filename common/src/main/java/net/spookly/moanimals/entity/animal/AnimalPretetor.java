package net.spookly.moanimals.entity.animal;

import net.spookly.moanimals.entity.MoAnimal;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;

public abstract class AnimalPretetor extends MoAnimal implements Enemy {
    protected AnimalPretetor(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }
}
