package minechem.apparatus.prefab.gui.element;

import minechem.Compendium;
import minechem.helper.LocalizationHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class GuiEnergyBar extends GuiElement {
    private static final int defaultWidth = 18, defaultHeight = 39;

    private IEnergyStorage energyStorage;

    /**
     * Create a energybar
     *
     * @param posX   the x pos of the element (origin is the left-top of the parent gui)
     * @param posY   the y pos of the element (origin is the left-top of the parent gui)
     * @param width  the width of the element
     * @param height the height of the element
     */
    public GuiEnergyBar(IEnergyStorage energyStorage, int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        this.energyStorage = energyStorage;
    }

    /**
     * Create a energybar
     *
     * @param posX the x pos of the element (origin is the left-top of the parent gui)
     * @param posY the y pos of the element (origin is the left-top of the parent gui)
     */
    public GuiEnergyBar(IEnergyStorage energyStorage, int posX, int posY) {
        this(energyStorage, posX, posY, defaultWidth, defaultHeight);
    }


    @Override
    public void drawBackground(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        bindTexture(Compendium.Resource.GUI.guiElements);
        drawTexturedModalRectScaled(guiLeft + posX, guiTop + posY, 0, 40, defaultWidth, defaultHeight, width, height);

        if (energyStorage != null && energyStorage.getEnergyStored() > 0) {
            float height = 1.0F * this.height / energyStorage.getMaxEnergyStored() * energyStorage.getEnergyStored();
            drawTexturedModalRectScaled(guiLeft + posX, guiTop + posY + this.height - 1 - height, 18, 40, defaultWidth, height, width, height);
        }
    }

    @Override
    public void drawForeground(int guiLeft, int guiTop, int mouseX, int mouseY) {
        drawTooltip(guiLeft, guiTop, mouseX, mouseY);
    }

    public void drawTooltip(int guiLeft, int guiTop, int mouseX, int mouseY) {
        mouseX -= guiLeft;
        mouseY -= guiTop;
        if (!mouseInElement(mouseX, mouseY)) {
            return;
        }

        List<String> description = new ArrayList<>(1);
        int stored = energyStorage.getEnergyStored();
        int capacity = energyStorage.getMaxEnergyStored();
        description.add(LocalizationHelper.getFormattedString("gui.element.energy.fe", stored, capacity));
        drawHoveringText(description, mouseX, mouseY, getFontRenderer());
    }

}
