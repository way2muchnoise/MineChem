package minechem.apparatus.prefab.gui.element;

import minechem.Compendium;
import minechem.helper.ColourHelper;
import minechem.helper.LocalizationHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class GuiEnergybar extends GuiElement {

    private IEnergyStorage energyStorage;
    private int colour;

    /**
     * Create a energybar
     *
     * @param posX   the x pos of the element (origin is the left-top of the parent gui)
     * @param posY   the y pos of the element (origin is the left-top of the parent gui)
     * @param width  the width of the element
     * @param height the height of the element
     */
    public GuiEnergybar(IEnergyStorage energyStorage, int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        this.energyStorage = energyStorage;
        this.colour = Compendium.Color.TrueColor.red;
    }

    /**
     * Create a energybar
     *
     * @param posX   the x pos of the element (origin is the left-top of the parent gui)
     * @param posY   the y pos of the element (origin is the left-top of the parent gui)
     */
    public GuiEnergybar(IEnergyStorage energyStorage, int posX, int posY) {
        this(energyStorage, posX, posY, 18, 39);
    }


    @Override
    public void drawBackground(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.color(ColourHelper.getRed(colour), ColourHelper.getGreen(colour), ColourHelper.getBlue(colour));

        bindTexture(Compendium.Resource.GUI.guiElements);
        drawTexturedModalRectScaled(guiLeft + posX, guiTop + posY, 0, 40, 18, 39, width, height);

        GlStateManager.color(1.0F, 1.0F, 1.0F);

        if (energyStorage != null && energyStorage.getEnergyStored() > 0) {
            float height = 1.0F * this.height / energyStorage.getMaxEnergyStored() * energyStorage.getEnergyStored();
            drawTexturedModalRectScaled(guiLeft + posX, guiTop + posY + this.height - height, 18, 40 + (39 - height),18, height, width, height);
        }
    }

    @Override
    public void drawForeground(int guiLeft, int guiTop, int mouseX, int mouseY) {
        drawTooltip(guiLeft, guiTop, mouseX, mouseY);
    }

    public void drawTooltip(int guiLeft, int guiTop, int mouseX, int mouseY)
    {
        mouseX -= guiLeft;
        mouseY -= guiTop;
        if (!mouseInElement(mouseX, mouseY))
        {
            return;
        }

        List<String> description = new ArrayList<>(1);
        int stored = energyStorage.getEnergyStored();
        int capacity = energyStorage.getMaxEnergyStored();
        description.add(LocalizationHelper.getFormattedString("gui.element.energy.fe", stored, capacity));
        drawHoveringText(description, mouseX, mouseY, getFontRenderer());
    }

}
