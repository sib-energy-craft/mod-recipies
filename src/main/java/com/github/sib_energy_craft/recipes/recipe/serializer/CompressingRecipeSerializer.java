package com.github.sib_energy_craft.recipes.recipe.serializer;

import com.github.sib_energy_craft.recipes.recipe.CompressingRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.2
 */
public final class CompressingRecipeSerializer implements RecipeSerializer<CompressingRecipe> {

    private final Codec<CompressingRecipe> codec;

    public CompressingRecipeSerializer(int cookingTime) {
        this.codec = RecordCodecBuilder.create(
                instance -> instance.group(
                                Codecs.createStrictOptionalFieldCodec(Codec.STRING, "group", "")
                                        .forGetter(CompressingRecipe::getGroup),
                                CookingRecipeCategory.CODEC.fieldOf("category")
                                        .orElse(CookingRecipeCategory.MISC)
                                        .forGetter(CompressingRecipe::getCategory),
                                Registries.ITEM.getCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("ingredient")
                                        .forGetter(CompressingRecipe::getInput),
                                Registries.ITEM.getCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("result")
                                        .forGetter(CompressingRecipe::getOutput),
                                Codec.FLOAT.fieldOf("experience")
                                        .orElse(0.0f)
                                        .forGetter(CompressingRecipe::getExperience),
                                Codec.INT.fieldOf("cookingtime")
                                        .orElse(cookingTime)
                                        .forGetter(CompressingRecipe::getCookTime)
                        )
                        .apply(instance, CompressingRecipe::new));
    }

    @NotNull
    @Override
    public CompressingRecipe read(@NotNull PacketByteBuf packetByteBuf) {
        var group = packetByteBuf.readString();
        var cookingRecipeCategory = packetByteBuf.readEnumConstant(CookingRecipeCategory.class);
        var ingredient = packetByteBuf.readItemStack();
        var result = packetByteBuf.readItemStack();
        var experience = packetByteBuf.readFloat();
        var i = packetByteBuf.readVarInt();
        return new CompressingRecipe(group, cookingRecipeCategory, ingredient, result, experience, i);
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

    @Override
    public Codec<CompressingRecipe> codec() {
        return codec;
    }

}
