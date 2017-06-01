package minechem.apparatus.tier1.centrifuge;

import minechem.apparatus.prefab.tileEntity.BaseTileEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicEnergyStorage;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import minechem.registry.BlockRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class CentrifugeTileEntity extends BaseTileEntity implements ITickable
{
    private BasicInventory inventoryIn, inventoryOut;
    private BasicEnergyStorage energy;

    public CentrifugeTileEntity()
    {
        super(BlockRegistry.centrifugeBlock);
        this.inventoryIn = new BasicInventory(3, "insert").setListener(this);
        this.inventoryOut = new BasicInventory(3, "extract").setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new CombinedInvWrapper(inventoryIn.asCapability(), inventoryOut.asCapability()));
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
    public void update() {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        inventoryIn.writeToNBT(nbttagcompound);
        inventoryOut.writeToNBT(nbttagcompound);
        energy.writeToNBT(nbttagcompound);
        return nbttagcompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        inventoryIn.readFromNBT(nbttagcompound);
        inventoryOut.readFromNBT(nbttagcompound);
        energy.readFromNBT(nbttagcompound);
    }
}
