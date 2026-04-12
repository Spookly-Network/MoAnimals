package net.spookly.moanimals.entity.ai.goal;

import net.spookly.moanimals.entity.struc.EggLayingAnimal;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.GameRules;

public class EggLayingBreedGoal extends BreedGoal {
    private final EggLayingAnimal<? extends Animal> eggAnimal;

    public EggLayingBreedGoal(EggLayingAnimal<? extends Animal> eggAnimal, double speed) {
        super((Animal) eggAnimal.getOwner(), speed);
        this.eggAnimal = eggAnimal;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.eggAnimal.hasEgg();
    }

    @Override
    protected void breed() {
        Animal animal = this.animal;
        ServerPlayer breedingPlayer = animal.getLoveCause();
        if (breedingPlayer == null) {
            breedingPlayer = this.partner.getLoveCause();
        }

        if (breedingPlayer != null) {
            breedingPlayer.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(breedingPlayer, animal, this.partner, null);
        }

        // Set the egg-laying animal to have an egg
        this.eggAnimal.setHasEgg(true);
        animal.setAge(6000);
        this.partner.setAge(6000);
        animal.resetLove();
        this.partner.resetLove();
        
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            int experience = animal.getRandom().nextInt(7) + 1;
            this.level.addFreshEntity(new ExperienceOrb(this.level, animal.getX(), animal.getY(), animal.getZ(), experience));
        }
    }
}
