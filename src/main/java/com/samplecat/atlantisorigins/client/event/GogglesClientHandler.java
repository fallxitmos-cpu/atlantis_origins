package com.samplecat.atlantisorigins.client.event;

import com.samplecat.atlantisorigins.common.attachment.PlayerAttachments;
import com.samplecat.atlantisorigins.common.util.CuriosHelper;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.material.FogType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

/**
 * Removes the underwater fog blur when the player is wearing the Curios goggles slot item.
 */
public class GogglesClientHandler {

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null) {
            return;
        }

        // Only affect submerged water fog.
        if (minecraft.gameRenderer.getMainCamera().getFluidInCamera() != FogType.WATER) {
            return;
        }

        if (!CuriosHelper.hasGogglesEquipped(minecraft.player) && !PlayerAttachments.isEvolved(minecraft.player)) {
            return;
        }

        float renderDistance = minecraft.gameRenderer.getRenderDistance();
        float farPlane = renderDistance * 16.0F;

        // Expand vanilla fog planes so the underwater fog is pushed out of view.
        event.setNearPlaneDistance(-8.0F);
        event.setFarPlaneDistance(farPlane);
        event.setCanceled(true);

        // Also push the shader fog uniforms as a best-effort for shader packs / Sodium
        // that read the vanilla fog start/end values. Fully custom shader water fog may
        // still apply regardless, because shader packs handle fog in their own programs.
        RenderSystem.setShaderFogStart(-8.0F);
        RenderSystem.setShaderFogEnd(farPlane);
    }
}
