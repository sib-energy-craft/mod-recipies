package com.github.sib_energy_craft.recipes.recipe;

import com.github.sib_energy_craft.recipes.load.RecipeSerializers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * Recipe for using in ore purifying machine, to purify ore with additional income.
 *
 * @author sibmaks
 * @since 0.0.8
 */
@Getter
@AllArgsConstructor
public class PurifyingRecipe implements Recipe<Inventory> {

    private final String group;
    private final Ingredient source;
    private final ItemStack outputMain;
    private final ItemStack outputSide;
    private final Optional<ItemStack> outputTrash;
    private final int cookingTime;

    @Override
    public boolean matches(Inventory inventory, World world) {
        if (inventory.isEmpty()) {
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
        return this.outputTrash
                .orElse(ItemStack.EMPTY)
                .copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
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
        private final Codec<PurifyingRecipe> codec;

        public Serializer(int defaultCookingTime) {
            this.codec = RecordCodecBuilder.create(
                    instance -> instance.group(
                                    Codecs.createStrictOptionalFieldCodec(Codec.STRING, "group", "")
                                            .forGetter(PurifyingRecipe::getGroup),
                                    Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("source")
                                            .forGetter(PurifyingRecipe::getSource),
                                    RecipeCodecs.CRAFTING_RESULT.fieldOf("outputMain")
                                            .forGetter(PurifyingRecipe::getOutputMain),
                                    RecipeCodecs.CRAFTING_RESULT.fieldOf("outputSide")
                                            .forGetter(PurifyingRecipe::getOutputSide),
                                    RecipeCodecs.CRAFTING_RESULT.optionalFieldOf("outputTrash")
                                            .forGetter(PurifyingRecipe::getOutputTrash),
                                    Codec.INT.fieldOf("cookingtime")
                                            .orElse(defaultCookingTime)
                                            .forGetter(PurifyingRecipe::getCookingTime)
                            )
                            .apply(instance, PurifyingRecipe::new));
        }


        @Override
        public PurifyingRecipe read(PacketByteBuf packetByteBuf) {
            var group = packetByteBuf.readString();
            var source = Ingredient.fromPacket(packetByteBuf);
            var outputMain = packetByteBuf.readItemStack();
            var outputSide = packetByteBuf.readItemStack();
            var outputTrash = packetByteBuf.readOptional(PacketByteBuf::readItemStack);
            var cookingTime = packetByteBuf.readInt();
            return new PurifyingRecipe(group, source, outputMain, outputSide, outputTrash, cookingTime);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, PurifyingRecipe recipe) {
            packetByteBuf.writeString(recipe.group);
            recipe.source.write(packetByteBuf);
            packetByteBuf.writeItemStack(recipe.outputMain);
            packetByteBuf.writeItemStack(recipe.outputSide);
            packetByteBuf.writeOptional(recipe.outputTrash, PacketByteBuf::writeItemStack);
            packetByteBuf.writeInt(recipe.cookingTime);
        }

        @Override
        public Codec<PurifyingRecipe> codec() {
            return codec;
        }
    }
}
