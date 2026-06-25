package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.config.Config;
import com.samplecat.atlantisorigins.common.item.OrichalcumArmorItem;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * Gameplay effects for the orichalcum armor set.
 * <p>
 * Each worn piece reduces incoming damage by 5%, stacking additively up to 20%
 * for a full set.
 */
@EventBusSubscriber(modid = AtlantisOrigins.MOD_ID)
public class OrichalcumArmorEventHandler {

    private static final float REDUCTION_PER_PIECE = 0.05F;

    @SubscribeEvent
    public static void onLivingDamagePre(LivingDamageEvent.Pre event) {
        if (!Config.ORICHALCUM_ARMOR_EFFECTS_ENABLED.get()) {
            return;
        }

        LivingEntity entity = event.getEntity();

        int pieces = 0;
        for (ItemStack stack : entity.getArmorSlots()) {
            if (stack.getItem() instanceof OrichalcumArmorItem) {
                pieces++;
            }
        }

        if (pieces == 0) {
            return;
        }

        float multiplier = 1.0F - (REDUCTION_PER_PIECE * pieces);
        float newDamage = event.getNewDamage() * multiplier;
        event.setNewDamage(Math.max(newDamage, 0.0F));
    }
}
