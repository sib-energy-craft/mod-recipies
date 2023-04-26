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
 * @author sibmaks
 * Created at 29-05-2022
 */
public class MaceratingRecipe extends OpenAbstractCookingRecipe {
    private final Item icon;

    public MaceratingRecipe(Identifier id,
                            String group,
                            CookingRecipeCategory category,
                            Ingredient input,
                            ItemStack output,
                            float experience,
                            int cookTime) {
        super(RecipeTypes.MACERATING, id, group, category, input, output, experience, cookTime);
        var itemId = Identifiers.of("macerator");
        this.icon = Registries.ITEM.getOrEmpty(itemId)
                .orElse(Items.CRAFTING_TABLE);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(icon);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.MACERATING;
    }
}