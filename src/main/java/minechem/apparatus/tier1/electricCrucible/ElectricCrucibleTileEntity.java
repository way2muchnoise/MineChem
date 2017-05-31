package minechem.apparatus.tier1.electricCrucible;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BaseTileEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicEnergyStorage;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ElectricCrucibleTileEntity extends BaseTileEntity
{
    private BasicInventory inventory;
    private BasicEnergyStorage energy;

    public ElectricCrucibleTileEntity()
    {
        super(Compendium.Naming.electricCrucible);
        this.inventory = new BasicInventory(2, getName());
        this.energy = new BasicEnergyStorage(10000);
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
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.readFromNBT(compound);
        energy.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        inventory.writeToNBT(compound);
        energy.readFromNBT(compound);
        return compound;
    }
}
