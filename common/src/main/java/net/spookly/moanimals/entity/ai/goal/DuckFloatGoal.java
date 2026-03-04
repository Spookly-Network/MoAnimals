package net.spookly.moanimals.entity.ai.goal;

import static net.spookly.moanimals.Moanimals.LOGGER;

import net.spookly.moanimals.entity.Duck;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.phys.Vec3;

public class DuckFloatGoal extends FloatGoal {

    private static final double BUOYANCY_STRENGTH = 0.25D;  // correction force
    private static final double WATER_DAMPING_Y = 0.52D;     // vertical damping in water
    private static final double MAX_UPWARD_SPEED = 0.01D;
    private static final double MIN_BUOYANCY_PUSH = 0.055D;
    private static final double ALLOWED_WATER_OFFSET = 0.1D;

    private final Duck duck;

    double TARGET_FLOAT_HEIGHT;

    public DuckFloatGoal(Duck duck) {
      super(duck);
      this.duck = duck;
      TARGET_FLOAT_HEIGHT = duck.isBaby() ? 0.1 : 0.16;
    }

    @Override
    public boolean canUse() {
//      double waterHeight = this.duck.getFluidHeight(FluidTags.WATER);
//      double rawError = TARGET_FLOAT_HEIGHT + waterHeight;
//      double error = Math.abs(rawError) > ALLOWED_WATER_OFFSET ? rawError : 0.0D;
//
//      boolean use = this.duck.isInWater();
//      return use || this.duck.isInLava();
      return this.duck.isInWater() || this.duck.isInLava();
    }



    @Override
    public void tick() {
      Vec3 vel = this.duck.getDeltaMovement();
      double waterHeight = this.duck.getFluidHeight(FluidTags.WATER);

      double rawError = TARGET_FLOAT_HEIGHT + waterHeight;
      double error = Math.abs(rawError) > ALLOWED_WATER_OFFSET ? rawError : 0.0D;

      if (error == 0.0D) {
        this.duck.setDeltaMovement(vel.x, 0.00D, vel.z);
        return;
      }

      double correction = error * BUOYANCY_STRENGTH;

      // Always force enough upward correction when too deep
      if (error > 0.0D) correction = Math.max(-correction, -MIN_BUOYANCY_PUSH);
      if (error < 0.0D) correction = Math.min(+correction, MIN_BUOYANCY_PUSH);

      this.duck.setCustomName(Component.literal(String.format("%s", error)));
      this.duck.setCustomNameVisible(true);
//
      double newY = WATER_DAMPING_Y * correction;
      newY = Mth.clamp(newY, -0.01D, MAX_UPWARD_SPEED);
      LOGGER.info(String.format("%s", newY));
      this.duck.setDeltaMovement(vel.x, newY, vel.z);
    }


  }
