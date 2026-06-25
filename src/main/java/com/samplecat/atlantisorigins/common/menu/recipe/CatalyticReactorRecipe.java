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

public class CatalyticReactorRecipe implements Recipe<RecipeInput> {

    public final List<Ingredient> inputs;
    public final ItemStack output;
    public final int processTime;

    public CatalyticReactorRecipe(List<Ingredient> inputs, ItemStack output, int processTime) {
        this.inputs = inputs;
        this.output = output;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(RecipeInput input, Level level) {
        if (input.size() < inputs.size()) return false;
        for (int i = 0; i < inputs.size(); i++) {
            if (!inputs.get(i).test(input.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CATALYTIC_REACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CATALYTIC_REACTOR_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CatalyticReactorRecipe> {

        public static final MapCodec<CatalyticReactorRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
                ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                Codec.INT.optionalFieldOf("process_time", 200).forGetter(recipe -> recipe.processTime)
        ).apply(instance, CatalyticReactorRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CatalyticReactorRecipe> STREAM_CODEC = StreamCodec.of(
                CatalyticReactorRecipe.Serializer::toNetwork,
                CatalyticReactorRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<CatalyticReactorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CatalyticReactorRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static CatalyticReactorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            List<Ingredient> inputs = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                inputs.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }
            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
            int processTime = buffer.readVarInt();
            return new CatalyticReactorRecipe(inputs, output, processTime);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, CatalyticReactorRecipe recipe) {
            buffer.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeVarInt(recipe.processTime);
        }
    }
}
