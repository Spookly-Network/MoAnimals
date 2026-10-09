package net.spookly.moanimals.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.spookly.moanimals.entity.Bird;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;

@Mixin(Fox.class)
public abstract class FoxMixin extends Mob {
    protected FoxMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals()V", at = @At("TAIL"))
    private void moanimals$addTargetsToFox(CallbackInfo ci) {
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Bird.class, 10, true, true, null));
    }
}
