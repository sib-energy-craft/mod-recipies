package com.github.sib_energy_craft.recipes.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.recipe.CookingRecipeSerializer;
import com.github.sib_energy_craft.recipes.recipe.ExtractingRecipe;
import com.github.sib_energy_craft.recipes.recipe.IronCraftingTableRecipe;
import com.github.sib_energy_craft.recipes.recipe.MaceratingRecipe;
import com.github.sib_energy_craft.recipes.recipe.ShapedRecipeCharged;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;

import static net.minecraft.recipe.RecipeSerializer.register;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RecipeSerializers {
    public static final CookingRecipeSerializer<ExtractingRecipe> EXTRACTING;
    public static final CookingRecipeSerializer<MaceratingRecipe> MACERATING;
    public static final RecipeSerializer<ShapedRecipe> SHAPED_CHARGED;
    public static final RecipeSerializer<IronCraftingTableRecipe> IRON_CRAFTING_TABLE_RECIPE_RECIPE;

    static {
        var extractingRecipeSerializer = new CookingRecipeSerializer<>(ExtractingRecipe::new, 100);
        EXTRACTING = register(Identifiers.asString("extracting"), extractingRecipeSerializer);

        var maceratingRecipeSerializer = new CookingRecipeSerializer<>(MaceratingRecipe::new, 100);
        MACERATING = register(Identifiers.asString("macerating"), maceratingRecipeSerializer);

        var shapedChargedRecipeSerializer = new ShapedRecipeCharged.Serializer();
        SHAPED_CHARGED = register(Identifiers.asString("crafting_shaped_charged"), shapedChargedRecipeSerializer);

        var ironCraftingTableRecipeSerializer = new IronCraftingTableRecipe.Serializer();
        IRON_CRAFTING_TABLE_RECIPE_RECIPE = register(IronCraftingTableRecipe.Type.ID, ironCraftingTableRecipeSerializer);
    }
}
