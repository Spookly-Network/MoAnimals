package net.spookly.moanimals.neoforge.datagen;

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

import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public class MoAnimalsRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public MoAnimalsRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, MoAnimalBlocks.JAR.get())
            .pattern(" W ")
            .pattern("G G")
            .pattern(" G ")
            .define('G', Items.GLASS)
            .define('W', ItemTags.PLANKS)
            .unlockedBy("has_glass", has(Items.GLASS))
            .save(recipeOutput);
    }
}
