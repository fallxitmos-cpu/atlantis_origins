package com.samplecat.atlantisorigins.common.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class BeginnerDivingPantsItem extends ArmorItem {

    public BeginnerDivingPantsItem(Item.Properties properties) {
        super(
                BeginnerDivingArmorMaterial.BEGINNER_DIVING,
                Type.LEGGINGS,
                properties.durability(ArmorItem.Type.LEGGINGS.getDurability(BeginnerDivingArmorMaterial.DURABILITY_MULTIPLIER))
        );
    }
}
