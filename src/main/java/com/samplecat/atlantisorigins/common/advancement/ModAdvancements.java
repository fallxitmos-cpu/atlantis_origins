package com.samplecat.atlantisorigins.common.advancement;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAdvancements {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, AtlantisOrigins.MOD_ID);

    // Custom criteria triggers will be registered here.
}
