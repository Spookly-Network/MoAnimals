package net.spookly.moanimals.core.neoforge;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.function.Supplier;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import dev.architectury.registry.registries.RegistrySupplier;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.spookly.moanimals.entity.Duck;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.entity.Racoon;
import net.spookly.moanimals.entity.RacoonVariant;
import net.spookly.moanimals.network.syncher.MoAnimalsEntityDataSerializers;
import org.jetbrains.annotations.NotNull;

public class CommonPlatformHelperImpl {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, MOD_ID);
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZER = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MOD_ID);

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntityType(String name, Supplier<BlockEntityType<T>> factory) {
        return BLOCK_ENTITY_TYPES.register(name, factory);
    }

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        // T registry = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, name), item.get());
        return ITEMS.register(name, item);
    }

    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEggItem(@NotNull String name, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return registerItem(name, () -> new SpawnEggItem(entityType.get(), backgroundColor, highlightColor, new Item.Properties()));
    }

    public static <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> soundEvent) {
        return SOUND_EVENTS.register(name, soundEvent);
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(factory, category).sized(width, height).clientTrackingRange(clientTrackingRange).build(name));
    }

    public static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(String name, Supplier<MenuType<T>> supplier) {
        return MENU_TYPES.register(name, supplier);
    }

    public static void openMenu(ServerPlayer player, MenuProvider provider) {
        player.openMenu(provider);
    }

    public static <T extends Potion> Supplier<T> registerPotion(String name, Supplier<T> potion) {
        return POTIONS.register(name, potion);
    }

    public static <T> Supplier<EntityDataSerializer<T>> registerEntityDataSerializers(String name, Supplier<EntityDataSerializer<T>> serializer) {
//        NeoForgeRegistries.ENTITY_DATA_SERIALIZERS.regregister(name, serializer)
        return ENTITY_DATA_SERIALIZER.register(name, serializer);
    }

//    public static void registerBrewingRecipe(Potion input, Item ingredient, Potion output) {
//        BrewingRecipeRegistry.addRecipe(new NaturalistBrewingRecipe(input, ingredient, output));
//    }
//
//    public static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.@NotNull SpawnPredicate<T> decoratorPredicate) {
//        SpawnPlacements.register(entityType, decoratorType, heightMapType, decoratorPredicate);
//    }
//
//    public static void registerCompostable(float chance, ItemLike item) {
//        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
//    }

    public static void registerRecipes(String name, Supplier<RecipeType<?>> type, Supplier<RecipeSerializer<?>> serializer) {
        RECIPE_TYPES.register(name, type);
        RECIPE_SERIALIZERS.register(name, serializer);
    }

    public static TagKey<Item> getShearsTag() {
        return Tags.Items.TOOLS_SHEAR;
    }

}
