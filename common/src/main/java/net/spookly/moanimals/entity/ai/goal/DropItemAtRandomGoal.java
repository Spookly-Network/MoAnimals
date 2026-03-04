package net.spookly.moanimals.entity.ai.goal;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.gameevent.GameEvent;

public class DropItemAtRandomGoal extends Goal {

  private final Mob mob;
  private final Item item;

  public int eggTime;

  public DropItemAtRandomGoal(Mob mob, Item item) {
    this.mob = mob;
    this.item = item;
    setNewEggTime();
  }

  @Override
  public boolean canUse() {
    return !this.mob.level().isClientSide && this.mob.isAlive() && !this.mob.isBaby() && --this.eggTime <= 0;
  }

  @Override
  public void start() {
    var level = this.mob.level();
    if (level instanceof ServerLevel serverLevel) {
      this.mob.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.mob.getRandom().nextFloat() - this.mob.getRandom().nextFloat()) * 0.2F + 1.0F);
      this.mob.spawnAtLocation(serverLevel, item);
      this.mob.gameEvent(GameEvent.ENTITY_PLACE);
      setNewEggTime();
    }
  }

  private void setNewEggTime() {
    this.eggTime = this.mob.getRandom().nextInt(6000) + 6000;
  }
}
