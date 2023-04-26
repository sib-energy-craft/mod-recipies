package com.github.sib_energy_craft.recipes.recipe;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.recipe.RecipeType;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IronCraftingTableRecipeType implements RecipeType<IronCraftingTableRecipe> {
    public static final String ID = Identifiers.asString("iron_crafting_table_tool");
    public static final IronCraftingTableRecipeType INSTANCE = new IronCraftingTableRecipeType();
}