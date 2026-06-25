package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.item.ModItems;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(modid = AtlantisOrigins.MOD_ID)
public class PoseidonsAnvilHandler {

    private static final int COMPLETION_COST = 10;

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (!isUnfinishedOrichalcum(left) || !right.is(ModItems.POSEIDONS_TOKEN.get())) {
            return;
        }

        ItemStack output = left.copy();
        output.remove(DataComponents.CUSTOM_DATA);
        event.setOutput(output);
        event.setCost(COMPLETION_COST);
        event.setMaterialCost(1);
    }

    private static boolean isUnfinishedOrichalcum(ItemStack stack) {
        CustomData custom = stack.get(DataComponents.CUSTOM_DATA);
        if (custom == null) {
            return false;
        }
        return custom.copyTag().getBoolean("unfinished");
    }
}
