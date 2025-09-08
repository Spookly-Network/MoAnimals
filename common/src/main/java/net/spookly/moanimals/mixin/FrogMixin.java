package net.spookly.moanimals.mixin;

import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.frog.Frog;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Frog.class)
public abstract class FrogMixin {

//    @Shadow
//    protected GoalSelector goalSelector;
//
//    @Inject(method = "registerGoals()V", @At("HEAD"))
//    void registerGoals(CallbackInfo ci) {
//        goalSelector.addGoal();
//    }

}
