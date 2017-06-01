package minechem.apparatus.tier1.electricCrucible;

import minechem.apparatus.prefab.tileEntity.BaseTileEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicEnergyStorage;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import minechem.registry.BlockRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

public class ElectricCrucibleTileEntity extends BaseTileEntity
{
    private BasicInventory inventoryIn, inventoryOut;
    private BasicEnergyStorage energy;

    public ElectricCrucibleTileEntity()
    {
        super(BlockRegistry.electricCrucibleBlock);
        this.inventoryIn = new BasicInventory(1, "insert").setListener(this);
        this.inventoryOut = new BasicInventory(6, "extract").setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventoryIn.asCapability());
        attachCapability(CapabilityEnergy.ENERGY, this.energy);
    }

    public BasicInventory getInventoryIn() {
        return inventoryIn;
    }

    public BasicInventory getInventoryOut() {
        return inventoryOut;
    }

    public BasicEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventoryIn.readFromNBT(compound);
        inventoryOut.readFromNBT(compound);
        energy.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        inventoryIn.writeToNBT(compound);
        inventoryOut.writeToNBT(compound);
        energy.writeToNBT(compound);
        return compound;
    }
}
