package minechem.apparatus.tier1.centrifuge;

import minechem.apparatus.prefab.gui.container.BasicContainer;
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
            addSlotToContainer(new CentrifugeSlot(centrifuge.getInventoryIn(), i, firstInSlotX, firstSlotY + i * 18));
        }
        for (int i = 0; i < centrifuge.getInventoryOut().getSizeInventory(); i++) {
            addSlotToContainer(new CentrifugeSlot(centrifuge.getInventoryOut(), i, firstOutSlotX, firstSlotY + i * 18));
        }
    }

    public static class CentrifugeSlot extends Slot {
        public CentrifugeSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() instanceof ChemicalItem;
        }
    }
}
