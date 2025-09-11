package net.spookly.moanimals.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.animal.frog.Frog;

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
