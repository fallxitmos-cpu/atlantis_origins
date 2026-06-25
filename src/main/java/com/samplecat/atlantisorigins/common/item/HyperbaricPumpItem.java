package com.samplecat.atlantisorigins.common.item;

import com.samplecat.atlantisorigins.common.advancement.AdvancementHelper;
import com.samplecat.atlantisorigins.common.attachment.PlayerAttachments;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HyperbaricPumpItem extends Item {

    public HyperbaricPumpItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            return InteractionResultHolder.success(stack);
        }

        int stage = PlayerAttachments.getDcsStage(player);
        boolean pending = PlayerAttachments.isDcsPendingMild(player);

        if (stage > 0 || pending) {
            PlayerAttachments.setDcsStage(player, 0);
            PlayerAttachments.setDcsTimer(player, 0);
            PlayerAttachments.setDcsPendingMild(player, false);
            player.setTicksFrozen(0);

            player.displayClientMessage(Component.translatable("message.atlantis_origins.dcs_cured"), true);
            if (player instanceof ServerPlayer serverPlayer) {
                AdvancementHelper.grantCureDcs(serverPlayer);
            }

            player.getCooldowns().addCooldown(this, 40);
        } else {
            player.displayClientMessage(Component.translatable("message.atlantis_origins.no_dcs_to_cure"), true);
        }

        return InteractionResultHolder.success(stack);
    }
}
