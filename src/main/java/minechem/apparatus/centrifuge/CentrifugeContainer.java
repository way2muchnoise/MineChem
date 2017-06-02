package minechem.apparatus.centrifuge;

import minechem.apparatus.prefab.gui.container.BasicContainer;
import minechem.apparatus.prefab.gui.container.ChemicalItemSlot;
import net.minecraft.entity.player.InventoryPlayer;

public class CentrifugeContainer extends BasicContainer {
    private static final int firstInSlotX = 67, firstOutSlotX = 125;
    private static final int firstSlotY = 34, firstOutSlotY = 16;

    public CentrifugeContainer(InventoryPlayer inventoryPlayer, CentrifugeTileEntity centrifuge) {
        bindPlayerInventory(inventoryPlayer);
        addSlotToContainer(new ChemicalItemSlot(centrifuge.getInventoryIn(), 0, firstInSlotX, firstSlotY));
        for (int i = 0; i < centrifuge.getInventoryOut().getSizeInventory(); i++) {
            addSlotToContainer(new ChemicalItemSlot(centrifuge.getInventoryOut(), i, firstOutSlotX, firstOutSlotY + i * 18).setExtractOnly());
        }
    }
}
