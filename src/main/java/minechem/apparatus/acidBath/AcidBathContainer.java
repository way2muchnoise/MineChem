package minechem.apparatus.acidBath;

import minechem.apparatus.prefab.gui.container.BasicContainer;
import minechem.apparatus.prefab.gui.container.ChemicalItemSlot;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class AcidBathContainer extends BasicContainer {
    private static final int inSlotX = 49, inSlotY = 33;
    private static final int firstOutSlotX = 109, firstInSlotY = 25;
    private static final int acidSlotX = 16, acidSlotY = 33;

    public AcidBathContainer(InventoryPlayer inventoryPlayer, AcidBathTileEntity acidBath) {
        bindPlayerInventory(inventoryPlayer);
        addSlotToContainer(new Slot(acidBath.getInventoryIn(), 0, inSlotX, inSlotY));
        int half = acidBath.getInventoryOut().getSizeInventory() / 2;
        for (int i = 0; i < half; i++) {
            addSlotToContainer(new ChemicalItemSlot(acidBath.getInventoryOut(), i, firstOutSlotX + i * 18, firstInSlotY).setExtractOnly());
            addSlotToContainer(new ChemicalItemSlot(acidBath.getInventoryOut(), i + half, firstOutSlotX + i * 18, firstInSlotY + 18).setExtractOnly());
        }
        addSlotToContainer(new ChemicalItemSlot(acidBath.getInventoryAcid(), 0, acidSlotX, acidSlotY));
    }
}
