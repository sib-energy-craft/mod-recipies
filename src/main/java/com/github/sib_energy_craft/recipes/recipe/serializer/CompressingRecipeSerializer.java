package com.github.sib_energy_craft.recipes.recipe.serializer;

import com.github.sib_energy_craft.recipes.recipe.CompressingRecipe;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public record CompressingRecipeSerializer(int cookingTime) implements RecipeSerializer<CompressingRecipe> {

    @NotNull
    @Override
    public CompressingRecipe read(@NotNull Identifier identifier,
                                  @NotNull JsonObject jsonObject) {
        var group = JsonHelper.getString(jsonObject, "group", "");
        var category = JsonHelper.getString(jsonObject, "category", null);
        var cookingRecipeCategory = CookingRecipeCategory.CODEC.byId(category, CookingRecipeCategory.MISC);
        var ingredient = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "ingredient"));
        var result = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
        var experience = JsonHelper.getFloat(jsonObject, "experience", 0.0f);
        var cookingTime = JsonHelper.getInt(jsonObject, "cookingTime", this.cookingTime);
        return new CompressingRecipe(identifier, group, cookingRecipeCategory, ingredient, result, experience, cookingTime);
    }

    @NotNull
    @Override
    public CompressingRecipe read(@NotNull Identifier identifier,
                                  @NotNull PacketByteBuf packetByteBuf) {
        var group = packetByteBuf.readString();
        var cookingRecipeCategory = packetByteBuf.readEnumConstant(CookingRecipeCategory.class);
        var ingredient = packetByteBuf.readItemStack();
        var result = packetByteBuf.readItemStack();
        var experience = packetByteBuf.readFloat();
        var i = packetByteBuf.readVarInt();
        return new CompressingRecipe(identifier, group, cookingRecipeCategory, ingredient, result, experience, i);
    }

    @Override
    public void write(@NotNull PacketByteBuf packetByteBuf,
                      @NotNull CompressingRecipe abstractCookingRecipe) {
        packetByteBuf.writeString(abstractCookingRecipe.getGroup());
        packetByteBuf.writeEnumConstant(abstractCookingRecipe.getCategory());
        packetByteBuf.writeItemStack(abstractCookingRecipe.getInput());
        packetByteBuf.writeItemStack(abstractCookingRecipe.getOutput());
        packetByteBuf.writeFloat(abstractCookingRecipe.getExperience());
        packetByteBuf.writeVarInt(abstractCookingRecipe.getCookTime());
    }
}
