package com.samplecat.atlantisorigins.client.screen;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.menu.AlchemicalReactorMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlchemicalReactorScreen extends AbstractContainerScreen<AlchemicalReactorMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "textures/gui/alchemical_reactor.png");

    public AlchemicalReactorScreen(AlchemicalReactorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // Progress arrow
        int progress = menu.getCookProgress();
        if (progress > 0) {
            guiGraphics.blit(TEXTURE, x + 79, y + 34, 176, 14, progress + 1, 16, 256, 256);
        }

        // Flame / heat indicator
        if (menu.getCookTime() > 0) {
            guiGraphics.blit(TEXTURE, x + 45, y + 55, 176, 0, 14, 14, 256, 256);
        }
    }
}
