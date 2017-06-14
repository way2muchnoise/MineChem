package minechem.apparatus.acidBath;

import minechem.Compendium;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.apparatus.prefab.gui.element.GuiArrow;
import minechem.apparatus.prefab.gui.element.GuiEnergyBar;
import net.minecraft.entity.player.InventoryPlayer;

public class AcidBathGui extends BasicGuiContainer {
    private static final int arrowPosX = 74, arrowPosY = 33;

    public AcidBathGui(InventoryPlayer inventoryPlayer, AcidBathTileEntity acidBath) {
        super(new AcidBathContainer(inventoryPlayer, acidBath));
        this.name = acidBath.getName();
        this.texture = Compendium.Resource.GUI.acidBath;
        addGuiElement(new GuiArrow(arrowPosX - 1, arrowPosY - 1, acidBath::getProgression));
    }
}
