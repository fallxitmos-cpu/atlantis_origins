package com.samplecat.atlantisorigins.common.fluid;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(
            NeoForgeRegistries.FLUID_TYPES, AtlantisOrigins.MOD_ID);

    public static final Supplier<FluidType> RUST_POISON = register("rust_poison",
            FluidType.Properties.create()
                    .density(1200)
                    .viscosity(1500)
                    .temperature(350)
                    .canSwim(true)
                    .canDrown(true)
                    .canExtinguish(false)
                    .canHydrate(false)
                    .rarity(Rarity.UNCOMMON));

    private static Supplier<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new FluidType(properties));
    }
}
