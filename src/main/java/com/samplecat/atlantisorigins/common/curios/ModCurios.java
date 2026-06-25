package com.samplecat.atlantisorigins.common.curios;

import net.neoforged.bus.api.IEventBus;

public class ModCurios {
    public static void register(IEventBus modEventBus) {
        // Slot registration is handled via datapack files:
        // data/atlantis_origins/curios/slots/oxygen_tank.json
        // data/atlantis_origins/curios/entities/player.json
        // Item integration is handled via ICurioItem on OxygenTankItem.
    }
}
