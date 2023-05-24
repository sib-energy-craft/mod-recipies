package com.github.sib_energy_craft.recipes.recipe;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.recipes.load.RecipeSerializers;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class IronCraftingTableRecipe implements Recipe<Inventory> {

    @Getter
    private final Identifier id;
    @Getter
    private final String group;
    @Getter
    private final Ingredient tool;
    @Getter
    private final Ingredient source;
    private final ItemStack output;
    private final Item icon;

    public IronCraftingTableRecipe(@NotNull Identifier id,
                                   @NotNull String group,
                                   @NotNull Ingredient tool,
                                   @NotNull Ingredient source,
                                   @NotNull ItemStack output) {
        this.id = id;
        this.group = group;
        this.tool = tool;
        this.source = source;
        this.output = output;

        var itemId = Identifiers.of("iron_crafting_table");
        this.icon = Registries.ITEM.getOrEmpty(itemId)
                .orElse(Items.CRAFTING_TABLE);
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        if(inventory.size() < 2) {
            return false;
        }
        return tool.test(inventory.getStack(0)) && source.test(inventory.getStack(1));
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
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
        return RecipeSerializers.IRON_CRAFTING_TABLE_RECIPE_RECIPE;
    }

    @Override
    public RecipeType<?> getType() {
        return IronCraftingTableRecipeType.INSTANCE;
    }

    @NotNull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(icon);
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
