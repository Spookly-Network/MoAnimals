package net.spookly.moanimals.entity;

import static net.spookly.moanimals.registry.MoAnimalsRegistries.ENTITY_TYPES;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import dev.architectury.registry.registries.RegistrySupplier;

public class MoAnimalEntityTypes {

    public static final RegistrySupplier<EntityType<Duck>> DUCK = ENTITY_TYPES.register("duck", () -> EntityType.Builder.of(Duck::new, MobCategory.CREATURE)
            .sized(0.75f, 0.75f).build("duck"));
    public static final RegistrySupplier<EntityType<Crocodile>> CROCODILE = ENTITY_TYPES.register("crocodile", () -> EntityType.Builder.of(Crocodile::new, MobCategory.CREATURE)
            .sized(0.75f, 0.75f).build("crocodile"));

    public static void init() {
        ENTITY_TYPES.register();
    }
}
