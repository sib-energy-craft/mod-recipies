package com.github.sib_energy_craft.recipes.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.recipes.recipe.CompressingRecipe;
import com.github.sib_energy_craft.recipes.recipe.ExtractingRecipe;
import com.github.sib_energy_craft.recipes.recipe.MaceratingRecipe;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.recipe.RecipeType;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RecipeTypes {
    public static final RecipeType<ExtractingRecipe> EXTRACTING;
    public static final RecipeType<MaceratingRecipe> MACERATING;
    public static final RecipeType<CompressingRecipe> COMPRESSING;

    static {
        EXTRACTING = RecipeType.register(Identifiers.asString("extracting"));
        MACERATING = RecipeType.register(Identifiers.asString("macerating"));
        COMPRESSING = RecipeType.register(Identifiers.asString("compressing"));
    }
}
