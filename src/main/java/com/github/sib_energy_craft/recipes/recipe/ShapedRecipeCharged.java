package com.github.sib_energy_craft.recipes.recipe;

import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.recipes.load.RecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class ShapedRecipeCharged extends ShapedRecipe {

    public ShapedRecipeCharged(Identifier identifier, ShapedRecipe shapedRecipe) {
        super(identifier, shapedRecipe.getGroup(), shapedRecipe.getCategory(), shapedRecipe.getWidth(),
                shapedRecipe.getHeight(), shapedRecipe.getIngredients(), shapedRecipe.getOutput(null));
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory, DynamicRegistryManager dynamicRegistryManager) {
        var itemStack = getOutput(dynamicRegistryManager).copy();
        var charge = 0;
        var size = craftingInventory.size();
        for(int slot = 0; slot < size; slot++) {
            var input = craftingInventory.getStack(slot);
            if(itemStack.getItem() instanceof ChargeableItem chargeableItem) {
                charge += chargeableItem.getCharge(input);
            }
        }
        if(itemStack.getItem() instanceof ChargeableItem chargeableItem) {
            chargeableItem.setCharge(itemStack, charge);
        }
        return itemStack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.SHAPED_CHARGED;
    }

    public static class Serializer extends ShapedRecipe.Serializer {
        @Override
        public ShapedRecipe read(Identifier identifier, JsonObject jsonObject) {
            var shapedRecipe = super.read(identifier, jsonObject);
            if(!(shapedRecipe.getOutput(null).getItem() instanceof ChargeableItem)) {
                throw new IllegalStateException("Result of %s should be Chargeable".formatted(identifier));
            }
            return new ShapedRecipeCharged(identifier, shapedRecipe);
        }

        @Override
        public ShapedRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            var shapedRecipe = super.read(identifier, packetByteBuf);
            if(!(shapedRecipe.getOutput(null).getItem() instanceof ChargeableItem)) {
                throw new IllegalStateException("Result of %s should be Chargeable".formatted(identifier));
            }
            return new ShapedRecipeCharged(identifier, shapedRecipe);
        }
    }
}
