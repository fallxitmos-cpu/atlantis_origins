package com.samplecat.atlantisorigins.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

public class OrichalcumShovelItem extends ShovelItem {

    public OrichalcumShovelItem(Item.Properties properties) {
        super(OrichalcumTier.INSTANCE, properties.attributes(ShovelItem.createAttributes(OrichalcumTier.INSTANCE, 1.5F, -3.0F)));
    }
}
