package com.samplecat.atlantisorigins.common.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

public class BeginnerDivingSuitItem extends ArmorItem {

    public BeginnerDivingSuitItem(Item.Properties properties) {
        super(
                BeginnerDivingArmorMaterial.BEGINNER_DIVING,
                Type.CHESTPLATE,
                properties.durability(ArmorItem.Type.CHESTPLATE.getDurability(BeginnerDivingArmorMaterial.DURABILITY_MULTIPLIER))
        );
    }
}
