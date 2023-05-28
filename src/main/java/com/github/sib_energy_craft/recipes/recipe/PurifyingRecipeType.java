package com.github.sib_energy_craft.recipes.recipe;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.recipe.RecipeType;

/**
 * @author sibmaks
 * @since 0.0.8
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurifyingRecipeType implements RecipeType<PurifyingRecipe> {
    public static final String ID = Identifiers.asString("purifying");
    public static final PurifyingRecipeType INSTANCE = new PurifyingRecipeType();
}