package com.samplecat.atlantisorigins.common.damage;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

/**
 * Damage types for Atlantis Origins.
 * <p>
 * These are loaded from the data pack registry via the JSON files under
 * {@code data/atlantis_origins/damage_type/}. The {@link #bootstrap(BootstrapContext)}
 * method is provided for data generation.
 */
public class ModDamageTypes {

    public static final ResourceKey<DamageType> TRUE_DAMAGE = createKey("true_damage");
    public static final ResourceKey<DamageType> DCS_DAMAGE = createKey("dcs_damage");
    public static final ResourceKey<DamageType> EVOLUTION = createKey("evolution");

    private static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(TRUE_DAMAGE, new DamageType(
                AtlantisOrigins.MOD_ID + ".true_damage",
                0.1F));

        context.register(DCS_DAMAGE, new DamageType(
                AtlantisOrigins.MOD_ID + ".dcs_damage",
                0.1F));

        context.register(EVOLUTION, new DamageType(
                AtlantisOrigins.MOD_ID + ".evolution",
                0.1F));
    }
}
