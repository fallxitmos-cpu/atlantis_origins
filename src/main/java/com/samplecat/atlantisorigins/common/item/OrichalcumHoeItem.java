package com.samplecat.atlantisorigins.common.item;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;

public class OrichalcumHoeItem extends HoeItem {

    public OrichalcumHoeItem(Item.Properties properties) {
        super(OrichalcumTier.INSTANCE, properties.attributes(HoeItem.createAttributes(OrichalcumTier.INSTANCE, -4.0F, 0.0F)));
    }
}
