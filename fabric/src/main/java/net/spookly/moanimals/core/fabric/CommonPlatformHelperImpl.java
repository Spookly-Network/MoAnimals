package net.spookly.moanimals.core.fabric;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.spookly.moanimals.mixin.SpawnPlacementsInvoker;
import org.jetbrains.annotations.NotNull;

public class CommonPlatformHelperImpl {
    public static <T extends Block> Supplier<T> registerBlock(@NotNull String name, Supplier<T> block) {
        T registry = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), block.get());
        return () -> registry;
    }

    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntityType(String name, @NotNull Supplier<BlockEntityType<T>> factory) {
        BlockEntityType<T> registry = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), factory.get());
        return () -> registry;
    }

    public static <T extends Item> Supplier<T> registerItem(String name, @NotNull Supplier<T> item) {
        T registry = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), item.get());
        return () -> registry;
    }

    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEggItem(@NotNull String name, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        return registerItem(name, () -> new SpawnEggItem(entityType.get(), backgroundColor, highlightColor, new Item.Properties()));
    }

    public static @NotNull Supplier<Item> registerMobBucketItem(@NotNull String name, Supplier<? extends EntityType<?>> entitySupplier, @NotNull Supplier<? extends Fluid> fluidSupplier, Supplier<? extends SoundEvent> soundSupplier) {
        return registerItem(name, () -> new MobBucketItem(entitySupplier.get(), fluidSupplier.get(), soundSupplier.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    }

    public static <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> soundEvent) {
        T registry = Registry.register(BuiltInRegistries.SOUND_EVENT, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), soundEvent.get());
        return () -> registry;
    }

    public static <T extends Entity> @NotNull Supplier<EntityType<T>> registerEntityType(@NotNull String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange) {
        EntityType<T> registry = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), FabricEntityTypeBuilder.create(category, factory).dimensions(EntityDimensions.scalable(width, height)).trackRangeChunks(clientTrackingRange).build());
        return () -> registry;
    }

    public static <T extends AbstractContainerMenu> @NotNull Supplier<MenuType<T>> registerMenuType(String name, @NotNull Supplier<MenuType<T>> menu) {
        var registry = Registry.register(BuiltInRegistries.MENU, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), menu.get());
        return () -> registry;
    }

//    public static <T extends AbstractContainerMenu> MenuType<T> createMenuType(NaturalistMenus.MenuFactory<T> factory) {
//        return new ExtendedScreenHandlerType<>(factory::create);
//    }

    public static void openMenu(ServerPlayer player, MenuProvider provider) {
        player.openMenu(provider);
    }

    public static <T extends Potion> Supplier<T> registerPotion(String name, Supplier<T> potion) {
        T registry = Registry.register(BuiltInRegistries.POTION, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), potion.get());
        return () -> registry;
    }

    public static void registerBrewingRecipe(Potion input, Item ingredient, Potion output) {
//        PotionBrewingInvoker.invokeAddMix(input, ingredient, output);
    }

    public static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacementType decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> decoratorPredicate) {
        SpawnPlacementsInvoker.invokeRegister(entityType, decoratorType, heightMapType, decoratorPredicate);
    }

    public static void registerCompostable(float chance, ItemLike item) {
        CompostingChanceRegistry.INSTANCE.add(item, chance);
    }

    public static void registerRecipes(String name, Supplier<RecipeType<?>> type, Supplier<RecipeSerializer<?>> serializer) {
        Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), type.get());
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(MOD_ID, name), serializer.get());
    }

    public static TagKey<Item> getShearsTag() {
        return ConventionalItemTags.SHEARS;
    }

    public static <T> Supplier<EntityDataSerializer<T>> registerEntityDataSerializers(String name, Supplier<EntityDataSerializer<T>> serializer) {
        EntityDataSerializers.registerSerializer(serializer.get());
        return serializer;
    }
}
