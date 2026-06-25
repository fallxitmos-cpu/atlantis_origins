package com.samplecat.atlantisorigins.client.compat.jei;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.block.ModBlocks;
import com.samplecat.atlantisorigins.common.menu.CupriteWeaponStationMenu;
import com.samplecat.atlantisorigins.common.menu.recipe.CupriteWeaponStationRecipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.Ingredient;

public class CupriteWeaponStationRecipeCategory implements IRecipeCategory<CupriteWeaponStationRecipe> {

    private static final int SLOT_SIZE = 18;
    private static final int GRID_X = 12;
    private static final int GRID_Y = 12;
    private static final int OUTPUT_X = GRID_X + CupriteWeaponStationMenu.GRID_WIDTH * SLOT_SIZE + 18;
    private static final int OUTPUT_Y = GRID_Y + (CupriteWeaponStationMenu.GRID_HEIGHT * SLOT_SIZE) / 2 - SLOT_SIZE / 2;

    private final IDrawable background;
    private final IDrawable icon;

    public CupriteWeaponStationRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(
                OUTPUT_X + SLOT_SIZE + 12,
                Math.max(OUTPUT_Y + SLOT_SIZE + 12, GRID_Y + CupriteWeaponStationMenu.GRID_HEIGHT * SLOT_SIZE + 12));
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CUPRITE_WEAPON_STATION_ITEM.get()));
    }

    @Override
    public RecipeType<CupriteWeaponStationRecipe> getRecipeType() {
        return AtlantisOriginsJeiPlugin.CUPRITE_WEAPON_STATION;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.atlantis_origins.cuprite_weapon_station");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CupriteWeaponStationRecipe recipe, IFocusGroup focuses) {
        int width = recipe.getWidth();
        int height = recipe.getHeight();
        int offsetX = (CupriteWeaponStationMenu.GRID_WIDTH - width) / 2;
        int offsetY = (CupriteWeaponStationMenu.GRID_HEIGHT - height) / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Ingredient ingredient = recipe.getIngredients().get(x + y * width);
                if (!ingredient.isEmpty()) {
                    builder.addSlot(RecipeIngredientRole.INPUT,
                                    GRID_X + (offsetX + x) * SLOT_SIZE + 1,
                                    GRID_Y + (offsetY + y) * SLOT_SIZE + 1)
                            .addIngredients(ingredient);
                }
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_X + 1, OUTPUT_Y + 1)
                .addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }
}
