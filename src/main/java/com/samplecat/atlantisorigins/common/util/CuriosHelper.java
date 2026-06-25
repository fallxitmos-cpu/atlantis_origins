package com.samplecat.atlantisorigins.common.util;

import com.samplecat.atlantisorigins.common.item.GogglesItem;
import com.samplecat.atlantisorigins.common.item.OxygenTankItem;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public class CuriosHelper {

    public static boolean hasOxygenTankEquipped(Player player) {
        return getOxygenTankStack(player).isPresent();
    }

    public static Optional<ItemStack> getOxygenTankStack(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .flatMap(handler -> handler.findFirstCurio(stack -> stack.getItem() instanceof OxygenTankItem))
                .map(SlotResult::stack);
    }

    public static boolean hasGogglesEquipped(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .flatMap(handler -> handler.findFirstCurio(stack -> stack.getItem() instanceof GogglesItem))
                .isPresent();
    }
}
