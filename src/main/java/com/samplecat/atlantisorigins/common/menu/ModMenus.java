package com.samplecat.atlantisorigins.common.menu;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, AtlantisOrigins.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<CupriteWeaponStationMenu>> CUPRITE_WEAPON_STATION = MENUS.register(
            "cuprite_weapon_station",
            () -> IMenuTypeExtension.create(CupriteWeaponStationMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<AlchemicalReactorMenu>> ALCHEMICAL_REACTOR = MENUS.register(
            "alchemical_reactor",
            () -> IMenuTypeExtension.create(AlchemicalReactorMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<LiquidInjectionChamberMenu>> LIQUID_INJECTION_CHAMBER = MENUS.register(
            "liquid_injection_chamber",
            () -> IMenuTypeExtension.create(LiquidInjectionChamberMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<SeparationTowerMenu>> SEPARATION_TOWER = MENUS.register(
            "separation_tower",
            () -> IMenuTypeExtension.create(SeparationTowerMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<CatalyticReactorMenu>> CATALYTIC_REACTOR = MENUS.register(
            "catalytic_reactor",
            () -> IMenuTypeExtension.create(CatalyticReactorMenu::new));

}
