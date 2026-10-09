package net.spookly.moanimals.mixin;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.spookly.moanimals.entity.MoAnimalEntityTypes;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;

@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal {

    @Unique private static final Predicate<LivingEntity> MOANIMALS_PREY_SELECTOR = livingEntity -> {
        EntityType<?> entityType = livingEntity.getType();
        return entityType == MoAnimalEntityTypes.BIRD || entityType == MoAnimalEntityTypes.SEAGULL || entityType == MoAnimalEntityTypes.DUCK;
    };

    protected WolfMixin(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals()V", at = @At("TAIL"))
    private void moanimals$addWolfTargets(CallbackInfo ci) {
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, MOANIMALS_PREY_SELECTOR));
    }

}
