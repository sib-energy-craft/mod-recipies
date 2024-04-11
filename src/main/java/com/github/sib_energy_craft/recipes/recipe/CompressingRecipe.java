package com.github.sib_energy_craft.recipes.recipe;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.recipes.load.RecipeSerializers;
import com.github.sib_energy_craft.recipes.load.RecipeTypes;
import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public class CompressingRecipe implements Recipe<Inventory> {

    protected final RecipeType<?> type;
    @Getter
    private final CookingRecipeCategory category;
    @Getter
    protected final String group;
    @Getter
    protected final ItemStack input;
    @Getter
    protected final ItemStack output;
    @Getter
    protected final float experience;
    @Getter
    protected final int cookTime;
    private final Item icon;

    public CompressingRecipe(@NotNull String group,
                             @NotNull CookingRecipeCategory category,
                             @NotNull ItemStack input,
                             @NotNull ItemStack output,
                             float experience,
                             int cookTime) {
        this.type = RecipeTypes.COMPRESSING;
        this.group = group;
        this.category = category;
        this.input = input;
        this.output = output;
        this.experience = experience;
        this.cookTime = cookTime;

        var itemId = Identifiers.of("compressor");
        this.icon = Registries.ITEM.getOrEmpty(itemId)
                .orElse(Items.CRAFTING_TABLE);
    }

    @Override
    public boolean matches(@NotNull Inventory inventory,
                           @NotNull World world) {
        ItemStack sourceSlot = inventory.getStack(0);
        return this.input.isOf(sourceSlot.getItem()) && sourceSlot.getCount() >= this.input.getCount();
    }

    @Override
    public ItemStack craft(@NotNull Inventory inventory,
                           @NotNull DynamicRegistryManager registryManager) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(@NotNull DynamicRegistryManager registryManager) {
        return output;
    }

    @NotNull
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        var defaultedList = DefaultedList.<Ingredient>of();
        defaultedList.add(Ingredient.ofStacks(this.input));
        return defaultedList;
    }

    @NotNull
    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @NotNull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(icon);
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.COMPRESSING;
    }
}