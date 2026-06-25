package com.samplecat.atlantisorigins.common.menu.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public class CupriteWeaponStationRecipe implements Recipe<CraftingInput> {

    private final String group;
    private final CraftingBookCategory category;
    private final int width;
    private final int height;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final boolean showNotification;

    public CupriteWeaponStationRecipe(String group, CraftingBookCategory category, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result, boolean showNotification) {
        this.group = group;
        this.category = category;
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        this.result = result;
        this.showNotification = showNotification;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.width() < width || input.height() < height) {
            return false;
        }
        for (int yOffset = 0; yOffset <= input.height() - height; yOffset++) {
            for (int xOffset = 0; xOffset <= input.width() - width; xOffset++) {
                if (matches(input, xOffset, yOffset, true)) {
                    return true;
                }
                if (matches(input, xOffset, yOffset, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matches(CraftingInput input, int xOffset, int yOffset, boolean mirror) {
        for (int y = 0; y < input.height(); y++) {
            for (int x = 0; x < input.width(); x++) {
                int patternX = x - xOffset;
                int patternY = y - yOffset;
                Ingredient ingredient;
                if (patternX >= 0 && patternY >= 0 && patternX < width && patternY < height) {
                    if (mirror) {
                        ingredient = ingredients.get(width - 1 - patternX + patternY * width);
                    } else {
                        ingredient = ingredients.get(patternX + patternY * width);
                    }
                } else {
                    ingredient = Ingredient.EMPTY;
                }
                ItemStack stack = input.getItem(x, y);
                if (!ingredient.test(stack)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CUPRITE_WEAPON_STATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CUPRITE_WEAPON_STATION_TYPE.get();
    }

    @Override
    public boolean showNotification() {
        return showNotification;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public static class Serializer implements RecipeSerializer<CupriteWeaponStationRecipe> {

        private static final Codec<String> PATTERN_ROW_CODEC = Codec.string(1, 5);

        private static final Codec<List<String>> PATTERN_CODEC = PATTERN_ROW_CODEC.listOf().flatXmap(
                list -> validatePattern(list),
                Serializer::validatePattern
        );

        private static DataResult<List<String>> validatePattern(List<String> list) {
            if (list.isEmpty()) {
                return DataResult.error(() -> "Invalid pattern: empty pattern not allowed");
            }
            if (list.size() > 5) {
                return DataResult.error(() -> "Invalid pattern: too many rows");
            }
            int width = list.get(0).length();
            if (width > 5) {
                return DataResult.error(() -> "Invalid pattern: row too wide");
            }
            for (String row : list) {
                if (row.length() != width) {
                    return DataResult.error(() -> "Invalid pattern: rows have different widths");
                }
            }
            return DataResult.success(list);
        }

        private static final MapCodec<CupriteWeaponStationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group),
                CraftingBookCategory.CODEC.optionalFieldOf("category", CraftingBookCategory.MISC).forGetter(r -> r.category),
                PATTERN_CODEC.fieldOf("pattern").forGetter(r -> shrink(r.ingredients, r.width, r.height)),
                Codec.unboundedMap(Codec.STRING, Ingredient.CODEC).fieldOf("key").forGetter(r -> Map.of()),
                ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result),
                Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(r -> r.showNotification)
        ).apply(instance, Serializer::createFromCodec));

        private static CupriteWeaponStationRecipe createFromCodec(String group, CraftingBookCategory category, List<String> pattern, Map<String, Ingredient> key, ItemStack result, boolean showNotification) {
            int height = pattern.size();
            int width = pattern.get(0).length();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
            for (int y = 0; y < height; y++) {
                String row = pattern.get(y);
                for (int x = 0; x < width; x++) {
                    char c = row.charAt(x);
                    if (c == ' ') {
                        ingredients.set(x + y * width, Ingredient.EMPTY);
                    } else {
                        String keyStr = String.valueOf(c);
                        if (!key.containsKey(keyStr)) {
                            throw new IllegalStateException("Pattern references undefined key '" + c + "'");
                        }
                        ingredients.set(x + y * width, key.get(keyStr));
                    }
                }
            }
            return new CupriteWeaponStationRecipe(group, category, width, height, ingredients, result, showNotification);
        }

        private static List<String> shrink(NonNullList<Ingredient> ingredients, int width, int height) {
            StringBuilder[] rows = new StringBuilder[height];
            for (int y = 0; y < height; y++) {
                rows[y] = new StringBuilder();
                for (int x = 0; x < width; x++) {
                    Ingredient ingredient = ingredients.get(x + y * width);
                    rows[y].append(ingredient.isEmpty() ? ' ' : '#');
                }
            }
            return List.of(rows).stream().map(StringBuilder::toString).toList();
        }

        private static final StreamCodec<RegistryFriendlyByteBuf, CupriteWeaponStationRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork,
                Serializer::fromNetwork
        );

        @Override
        public MapCodec<CupriteWeaponStationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CupriteWeaponStationRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static CupriteWeaponStationRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            CraftingBookCategory category = buffer.readEnum(CraftingBookCategory.class);
            int width = buffer.readVarInt();
            int height = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            boolean showNotification = buffer.readBoolean();
            return new CupriteWeaponStationRecipe(group, category, width, height, ingredients, result, showNotification);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, CupriteWeaponStationRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            buffer.writeVarInt(recipe.width);
            buffer.writeVarInt(recipe.height);
            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeBoolean(recipe.showNotification);
        }
    }
}
