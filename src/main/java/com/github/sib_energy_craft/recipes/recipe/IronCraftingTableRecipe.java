package com.github.sib_energy_craft.recipes.recipe;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@AllArgsConstructor
public class IronCraftingTableRecipe implements Recipe<CraftingInventory> {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Type implements RecipeType<IronCraftingTableRecipe> {
        public static final String ID = Identifiers.asString("iron_crafting_table_tool");
        public static final Type INSTANCE = new Type();
    }

    @Getter
    private final Identifier id;
    @Getter
    private final String group;
    @Getter
    private final Ingredient tool;
    @Getter
    private final Ingredient source;
    private final ItemStack output;

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        if(inventory.size() < 2) {
            return false;
        }
        return tool.test(inventory.getStack(0)) && source.test(inventory.getStack(1));
    }

    @Override
    public ItemStack craft(CraftingInventory inventory, DynamicRegistryManager registryManager) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<IronCraftingTableRecipe> {
        @Override
        public IronCraftingTableRecipe read(Identifier identifier, JsonObject jsonObject) {
            var group = JsonHelper.getString(jsonObject, "group", "");
            var toolStack = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "tool"));
            var sourceStack = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "source"));
            var outputStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "output"));
            return new IronCraftingTableRecipe(identifier, group, toolStack, sourceStack, outputStack);
        }

        @Override
        public IronCraftingTableRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            var group = packetByteBuf.readString();
            var tool = Ingredient.fromPacket(packetByteBuf);
            var source = Ingredient.fromPacket(packetByteBuf);
            var output = packetByteBuf.readItemStack();
            return new IronCraftingTableRecipe(identifier, group, tool, source, output);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, IronCraftingTableRecipe recipe) {
            packetByteBuf.writeString(recipe.group);
            recipe.tool.write(packetByteBuf);
            recipe.source.write(packetByteBuf);
            packetByteBuf.writeItemStack(recipe.output);
        }
    }
}
