package com.github.sib_energy_craft.recipes.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.dynamic.Codecs;

/**
 * @author sibmaks
 * @since 0.0.11
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MaceratingRecipeCodecFactory {

    public static Codec<MaceratingRecipe> build(int cookingTime) {
        return RecordCodecBuilder.create(
                instance -> instance.group(
                                Codecs.createStrictOptionalFieldCodec(Codec.STRING, "group", "")
                                        .forGetter(MaceratingRecipe::getGroup),
                                CookingRecipeCategory.CODEC.fieldOf("category")
                                        .orElse(CookingRecipeCategory.MISC)
                                        .forGetter(MaceratingRecipe::getCategory),
                                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient")
                                        .forGetter(MaceratingRecipe::getInput),
                                ItemStack.RECIPE_RESULT_CODEC.fieldOf("result")
                                        .forGetter(MaceratingRecipe::getOutput),
                                Codec.FLOAT.fieldOf("experience")
                                        .orElse(0.0f)
                                        .forGetter(MaceratingRecipe::getExperience),
                                Codec.INT.fieldOf("cookingtime")
                                        .orElse(cookingTime)
                                        .forGetter(MaceratingRecipe::getCookingTime)
                        )
                        .apply(instance, MaceratingRecipe::new));
    }

}