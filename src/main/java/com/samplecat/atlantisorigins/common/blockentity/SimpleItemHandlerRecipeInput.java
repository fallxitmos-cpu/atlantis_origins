package com.samplecat.atlantisorigins.common.blockentity;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.items.ItemStackHandler;

public record SimpleItemHandlerRecipeInput(ItemStackHandler handler) implements RecipeInput {
    @Override
    public int size() {
        return handler.getSlots();
    }

    @Override
    public ItemStack getItem(int slot) {
        return handler.getStackInSlot(slot);
    }
}
