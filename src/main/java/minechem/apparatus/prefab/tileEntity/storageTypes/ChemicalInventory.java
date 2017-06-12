package minechem.apparatus.prefab.tileEntity.storageTypes;

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
}
