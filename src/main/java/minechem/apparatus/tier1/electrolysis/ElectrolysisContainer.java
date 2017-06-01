package minechem.apparatus.tier1.electrolysis;

import minechem.apparatus.prefab.gui.container.BasicContainer;
import minechem.apparatus.prefab.gui.container.ChemicalItemSlot;
import net.minecraft.entity.player.InventoryPlayer;

public class ElectrolysisContainer extends BasicContainer {
    private static final int firstInSlotX = 77, firstOutSlotX = 133;
    private static final int firstSlotY = 26;

    public ElectrolysisContainer(InventoryPlayer inventoryPlayer, ElectrolysisTileEntity electrolysis) {
        bindPlayerInventory(inventoryPlayer);
        for (int i = 0; i < electrolysis.getInventoryIn().getSizeInventory(); i++) {
            addSlotToContainer(new ChemicalItemSlot(electrolysis.getInventoryIn(), i, firstInSlotX, firstSlotY + i * 18));
        }
        for (int i = 0; i < electrolysis.getInventoryOut().getSizeInventory(); i++) {
            addSlotToContainer(new ChemicalItemSlot(electrolysis.getInventoryOut(), i, firstOutSlotX, firstSlotY + i * 18).setExtractOnly());
        }
    }
}
