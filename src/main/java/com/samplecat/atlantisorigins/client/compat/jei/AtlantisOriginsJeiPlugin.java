package com.samplecat.atlantisorigins.client.compat.jei;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.block.ModBlocks;
import com.samplecat.atlantisorigins.common.menu.recipe.CupriteWeaponStationRecipe;
import com.samplecat.atlantisorigins.common.menu.recipe.ModRecipes;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

@JeiPlugin
public class AtlantisOriginsJeiPlugin implements IModPlugin {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "jei_plugin");

    public static final RecipeType<CupriteWeaponStationRecipe> CUPRITE_WEAPON_STATION =
            RecipeType.create(AtlantisOrigins.MOD_ID, "cuprite_weapon_station", CupriteWeaponStationRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new CupriteWeaponStationRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Minecraft.getInstance().level == null) {
            return;
        }
        net.minecraft.world.item.crafting.RecipeType<CupriteWeaponStationRecipe> type = ModRecipes.CUPRITE_WEAPON_STATION_TYPE.get();
        registration.addRecipes(CUPRITE_WEAPON_STATION,
                Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(type).stream()
                        .map(RecipeHolder::value)
                        .toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CUPRITE_WEAPON_STATION_ITEM.get()), CUPRITE_WEAPON_STATION);
    }
}
