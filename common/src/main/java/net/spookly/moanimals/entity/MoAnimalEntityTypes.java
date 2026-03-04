package net.spookly.moanimals.entity;

import static net.spookly.moanimals.Moanimals.MOD_ID;
import static net.spookly.moanimals.registry.MoAnimalsRegistries.ENTITY_TYPES;

import dev.architectury.registry.registries.RegistrySupplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class MoAnimalEntityTypes {

    public static final RegistrySupplier<EntityType<Duck>> DUCK = register("duck", EntityType.Builder.of(Duck::new, MobCategory.CREATURE)
            .sized(0.5f, 0.55f)
    );

    public static final RegistrySupplier<EntityType<Crocodile>> CROCODILE = register("crocodile", EntityType.Builder.of(Crocodile::new, MobCategory.CREATURE)
            .sized(0.8f, 0.6f)
    );

    public static final RegistrySupplier<EntityType<Racoon>> RACOON = register("racoon", EntityType.Builder.of(Racoon::new, MobCategory.CREATURE)
            .sized(.7f, 0.75f)
            .eyeHeight(0.5f)
    );

    public static final RegistrySupplier<EntityType<Butterfly>> BUTTERFLY = register("butterfly", EntityType.Builder.of(Butterfly::new, MobCategory.AMBIENT)
            .sized(0.70f, 0.1f)
    );

    public static final RegistrySupplier<EntityType<Snail>> SNAIL = register("snail", EntityType.Builder.of(Snail::new, MobCategory.CREATURE)
            .sized(0.35f, 0.4f)
    );

    public static final RegistrySupplier<EntityType<Penguin>> PENGUIN = register("penguin",  EntityType.Builder.of(Penguin::new, MobCategory.CREATURE)
            .sized(0.5f, 1.15f)
            .eyeHeight(0.85F)
    );

    public static final RegistrySupplier<EntityType<Ostrich>> OSTRICH =
            register("ostrich", EntityType.Builder.of(Ostrich::new, MobCategory.CREATURE)
            .sized(1.5f, 2.7f)
            .eyeHeight(2.5f));

    public static void init() {
        ENTITY_TYPES.register();
    }

    private static <T extends Entity> RegistrySupplier<EntityType<T>> register(String string, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(string, () -> builder.build(moanimalsEntityId(string)));
    }

    public static ResourceKey<EntityType<?>> moanimalsEntityId(String string) {
        return ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, string));
    }
}
