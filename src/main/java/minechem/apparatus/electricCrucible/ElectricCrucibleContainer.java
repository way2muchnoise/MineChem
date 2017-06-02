package minechem.apparatus.electricCrucible;

import minechem.apparatus.prefab.gui.container.BasicContainer;
import minechem.apparatus.prefab.gui.container.ChemicalItemSlot;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ElectricCrucibleContainer extends BasicContainer {
    private static final int inSlotX = 49, inSlotY = 33;
    private static final int firstOutSlotX = 109, firstInSlotY = 25;

    public ElectricCrucibleContainer(InventoryPlayer inventoryPlayer, ElectricCrucibleTileEntity electricCrucible) {
        bindPlayerInventory(inventoryPlayer);
        addSlotToContainer(new Slot(electricCrucible.getInventoryIn(), 0, inSlotX, inSlotY));
        int half = electricCrucible.getInventoryOut().getSizeInventory() / 2;
        for (int i = 0; i < half; i++) {
            addSlotToContainer(new ChemicalItemSlot(electricCrucible.getInventoryOut(), i, firstOutSlotX + i * 18, firstInSlotY).setExtractOnly());
            addSlotToContainer(new ChemicalItemSlot(electricCrucible.getInventoryOut(), i + half, firstOutSlotX + i * 18, firstInSlotY + 18).setExtractOnly());
        }
    }
}