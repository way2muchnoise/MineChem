package minechem.apparatus.tier1.electrolysis;

import minechem.Compendium;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.apparatus.prefab.gui.element.GuiEnergybar;
import minechem.apparatus.prefab.gui.element.GuiFluidTank;
import net.minecraft.entity.player.InventoryPlayer;

public class ElectrolysisGUI extends BasicGuiContainer {
    private static final int arrowProgressionX = 180, arrowProgressionY = 6;
    private static final int arrowPosX = 74, arrowPosY = 33, arrowW = 22, arrowH = 15;

    private ElectrolysisTileEntity electrolysis;

    public ElectrolysisGUI(InventoryPlayer inventoryPlayer, ElectrolysisTileEntity electrolysis) {
        super(new ElectrolysisContainer(inventoryPlayer, electrolysis));
        this.name = electrolysis.getName();
        this.texture = Compendium.Resource.GUI.electrolysis;
        this.electrolysis = electrolysis;
        addGuiElement(new GuiEnergybar(electrolysis.getEnergy(), 12, 23));
        addGuiElement(new GuiFluidTank(electrolysis.getTank(), 32, 23));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // TODO arrow
    }
}
