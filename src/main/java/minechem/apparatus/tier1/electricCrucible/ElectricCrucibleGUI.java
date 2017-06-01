package minechem.apparatus.tier1.electricCrucible;

import minechem.Compendium;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.apparatus.prefab.gui.element.GuiEnergybar;
import net.minecraft.entity.player.InventoryPlayer;

public class ElectricCrucibleGUI extends BasicGuiContainer {
    private static final int arrowProgressionX = 180, arrowProgressionY = 6;
    private static final int arrowPosX = 74, arrowPosY = 33, arrowW = 22, arrowH = 15;

    private ElectricCrucibleTileEntity electricCrucible;

    public ElectricCrucibleGUI(InventoryPlayer inventoryPlayer, ElectricCrucibleTileEntity electricCrucible) {
        super(new ElectricCrucibleContainer(inventoryPlayer, electricCrucible));
        this.name = electricCrucible.getName();
        this.texture = Compendium.Resource.GUI.electricCrucible;
        this.electricCrucible = electricCrucible;
        addGuiElement(new GuiEnergybar(electricCrucible.getEnergy(), 12, 23));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // TODO arrow
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0,176, 166);
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }
}
