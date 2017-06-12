package minechem.apparatus.centrifuge;

import minechem.Compendium;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.apparatus.prefab.gui.element.GuiArrow;
import minechem.apparatus.prefab.gui.element.GuiEnergyBar;
import net.minecraft.entity.player.InventoryPlayer;

public class CentrifugeGUI extends BasicGuiContainer {
    private static final int arrowPosX = 92, arrowPosY = 33;

    public CentrifugeGUI(InventoryPlayer inventoryPlayer, CentrifugeTileEntity centrifuge) {
        super(new CentrifugeContainer(inventoryPlayer, centrifuge));
        this.name = centrifuge.getName();
        this.texture = Compendium.Resource.GUI.centrifuge;
        addGuiElement(new GuiEnergyBar(centrifuge.getEnergy(), 20, 25));
        addGuiElement(new GuiArrow(arrowPosX - 1, arrowPosY - 1, centrifuge::getProgression));
    }
}
