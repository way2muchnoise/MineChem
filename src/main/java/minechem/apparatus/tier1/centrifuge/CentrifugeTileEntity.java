package minechem.apparatus.tier1.centrifuge;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BaseTileEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicEnergyStorage;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CentrifugeTileEntity extends BaseTileEntity
{
    private BasicInventory inventory;
    private BasicEnergyStorage energy;

    public CentrifugeTileEntity()
    {
        super(Compendium.Naming.centrifuge);
        this.inventory = new BasicInventory(2, getName()).setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new InvWrapper(inventory));
        attachCapability(CapabilityEnergy.ENERGY, this.energy);
    }

    public BasicInventory getInventory() {
        return inventory;
    }

    public BasicEnergyStorage getEnergy() {
        return energy;
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
