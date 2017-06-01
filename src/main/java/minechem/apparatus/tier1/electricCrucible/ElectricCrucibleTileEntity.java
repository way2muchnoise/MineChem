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
    private BasicInventory inventory;
    private BasicEnergyStorage energy;

    public ElectricCrucibleTileEntity()
    {
        super(BlockRegistry.electricCrucibleBlock);
        this.inventory = new BasicInventory(2, getName()).setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory.asCapability());
        attachCapability(CapabilityEnergy.ENERGY, this.energy);
    }

    public BasicInventory getInventory() {
        return inventory;
    }

    public BasicEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.readFromNBT(compound);
        energy.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        inventory.writeToNBT(compound);
        energy.writeToNBT(compound);
        return compound;
    }
}
