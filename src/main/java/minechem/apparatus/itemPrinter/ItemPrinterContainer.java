package minechem.apparatus.itemPrinter;

import minechem.apparatus.prefab.gui.container.BasicContainer;
import minechem.apparatus.prefab.gui.container.ChemicalItemSlot;
import minechem.apparatus.prefab.gui.container.GhostItemSlot;
import net.minecraft.entity.player.InventoryPlayer;

public class ItemPrinterContainer extends BasicContainer {
    private static final int inFirstSlotX = 58, inFirstSlotY = 20;
    private static final int outSlotX = 147, outSlotY = 37;
    private static final int previewSlotX = 120, previewSlotY = 49;

    public ItemPrinterContainer(InventoryPlayer inventoryPlayer, ItemPrinterTileEntity itemPrinter) {
        bindPlayerInventory(inventoryPlayer);
        for (int i = 0; i < itemPrinter.getInput().getSizeInventory(); i++) {
            addSlotToContainer(new ChemicalItemSlot(itemPrinter.getInput(), i, inFirstSlotX + (i / 3) * 18, inFirstSlotY + (i % 3) * 18));
        }
        addSlotToContainer(new GhostItemSlot(previewSlotX, previewSlotY, itemPrinter::getCurrentRecipe));
        addSlotToContainer(new ChemicalItemSlot(itemPrinter.getOutput(), 0, outSlotX, outSlotY));
    }
}
