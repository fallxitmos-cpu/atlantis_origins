package com.samplecat.atlantisorigins.common.ai;

import com.samplecat.atlantisorigins.common.ai.squad.SquadManager;
import com.samplecat.atlantisorigins.common.entity.DeepGuardian;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * Event handlers for Deep Guardian squad lifecycle.
 */
public class SquadEvents {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (!(event.getEntity() instanceof DeepGuardian dead)) {
            return;
        }
        SquadManager.onCaptainDeath(dead);
    }
}
