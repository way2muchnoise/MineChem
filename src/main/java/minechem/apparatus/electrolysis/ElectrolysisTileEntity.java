package minechem.apparatus.electrolysis;

import minechem.Config;
import minechem.apparatus.prefab.tileEntity.BasicTileTickingEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.*;
import minechem.chemical.process.ChemicalProcessType;
import minechem.registry.BlockRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class ElectrolysisTileEntity extends BasicTileTickingEntity
{
    private BasicInventory inventoryIn, inventoryOut;
    private ProcessingInventory processingInventory;
    private BasicEnergyStorage energy;
    private BasicFluidTank tank;

    public ElectrolysisTileEntity()
    {
        super(BlockRegistry.electrolysisBlock);
        this.inventoryIn = new BasicInventory(1, "insert").setListener(this);
        this.inventoryOut = new BasicInventory(2, "extract").setListener(this).setOutput();
        this.processingInventory = new ProcessingInventory(5).setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        this.tank = new BasicFluidTank(3000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, BasicInventory.asCapability(inventoryIn, inventoryOut));
        attachCapability(CapabilityEnergy.ENERGY, this.energy);
        attachCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this.tank);
        registerDropableInventories(inventoryIn, inventoryOut); // You don't get the processing inventory back
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

    public int getProgression() {
        return processingInventory.getProgress();
    }

    @Override
    public void update() {
        doChemicalProcessUpdate(inventoryIn, inventoryOut, processingInventory, ChemicalProcessType.electrolysis);
        super.update();
    }

    @Override
    protected boolean processingTick() {
        if (tryAndExtractEnergy(energy, Config.energyConsumption)) {
            if(tryAndExtractFluid(tank, Config.fluidConsumption)) {
                return true;
            }
            // put energy back when there was no fluid
            energy.receiveEnergy(Config.energyConsumption, false);
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        INBTWritable.readNBT(nbttagcompound, inventoryIn, inventoryOut, tank, processingInventory, energy);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        return INBTWritable.writeNBT(super.writeToNBT(nbttagcompound), inventoryIn, inventoryOut, tank, processingInventory, energy);
    }
}
