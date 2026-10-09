package net.spookly.moanimals.fabric.datagen;

import java.util.concurrent.CompletableFuture;

import net.spookly.moanimals.block.MoAnimalBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class MoAnimalsRecipeProvider extends RecipeProvider {
    public MoAnimalsRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, completableFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, MoAnimalBlocks.JAR.get())
            .pattern(" W ")
            .pattern("G G")
            .pattern(" G ")
            .define('G', Items.GLASS)
            .define('W', ItemTags.PLANKS)
            .unlockedBy("has_glass", has(Items.GLASS)).save(recipeOutput);
    }
}
