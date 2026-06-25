package com.samplecat.atlantisorigins.common.item;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

/**
 * Orichalcum armor material.
 * <p>
 * Defense and toughness match Netherite. Durability multiplier is multiplied by 3
 * to match the effective durability of a Netherite armor set enchanted with Unbreaking II.
 */
public class OrichalcumArmorMaterial {

    // Netherite armor durability multiplier in vanilla is 37.
    // Unbreaking II effectively triples durability, so 37 * 3 = 111.
    public static final int DURABILITY_MULTIPLIER = 111;

    public static final Holder<ArmorMaterial> ORICHALCUM = Holder.direct(new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                // Same base defense values as netherite armor
                map.put(ArmorItem.Type.BOOTS, 4);
                map.put(ArmorItem.Type.LEGGINGS, 7);
                map.put(ArmorItem.Type.CHESTPLATE, 9);
                map.put(ArmorItem.Type.HELMET, 4);
                map.put(ArmorItem.Type.BODY, 9);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(ModItems.ORICHALCUM_INGOT.get()),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "orichalcum"))),
            4.0F,
            0.1F
    ));
}
