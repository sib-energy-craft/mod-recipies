package com.github.sib_energy_craft.recipes.recipe;

import com.github.sib_energy_craft.recipes.load.RecipeSerializers;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

/**
 * Recipe for using in ore purifying machine, to purify ore with additional income.
 *
 * @author sibmaks
 * @since 0.0.8
 */
@AllArgsConstructor
public class PurifyingRecipe implements Recipe<Inventory> {

    @Getter
    private final Identifier id;
    @Getter
    private final String group;
    @Getter
    private final Ingredient source;
    private final ItemStack outputMain;
    @Getter
    private final ItemStack outputSide;
    @Getter
    private final ItemStack outputTrash;
    @Getter
    private final int cookingTime;

    @Override
    public boolean matches(Inventory inventory, World world) {
        if (inventory.size() < 1) {
            return false;
        }
        return source.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return this.outputMain.copy();
    }

    public ItemStack craftSide() {
        return this.outputSide.copy();
    }

    public ItemStack craftTrash() {
        return this.outputTrash.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return outputMain;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.PURIFYING_RECIPE_RECIPE;
    }

    @Override
    public RecipeType<?> getType() {
        return PurifyingRecipeType.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<PurifyingRecipe> {
        @Override
        public PurifyingRecipe read(Identifier identifier, JsonObject jsonObject) {
            var group = JsonHelper.getString(jsonObject, "group", "");
            var sourceStack = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "source"));
            var outputMainStack = ShapedRecipe.outputFromJson(
                    JsonHelper.getObject(jsonObject, "outputMain")
            );
            var outputSideStack = ShapedRecipe.outputFromJson(
                    JsonHelper.getObject(jsonObject, "outputSide")
            );
            var outputTrashStack = ItemStack.EMPTY;
            if(JsonHelper.hasJsonObject(jsonObject, "outputTrash")) {
                outputTrashStack = ShapedRecipe.outputFromJson(
                        JsonHelper.getObject(jsonObject, "outputTrash")
                );
            }
            var cookingTime = JsonHelper.getInt(jsonObject, "cookingTime", 0);
            return new PurifyingRecipe(identifier, group, sourceStack, outputMainStack, outputSideStack, outputTrashStack, cookingTime);
        }

        @Override
        public PurifyingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            var group = packetByteBuf.readString();
            var source = Ingredient.fromPacket(packetByteBuf);
            var outputMain = packetByteBuf.readItemStack();
            var outputSide = packetByteBuf.readItemStack();
            var outputTrash = packetByteBuf.readItemStack();
            var cookingTime = packetByteBuf.readInt();
            return new PurifyingRecipe(identifier, group, source, outputMain, outputSide, outputTrash, cookingTime);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, PurifyingRecipe recipe) {
            packetByteBuf.writeString(recipe.group);
            recipe.source.write(packetByteBuf);
            packetByteBuf.writeItemStack(recipe.outputMain);
            packetByteBuf.writeItemStack(recipe.outputSide);
            packetByteBuf.writeItemStack(recipe.outputTrash);
            packetByteBuf.writeInt(recipe.cookingTime);
        }
    }
}
