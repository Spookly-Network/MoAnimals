package net.spookly.moanimals.block;

import static net.spookly.moanimals.Moanimals.LOGGER;
import static net.spookly.moanimals.Moanimals.MOD_ID;
import static net.spookly.moanimals.registry.MoAnimalsRegistries.BLOCK_ENTITYS;

import dev.architectury.registry.registries.RegistrySupplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MoAnimalsBlockEntityTypes {

    public static final RegistrySupplier<BlockEntityType<JarBlockEntity>> JAR = BLOCK_ENTITYS.register(
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "jar"),
        () -> BlockEntityType.Builder.of(
            JarBlockEntity::new,
            MoAnimalBlocks.JAR.get()
        ).build(null));

    public static void init() {
        LOGGER.info("Registering block entity types");
        BLOCK_ENTITYS.register();
        LOGGER.info("✓ Registering block entity types");
    }
}
