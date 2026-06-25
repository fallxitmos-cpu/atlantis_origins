package com.samplecat.atlantisorigins.common.menu.recipe;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SeparationTowerRecipe implements Recipe<RecipeInput> {

    public final Ingredient input;
    public final List<ItemStack> outputs;
    public final int processTime;

    public SeparationTowerRecipe(Ingredient input, List<ItemStack> outputs, int processTime) {
        this.input = input;
        this.outputs = outputs;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(RecipeInput container, Level level) {
        return input.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        if (!outputs.isEmpty()) {
            return outputs.get(0).copy();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return outputs.isEmpty() ? ItemStack.EMPTY : outputs.get(0);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SEPARATION_TOWER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SEPARATION_TOWER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<SeparationTowerRecipe> {

        public static final MapCodec<SeparationTowerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                ItemStack.CODEC.listOf().fieldOf("results").forGetter(recipe -> recipe.outputs),
                Codec.INT.optionalFieldOf("process_time", 200).forGetter(recipe -> recipe.processTime)
        ).apply(instance, SeparationTowerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SeparationTowerRecipe> STREAM_CODEC = StreamCodec.of(
                SeparationTowerRecipe.Serializer::toNetwork,
                SeparationTowerRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<SeparationTowerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SeparationTowerRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SeparationTowerRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int size = buffer.readVarInt();
            List<ItemStack> outputs = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                outputs.add(ItemStack.STREAM_CODEC.decode(buffer));
            }
            int processTime = buffer.readVarInt();
            return new SeparationTowerRecipe(input, outputs, processTime);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SeparationTowerRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            buffer.writeVarInt(recipe.outputs.size());
            for (ItemStack output : recipe.outputs) {
                ItemStack.STREAM_CODEC.encode(buffer, output);
            }
            buffer.writeVarInt(recipe.processTime);
        }
    }
}
