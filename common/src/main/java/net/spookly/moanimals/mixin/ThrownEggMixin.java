package net.spookly.moanimals.mixin;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import net.spookly.moanimals.entity.Duck;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.item.MoAnimalItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEgg.class)
public abstract class ThrownEggMixin extends ThrowableItemProjectile {

    public ThrownEggMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
        method = "onHit",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/animal/Chicken;setAge(I)V"
        ),
        cancellable = true
    )
    private void onChickenCreated(HitResult hitResult, CallbackInfo ci) {
        if (this.getItem().is(MoAnimalItems.DUCK_EGG.get())) {
            // Skip the chicken-related code entirely and inject our duck-spawning logic
            ci.cancel();
            
            if (!this.level().isClientSide) {
                // Spawn a duck instead
                Duck duck = MoAnimalEntityTypes.DUCK.get().create(this.level());
                if (duck != null) {
                    duck.setAge(-24000);
                    duck.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                    
                    // If fudgePositionAfterSizeChange succeeds, add the entity
                    if (duck.fudgePositionAfterSizeChange(EntityDimensions.fixed(0.0F, 0.0F))) {
                        this.level().addFreshEntity(duck);
                    }
                }
                
                // Continue with the discard and particle logic
                this.level().broadcastEntityEvent(this, (byte)3);
                this.discard();
            }
        }
    }
}
