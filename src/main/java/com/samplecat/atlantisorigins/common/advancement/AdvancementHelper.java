package com.samplecat.atlantisorigins.common.advancement;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementHelper {

    private static final ResourceLocation ROOT = id("root");
    private static final ResourceLocation DEEP_DIVER = id("deep_diver");
    private static final ResourceLocation ABYSS_WALKER = id("abyss_walker");
    private static final ResourceLocation CURE_DCS = id("cure_dcs");
    private static final ResourceLocation SURVIVE_HORROR = id("survive_horror");

    public static void grantRoot(ServerPlayer player) {
        grant(player, ROOT, "equip_oxygen_tank");
    }

    public static void grantDeepDiver(ServerPlayer player) {
        grant(player, DEEP_DIVER, "below_y_20");
    }

    public static void grantAbyssWalker(ServerPlayer player) {
        grant(player, ABYSS_WALKER, "below_sea_level");
    }

    public static void grantCureDcs(ServerPlayer player) {
        grant(player, CURE_DCS, "cure_dcs");
    }

    public static void grantSurviveHorror(ServerPlayer player) {
        grant(player, SURVIVE_HORROR, "horror");
    }

    private static void grant(ServerPlayer player, ResourceLocation advancementId, String criterion) {
        if (player.server == null) {
            return;
        }
        AdvancementHolder holder = player.server.getAdvancements().get(advancementId);
        if (holder != null) {
            player.getAdvancements().award(holder, criterion);
        }
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, path);
    }
}
