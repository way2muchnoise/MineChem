package minechem.apparatus.tier1.centrifuge;

import minechem.Compendium;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.apparatus.prefab.gui.element.GuiEnergybar;
import net.minecraft.entity.player.InventoryPlayer;

public class CentrifugeGUI extends BasicGuiContainer {
    private static final int arrowProgressionX = 180, arrowProgressionY = 6;
    private static final int arrowPosX = 92, arrowPosY = 33, arrowW = 22, arrowH = 15;

    private CentrifugeTileEntity centrifuge;

    public CentrifugeGUI(InventoryPlayer inventoryPlayer, CentrifugeTileEntity centrifuge) {
        super(new CentrifugeContainer(inventoryPlayer, centrifuge));
        this.name = centrifuge.getName();
        this.texture = Compendium.Resource.GUI.centrifuge;
        this.centrifuge = centrifuge;
        addGuiElement(new GuiEnergybar(centrifuge.getEnergy(), 20, 25));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // TODO arrow
    }
}
