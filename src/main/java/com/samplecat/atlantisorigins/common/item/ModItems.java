package com.samplecat.atlantisorigins.common.item;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.block.ModBlocks;
import com.samplecat.atlantisorigins.common.entity.ModEntities;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AtlantisOrigins.MOD_ID);

    public static final DeferredItem<Item> OXYGEN_TANK = ITEMS.register(
            "oxygen_tank", () -> new OxygenTankItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> FLIPPER = ITEMS.register(
            "flipper", () -> new FlipperItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> GOGGLES = ITEMS.register(
            "goggles", () -> new GogglesItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> BEGINNER_DIVING_SUIT = ITEMS.register(
            "beginner_diving_suit", () -> new BeginnerDivingSuitItem(new Item.Properties()));

    public static final DeferredItem<Item> BEGINNER_DIVING_PANTS = ITEMS.register(
            "beginner_diving_pants", () -> new BeginnerDivingPantsItem(new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_HELMET = ITEMS.register(
            "orichalcum_helmet", () -> new OrichalcumArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_CHESTPLATE = ITEMS.register(
            "orichalcum_chestplate", () -> new OrichalcumArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_LEGGINGS = ITEMS.register(
            "orichalcum_leggings", () -> new OrichalcumArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_BOOTS = ITEMS.register(
            "orichalcum_boots", () -> new OrichalcumArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_STICK = ITEMS.register(
            "orichalcum_stick", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_PICKAXE = ITEMS.register(
            "orichalcum_pickaxe", () -> new OrichalcumPickaxeItem(new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_AXE = ITEMS.register(
            "orichalcum_axe", () -> new OrichalcumAxeItem(new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_SHOVEL = ITEMS.register(
            "orichalcum_shovel", () -> new OrichalcumShovelItem(new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_HOE = ITEMS.register(
            "orichalcum_hoe", () -> new OrichalcumHoeItem(new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_TRIDENT = ITEMS.register(
            "orichalcum_trident", () -> new OrichalcumTridentItem(new Item.Properties()));

    public static final DeferredItem<Item> DEEP_GUARDIAN_TRIDENT = ITEMS.register(
            "deep_guardian_trident", () -> new DeepGuardianTridentItem(new Item.Properties()));

    public static final DeferredItem<Item> POSEIDONS_FLESH = ITEMS.register(
            "poseidons_flesh", () -> new PoseidonsFleshItem(new Item.Properties().stacksTo(1).food(ModFoods.POSEIDONS_FLESH)));

    public static final DeferredItem<Item> POSEIDONS_SPEAR = ITEMS.register(
            "poseidons_spear", () -> new PoseidonsSpearItem(new Item.Properties()));

    public static final DeferredItem<Item> POSEIDONS_TOKEN = ITEMS.register(
            "poseidons_token", () -> new PoseidonsTokenItem(new Item.Properties()));

    public static final DeferredItem<Item> HYPERBARIC_CHAMBER = ITEMS.register(
            "hyperbaric_chamber", () -> new HyperbaricPumpItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> BRIMSTONE_DUST = ITEMS.register(
            "brimstone_dust", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RUBBER = ITEMS.register(
            "rubber", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CARBON_BLACK = ITEMS.register(
            "carbon_black", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GLOWING_SEED_CLUSTER = ITEMS.register(
            "glowing_seed_cluster", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SILICONE = ITEMS.register(
            "raw_silicone", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SILICONE = ITEMS.register(
            "silicone", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_CUPRITE = ITEMS.register(
            "raw_cuprite", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CUPRITE_INGOT = ITEMS.register(
            "cuprite_ingot", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_ORICHALCUM = ITEMS.register(
            "raw_orichalcum", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_SHARD = ITEMS.register(
            "orichalcum_shard", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ORICHALCUM_INGOT = ITEMS.register(
            "orichalcum_ingot", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SALT = ITEMS.register(
            "raw_salt", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SILVER = ITEMS.register(
            "raw_silver", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SILVER_NUGGET = ITEMS.register(
            "silver_nugget", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.register(
            "silver_ingot", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SILVER_AMALGAM = ITEMS.register(
            "silver_amalgam", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DEEP_SEA_WATER_BOTTLE = ITEMS.register(
            "deep_sea_water_bottle", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> ABYSSAL_EYE = ITEMS.register(
            "abyssal_eye", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BROKEN_LOG = ITEMS.register(
            "broken_log", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TEMPLE_INSCRIPTION = ITEMS.register(
            "temple_inscription", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RECOLLECTION_BOTTLE = ITEMS.register(
            "recollection_bottle", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> EYE_OF_REVELATION = ITEMS.register(
            "eye_of_revelation", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MORPH_STABLE_POTION = ITEMS.register(
            "morph_stable_potion", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> PAIN_POTION = ITEMS.register(
            "pain_potion", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> RAW_EEL = ITEMS.register(
            "raw_eel", () -> new Item(new Item.Properties().food(ModFoods.RAW_EEL)));

    public static final DeferredItem<Item> COOKED_EEL = ITEMS.register(
            "cooked_eel", () -> new Item(new Item.Properties().food(ModFoods.COOKED_EEL)));

    public static final DeferredItem<Item> RAW_CRAB_MEAT = ITEMS.register(
            "raw_crab_meat", () -> new Item(new Item.Properties().food(ModFoods.RAW_CRAB_MEAT)));

    public static final DeferredItem<Item> CRAB_MEAT_STEW = ITEMS.register(
            "crab_meat_stew", () -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .craftRemainder(Items.BOWL)
                    .food(ModFoods.CRAB_MEAT_STEW)));

    public static final DeferredItem<Item> RAW_GULPER_FLESH = ITEMS.register(
            "raw_gulper_flesh", () -> new Item(new Item.Properties().food(ModFoods.RAW_GULPER_FLESH)));

    public static final DeferredItem<Item> COOKED_GULPER = ITEMS.register(
            "cooked_gulper", () -> new Item(new Item.Properties().food(ModFoods.COOKED_GULPER)));

    public static final DeferredItem<Item> KRILL = ITEMS.register(
            "krill", () -> new Item(new Item.Properties().food(ModFoods.KRILL)));

    public static final DeferredItem<Item> KRILL_PASTE = ITEMS.register(
            "krill_paste", () -> new Item(new Item.Properties().food(ModFoods.KRILL_PASTE)));

    public static final DeferredItem<Item> KRILL_PASTE_BREAD = ITEMS.register(
            "krill_paste_bread", () -> new Item(new Item.Properties().food(ModFoods.KRILL_PASTE_BREAD)));

    public static final DeferredItem<Item> KELP_ROLL = ITEMS.register(
            "kelp_roll", () -> new Item(new Item.Properties().food(ModFoods.KELP_ROLL)));

    public static final DeferredItem<Item> SALTED_FISH = ITEMS.register(
            "salted_fish", () -> new Item(new Item.Properties().food(ModFoods.SALTED_FISH)));

    public static final DeferredItem<Item> DEEP_SEA_STEW = ITEMS.register(
            "deep_sea_stew", () -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .craftRemainder(Items.BOWL)
                    .food(ModFoods.DEEP_SEA_STEW)));

    public static final DeferredItem<Item> ABYSSAL_TENTACLE = ITEMS.register(
            "abyssal_tentacle", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MEMORY_FRAGMENT = ITEMS.register(
            "memory_fragment", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CUPRITE_NUGGET = ITEMS.register(
            "cuprite_nugget", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PAIN_CRYSTAL = ITEMS.register(
            "pain_crystal", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GAZING_CORE = ITEMS.register(
            "gazing_core", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FUSED_SCALE = ITEMS.register(
            "fused_scale", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ABYSSAL_JELLYFISH_SPAWN_EGG = ITEMS.register(
            "abyssal_jellyfish_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.ABYSSAL_JELLYFISH, 0x1A237E, 0x00BCD4,
                    new Item.Properties()));

    public static final DeferredItem<Item> BIOLUMINESCENT_FISH_SPAWN_EGG = ITEMS.register(
            "bioluminescent_fish_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.BIOLUMINESCENT_FISH, 0x00E5FF, 0x76FF03,
                    new Item.Properties()));

    public static final DeferredItem<Item> DEEP_EEL_SPAWN_EGG = ITEMS.register(
            "deep_eel_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.DEEP_EEL, 0x263238, 0x004D40,
                    new Item.Properties()));

    public static final DeferredItem<Item> ABYSSAL_CRAB_SPAWN_EGG = ITEMS.register(
            "abyssal_crab_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.ABYSSAL_CRAB, 0x3E2723, 0xFF5722,
                    new Item.Properties()));

    public static final DeferredItem<Item> GULPER_SPAWN_EGG = ITEMS.register(
            "gulper_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.GULPER, 0x1A237E, 0xFFEB3B,
                    new Item.Properties()));

    public static final DeferredItem<Item> KRILL_SWARM_SPAWN_EGG = ITEMS.register(
            "krill_swarm_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.KRILL_SWARM, 0xFF9800, 0xFFCC80,
                    new Item.Properties()));

    public static final DeferredItem<Item> STINGING_JELLYFISH_SPAWN_EGG = ITEMS.register(
            "stinging_jellyfish_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.STINGING_JELLYFISH, 0x4A148C, 0xE91E63,
                    new Item.Properties()));

    public static final DeferredItem<Item> GAZING_JELLYFISH_SPAWN_EGG = ITEMS.register(
            "gazing_jellyfish_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.GAZING_JELLYFISH, 0xFFD700, 0xFFF176,
                    new Item.Properties()));

    public static final DeferredItem<Item> DEEP_GUARDIAN_SPAWN_EGG = ITEMS.register(
            "deep_guardian_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.DEEP_GUARDIAN, 0x0D47A1, 0x00BCD4,
                    new Item.Properties()));

    public static final DeferredItem<Item> POSEIDONS_BLADE_SPAWN_EGG = ITEMS.register(
            "poseidons_blade_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.POSEIDONS_BLADE, 0x004D40, 0xFFEB3B,
                    new Item.Properties()));
}
