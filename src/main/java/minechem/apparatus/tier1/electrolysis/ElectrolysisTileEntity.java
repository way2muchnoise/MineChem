package minechem.apparatus.tier1.electrolysis;

import minechem.apparatus.prefab.tileEntity.BaseTileEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicEnergyStorage;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicFluidTank;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import minechem.registry.BlockRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class ElectrolysisTileEntity extends BaseTileEntity
{
    private BasicInventory inventoryIn, inventoryOut;
    private BasicEnergyStorage energy;
    private BasicFluidTank tank;

    public ElectrolysisTileEntity()
    {
        super(BlockRegistry.electrolysisBlock);
        this.inventoryIn = new BasicInventory(2, "insert").setListener(this);
        this.inventoryOut = new BasicInventory(2, "extract").setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        this.tank = new BasicFluidTank(3000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new CombinedInvWrapper(inventoryIn.asCapability(), inventoryOut.asCapability()));
        attachCapability(CapabilityEnergy.ENERGY, this.energy);
        attachCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this.tank);
    }

    public BasicInventory getInventoryOut() {
        return inventoryOut;
    }

    public BasicEnergyStorage getEnergy() {
        return energy;
    }

    public BasicInventory getInventoryIn() {
        return inventoryIn;
    }

    public BasicFluidTank getTank() {
        return tank;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        inventoryIn.writeToNBT(nbttagcompound);
        inventoryOut.writeToNBT(nbttagcompound);
        energy.writeToNBT(nbttagcompound);
        tank.writeToNBT(nbttagcompound);
        return nbttagcompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        inventoryIn.readFromNBT(nbttagcompound);
        inventoryOut.readFromNBT(nbttagcompound);
        energy.readFromNBT(nbttagcompound);
        tank.readFromNBT(nbttagcompound);
    }
}
