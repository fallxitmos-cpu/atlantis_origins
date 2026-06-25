package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.common.blockentity.AlchemicalReactorBlockEntity;
import com.samplecat.atlantisorigins.common.blockentity.ModBlockEntityTypes;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class CapabilityEventHandler {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntityTypes.ALCHEMICAL_REACTOR.get(),
                (blockEntity, side) -> ((AlchemicalReactorBlockEntity) blockEntity).getItemHandler()
        );

    }
}
