package com.samplecat.atlantisorigins.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class OrichalcumPickaxeItem extends PickaxeItem {

    public OrichalcumPickaxeItem(Item.Properties properties) {
        super(OrichalcumTier.INSTANCE, properties.attributes(PickaxeItem.createAttributes(OrichalcumTier.INSTANCE, 1.0F, -2.8F)));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state);
    }
}
