package minechem.apparatus.electrolysis;

import minechem.apparatus.prefab.gui.container.BasicContainer;
import minechem.apparatus.prefab.gui.container.ChemicalItemSlot;
import net.minecraft.entity.player.InventoryPlayer;

public class ElectrolysisContainer extends BasicContainer {
    private static final int firstInSlotX = 77, firstOutSlotX = 133;
    private static final int firstInSlotY = 35, firstOutSlotY = 26;

    public ElectrolysisContainer(InventoryPlayer inventoryPlayer, ElectrolysisTileEntity electrolysis) {
        bindPlayerInventory(inventoryPlayer);
        addSlotToContainer(new ChemicalItemSlot(electrolysis.getInventoryIn(), 0, firstInSlotX, firstInSlotY));
        for (int i = 0; i < electrolysis.getInventoryOut().getSizeInventory(); i++) {
            addSlotToContainer(new ChemicalItemSlot(electrolysis.getInventoryOut(), i, firstOutSlotX, firstOutSlotY + i * 18).setExtractOnly());
        }
    }
}
