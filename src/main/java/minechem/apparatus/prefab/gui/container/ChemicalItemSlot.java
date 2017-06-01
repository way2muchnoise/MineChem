package minechem.apparatus.prefab.gui.container;

import minechem.item.chemical.ChemicalItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ChemicalItemSlot extends Slot {
    private boolean extractOnly;

    public ChemicalItemSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return !extractOnly && stack.getItem() instanceof ChemicalItem;
    }

    public ChemicalItemSlot setExtractOnly() {
        this.extractOnly = true;
        return this;
    }
}
