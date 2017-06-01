package minechem.apparatus.tier1.centrifuge;

import minechem.apparatus.prefab.gui.container.BasicContainer;
import minechem.apparatus.prefab.gui.container.ChemicalItemSlot;
import minechem.item.chemical.ChemicalItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CentrifugeContainer extends BasicContainer {
    private static final int firstInSlotX = 67, firstOutSlotX = 125;
    private static final int firstSlotY = 16;

    public CentrifugeContainer(InventoryPlayer inventoryPlayer, CentrifugeTileEntity centrifuge) {
        bindPlayerInventory(inventoryPlayer);
        for (int i = 0; i < centrifuge.getInventoryIn().getSizeInventory(); i++) {
            addSlotToContainer(new ChemicalItemSlot(centrifuge.getInventoryIn(), i, firstInSlotX, firstSlotY + i * 18));
        }
        for (int i = 0; i < centrifuge.getInventoryOut().getSizeInventory(); i++) {
            addSlotToContainer(new ChemicalItemSlot(centrifuge.getInventoryOut(), i, firstOutSlotX, firstSlotY + i * 18).setExtractOnly());
        }
    }
}
