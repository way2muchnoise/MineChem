package minechem.apparatus.electricCrucible;

import minechem.Compendium;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.apparatus.prefab.gui.element.GuiArrow;
import minechem.apparatus.prefab.gui.element.GuiEnergyBar;
import net.minecraft.entity.player.InventoryPlayer;

public class ElectricCrucibleGUI extends BasicGuiContainer {
    private static final int arrowPosX = 74, arrowPosY = 33;

    public ElectricCrucibleGUI(InventoryPlayer inventoryPlayer, ElectricCrucibleTileEntity electricCrucible) {
        super(new ElectricCrucibleContainer(inventoryPlayer, electricCrucible));
        this.name = electricCrucible.getName();
        this.texture = Compendium.Resource.GUI.electricCrucible;
        addGuiElement(new GuiEnergyBar(electricCrucible.getEnergy(), 12, 23));
        addGuiElement(new GuiArrow(arrowPosX - 1, arrowPosY - 1, electricCrucible::getProgression));
    }
}
