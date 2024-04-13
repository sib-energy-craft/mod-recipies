package com.github.sib_energy_craft.recipes.recipe;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.recipes.load.RecipeSerializers;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;

/**
 * @author sibmaks
 * @since 0.0.1
 */
public class ShapedRecipeCharged extends ShapedRecipe {

    public ShapedRecipeCharged(ShapedRecipe shapedRecipe) {
        super(shapedRecipe.getGroup(),
                shapedRecipe.getCategory(),
                shapedRecipe.getWidth(),
                shapedRecipe.getHeight(),
                shapedRecipe.getIngredients(),
                shapedRecipe.getResult(null));
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager) {
        var itemStack = getResult(dynamicRegistryManager).copy();
        var charge = Energy.ZERO;
        var size = recipeInputInventory.size();
        for (int slot = 0; slot < size; slot++) {
            var input = recipeInputInventory.getStack(slot);
            if (itemStack.getItem() instanceof ChargeableItem chargeableItem) {
                charge = charge.add(chargeableItem.getCharge(input));
            }
        }
        if (itemStack.getItem() instanceof ChargeableItem chargeableItem) {
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
        public ShapedRecipe read(PacketByteBuf packetByteBuf) {
            var shapedRecipe = super.read(packetByteBuf);
            if (!(shapedRecipe.getResult(null).getItem() instanceof ChargeableItem)) {
                throw new IllegalStateException("Result of %s should be Chargeable".formatted(shapedRecipe.getGroup()));
            }
            return new ShapedRecipeCharged(shapedRecipe);
        }
    }
}
