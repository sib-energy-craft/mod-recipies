package com.github.sib_energy_craft.recipes.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.recipe.CookingRecipeSerializer;
import com.github.sib_energy_craft.recipes.recipe.*;
import com.github.sib_energy_craft.recipes.recipe.serializer.CompressingRecipeSerializer;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapedRecipeCharged;

import static net.minecraft.recipe.RecipeSerializer.register;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public final class RecipeSerializers implements DefaultModInitializer {
    public static final CookingRecipeSerializer<ExtractingRecipe> EXTRACTING;
    public static final CookingRecipeSerializer<MaceratingRecipe> MACERATING;
    public static final CompressingRecipeSerializer COMPRESSING;
    public static final RecipeSerializer<ShapedRecipe> SHAPED_CHARGED;
    public static final RecipeSerializer<IronCraftingTableRecipe> IRON_CRAFTING_TABLE_RECIPE_RECIPE;
    public static final RecipeSerializer<PurifyingRecipe> PURIFYING_RECIPE_RECIPE;

    static {
        var extractingRecipeSerializer = new CookingRecipeSerializer<>(
                ExtractingRecipe::new,
                ExtractingRecipeCodecFactory.build(100),
                100
        );
        EXTRACTING = register(Identifiers.asString("extracting"), extractingRecipeSerializer);

        var maceratingRecipeSerializer = new CookingRecipeSerializer<>(
                MaceratingRecipe::new,
                MaceratingRecipeCodecFactory.build(100),
                100
        );
        MACERATING = register(Identifiers.asString("macerating"), maceratingRecipeSerializer);

        var compressingRecipeSerializer = new CompressingRecipeSerializer(100);
        COMPRESSING = RecipeSerializer.register(Identifiers.asString("compressing"), compressingRecipeSerializer);

        var shapedChargedRecipeSerializer = new ShapedRecipeCharged.Serializer();
        SHAPED_CHARGED = register(Identifiers.asString("crafting_shaped_charged"), shapedChargedRecipeSerializer);

        var ironCraftingTableRecipeSerializer = new IronCraftingTableRecipe.Serializer();
        IRON_CRAFTING_TABLE_RECIPE_RECIPE = register(IronCraftingTableRecipeType.ID, ironCraftingTableRecipeSerializer);

        var purifyingRecipeSerializer = new PurifyingRecipe.Serializer(100);
        PURIFYING_RECIPE_RECIPE = register(PurifyingRecipeType.ID, purifyingRecipeSerializer);
    }
}
