package com.NerFeed.NerFeedMod.screen;

import com.NerFeed.NerFeedMod.block.entity.DryingTableMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DryingTableScreen extends AbstractContainerScreen<DryingTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("nerfeedmod", "textures/gui/container/drying_table.png");

    public DryingTableScreen(DryingTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        // Отрисовка стрелки прогресса
        int progress = menu.getProgress();
        int maxProgress = menu.getMaxProgress();
        if (progress > 0 && maxProgress > 0) {
            int arrowWidth = (int) ((float) progress / maxProgress * 24);
            guiGraphics.blit(TEXTURE, x + 79, y + 34, 176, 14, arrowWidth, 16);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }
}