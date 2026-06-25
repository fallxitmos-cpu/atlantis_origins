package com.samplecat.atlantisorigins.common.menu.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public class AlchemicalReactorRecipe implements Recipe<RecipeInput> {

    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    public AlchemicalReactorRecipe(List<Ingredient> ingredients, ItemStack result) {
        this.ingredients = NonNullList.create();
        this.ingredients.addAll(ingredients);
        this.result = result;
    }

    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean matches(RecipeInput input, Level level) {
        if (input.size() < 4) return false;
        for (int i = 0; i < 4; i++) {
            if (i >= ingredients.size()) return false;
            if (!ingredients.get(i).test(input.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALCHEMICAL_REACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ALCHEMICAL_REACTOR_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<AlchemicalReactorRecipe> {

        private static final MapCodec<AlchemicalReactorRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(r -> r.ingredients),
                ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result)
        ).apply(instance, AlchemicalReactorRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, AlchemicalReactorRecipe> STREAM_CODEC = StreamCodec.of(
                AlchemicalReactorRecipe.Serializer::toNetwork,
                AlchemicalReactorRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<AlchemicalReactorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlchemicalReactorRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static AlchemicalReactorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            return new AlchemicalReactorRecipe(ingredients, result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, AlchemicalReactorRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        }
    }
}
