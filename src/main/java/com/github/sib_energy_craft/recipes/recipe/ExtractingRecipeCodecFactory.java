package com.github.sib_energy_craft.recipes.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeCodecs;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.dynamic.Codecs;

/**
 * @author sibmaks
 * @since 0.0.11
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExtractingRecipeCodecFactory {

    public static Codec<ExtractingRecipe> build(int cookingTime) {
        return RecordCodecBuilder.create(
                instance -> instance.group(
                                Codecs.createStrictOptionalFieldCodec(Codec.STRING, "group", "")
                                        .forGetter(ExtractingRecipe::getGroup),
                                CookingRecipeCategory.CODEC.fieldOf("category")
                                        .orElse(CookingRecipeCategory.MISC)
                                        .forGetter(ExtractingRecipe::getCategory),
                                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient")
                                        .forGetter(ExtractingRecipe::getInput),
                                RecipeCodecs.CRAFTING_RESULT.fieldOf("result")
                                        .forGetter(ExtractingRecipe::getOutput),
                                Codec.FLOAT.fieldOf("experience")
                                        .orElse(0.0f)
                                        .forGetter(ExtractingRecipe::getExperience),
                                Codec.INT.fieldOf("cookingtime")
                                        .orElse(cookingTime)
                                        .forGetter(ExtractingRecipe::getCookingTime)
                        )
                        .apply(instance, ExtractingRecipe::new));
    }

}