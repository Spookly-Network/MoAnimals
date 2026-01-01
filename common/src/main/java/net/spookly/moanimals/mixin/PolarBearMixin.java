package net.spookly.moanimals.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.spookly.moanimals.entity.Penguin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.level.Level;

@Mixin(PolarBear.class)
public abstract class PolarBearMixin extends Mob {

    protected PolarBearMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals()V", at = @At("TAIL"))
    private void moanimals$addTargetPenguin(CallbackInfo ci) {
        // Access targetSelector directly on 'this' since we extend Mob
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Penguin.class, 10, true, true, null));
    }
}
