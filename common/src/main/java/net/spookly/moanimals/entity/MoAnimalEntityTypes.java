package net.spookly.moanimals.entity;

import static net.spookly.moanimals.registry.MoAnimalsRegistries.ENTITY_TYPES;

import dev.architectury.registry.registries.RegistrySupplier;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class MoAnimalEntityTypes {

    public static final RegistrySupplier<EntityType<Duck>> DUCK = ENTITY_TYPES.register("duck", () -> EntityType.Builder.of(Duck::new, MobCategory.CREATURE)
            .sized(0.5f, 0.5f)
            .build("duck"));
    public static final RegistrySupplier<EntityType<Crocodile>> CROCODILE = ENTITY_TYPES.register("crocodile", () -> EntityType.Builder.of(Crocodile::new, MobCategory.CREATURE)
            .sized(0.8f, 0.6f).build("crocodile"));
    public static final RegistrySupplier<EntityType<Racoon>> RACOON = ENTITY_TYPES.register("racoon", () -> EntityType.Builder.of(Racoon::new, MobCategory.CREATURE)
            .sized(.7f, 0.75f)
            .eyeHeight(0.5f)
            .build("racoon")
    );
    public static final RegistrySupplier<EntityType<Butterfly>> BUTTERFLY = ENTITY_TYPES.register("butterfly", () -> EntityType.Builder.of(Butterfly::new, MobCategory.AMBIENT)
            .sized(0.1f, 0.5f)
            .build("butterfly")
    );

    public static void init() {
        ENTITY_TYPES.register();
    }
}
