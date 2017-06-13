package minechem.apparatus.prefab.tileEntity.storageTypes;

import minechem.item.chemical.ChemicalItem;
import net.minecraft.item.ItemStack;

public class ChemicalInventory extends BasicInventory {
    public ChemicalInventory(int inventorySize) {
        super(inventorySize, "chemicalInventory");
    }

    public ChemicalInventory(int inventorySize, String inventoryName) {
        super(inventorySize, inventoryName);
    }

    @Override
    public int getInventoryStackLimit() {
        return 256;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return super.isItemValidForSlot(index, stack) && stack.getItem() instanceof ChemicalItem;
    }
}
