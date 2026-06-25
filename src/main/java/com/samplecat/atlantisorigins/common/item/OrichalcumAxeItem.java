package com.samplecat.atlantisorigins.common.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

public class OrichalcumAxeItem extends AxeItem {

    public OrichalcumAxeItem(Item.Properties properties) {
        super(OrichalcumTier.INSTANCE, properties.attributes(AxeItem.createAttributes(OrichalcumTier.INSTANCE, 5.0F, -3.0F)));
    }
}
