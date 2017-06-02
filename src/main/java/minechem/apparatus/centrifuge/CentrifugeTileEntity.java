package minechem.apparatus.centrifuge;

import minechem.Config;
import minechem.apparatus.prefab.tileEntity.BasicTileTickingEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.*;
import minechem.chemical.process.ChemicalProcessType;
import minechem.registry.BlockRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

public class CentrifugeTileEntity extends BasicTileTickingEntity
{
    private BasicInventory inventoryIn, inventoryOut;
    private ProcessingInventory processingInventory;
    private BasicEnergyStorage energy;

    public CentrifugeTileEntity()
    {
        super(BlockRegistry.centrifugeBlock);
        this.inventoryIn = new BasicInventory(1, "insert").setListener(this);
        this.inventoryOut = new BasicInventory(3, "extract").setListener(this);
        this.processingInventory = new ProcessingInventory(5).setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, BasicInventory.asCapability(inventoryIn, inventoryOut));
        attachCapability(CapabilityEnergy.ENERGY, this.energy);
        registerDropableInventories(inventoryIn, inventoryOut); // You don't get the processing inventory back
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

    public int getProgression() {
        return processingInventory.getProgress();
    }

    @Override
    public void update() {
        doChemicalProcessUpdate(inventoryIn, inventoryOut, processingInventory, ChemicalProcessType.centrifuge);
        super.update();
    }

    @Override
    protected boolean processingTick() {
        return tryAndExtractEnergy(energy, Config.energyConsumption);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        return INBTWritable.writeToNBT(super.writeToNBT(nbttagcompound), inventoryIn, inventoryOut, processingInventory, energy);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        INBTWritable.readFromNBT(nbttagcompound, inventoryIn, inventoryOut, processingInventory, energy);
    }
}
