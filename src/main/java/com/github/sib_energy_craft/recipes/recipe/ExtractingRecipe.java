package com.github.sib_energy_craft.recipes.recipe;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.recipe.OpenAbstractCookingRecipe;
import com.github.sib_energy_craft.recipes.load.RecipeSerializers;
import com.github.sib_energy_craft.recipes.load.RecipeTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class ExtractingRecipe extends OpenAbstractCookingRecipe {
    private final Item icon;

    public ExtractingRecipe(Identifier id,
                            String group,
                            CookingRecipeCategory category,
                            Ingredient input,
                            ItemStack output,
                            float experience,
                            int cookTime) {
        super(RecipeTypes.EXTRACTING, id, group, category, input, output, experience, cookTime);
        var itemId = Identifiers.of("extractor");
        this.icon = Registries.ITEM.getOrEmpty(itemId)
                .orElse(Items.CRAFTING_TABLE);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(icon);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.EXTRACTING;
    }
}