package minechem.apparatus.tier1.electrolysis;

import minechem.apparatus.prefab.tileEntity.BaseTileEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicEnergyStorage;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import minechem.registry.BlockRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

public class ElectrolysisTileEntity extends BaseTileEntity
{
    private BasicInventory inventory;
    private BasicEnergyStorage energy;

    public ElectrolysisTileEntity()
    {
        super(BlockRegistry.electrolysisBlock);
        this.inventory = new BasicInventory(2, getName()).setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory.asCapability());
        attachCapability(CapabilityEnergy.ENERGY, this.energy);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        inventory.writeToNBT(nbttagcompound);
        energy.writeToNBT(nbttagcompound);
        return nbttagcompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        inventory.readFromNBT(nbttagcompound);
        energy.readFromNBT(nbttagcompound);
    }
}
