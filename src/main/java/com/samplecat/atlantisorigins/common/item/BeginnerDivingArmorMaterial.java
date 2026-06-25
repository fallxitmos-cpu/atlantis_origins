package com.samplecat.atlantisorigins.common.item;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

/**
 * Beginner diving suit material.
 * <p>
 * Defense and durability match leather armor.
 * It provides a longer hypothermia onset time when the full set is worn.
 */
public class BeginnerDivingArmorMaterial {

    // Leather armor durability multiplier in vanilla.
    public static final int DURABILITY_MULTIPLIER = 5;

    public static final Holder<ArmorMaterial> BEGINNER_DIVING = Holder.direct(new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                // Same defense values as leather armor
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.BODY, 3);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "beginner_diving"))),
            0.0F,
            0.0F
    ));
}
