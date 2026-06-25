package com.samplecat.atlantisorigins.common.menu.recipe;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, AtlantisOrigins.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, AtlantisOrigins.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AlchemicalReactorRecipe>> ALCHEMICAL_REACTOR_TYPE = RECIPE_TYPES.register(
            "alchemical_reactor",
            () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "alchemical_reactor")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlchemicalReactorRecipe>> ALCHEMICAL_REACTOR_SERIALIZER = RECIPE_SERIALIZERS.register(
            "alchemical_reactor",
            AlchemicalReactorRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<CupriteWeaponStationRecipe>> CUPRITE_WEAPON_STATION_TYPE = RECIPE_TYPES.register(
            "cuprite_weapon_station",
            () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "cuprite_weapon_station")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CupriteWeaponStationRecipe>> CUPRITE_WEAPON_STATION_SERIALIZER = RECIPE_SERIALIZERS.register(
            "cuprite_weapon_station",
            CupriteWeaponStationRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<LiquidInjectionChamberRecipe>> LIQUID_INJECTION_CHAMBER_TYPE = RECIPE_TYPES.register(
            "liquid_injection_chamber",
            () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "liquid_injection_chamber")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<LiquidInjectionChamberRecipe>> LIQUID_INJECTION_CHAMBER_SERIALIZER = RECIPE_SERIALIZERS.register(
            "liquid_injection_chamber",
            LiquidInjectionChamberRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<SeparationTowerRecipe>> SEPARATION_TOWER_TYPE = RECIPE_TYPES.register(
            "separation_tower",
            () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "separation_tower")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SeparationTowerRecipe>> SEPARATION_TOWER_SERIALIZER = RECIPE_SERIALIZERS.register(
            "separation_tower",
            SeparationTowerRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<CatalyticReactorRecipe>> CATALYTIC_REACTOR_TYPE = RECIPE_TYPES.register(
            "catalytic_reactor",
            () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "catalytic_reactor")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CatalyticReactorRecipe>> CATALYTIC_REACTOR_SERIALIZER = RECIPE_SERIALIZERS.register(
            "catalytic_reactor",
            CatalyticReactorRecipe.Serializer::new);
}
