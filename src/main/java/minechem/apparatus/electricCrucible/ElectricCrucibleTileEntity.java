package minechem.apparatus.electricCrucible;

import minechem.Config;
import minechem.apparatus.prefab.tileEntity.BasicTileTickingEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicEnergyStorage;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import minechem.apparatus.prefab.tileEntity.storageTypes.INBTWritable;
import minechem.apparatus.prefab.tileEntity.storageTypes.ProcessingInventory;
import minechem.chemical.process.ChemicalProcessType;
import minechem.registry.BlockRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

public class ElectricCrucibleTileEntity extends BasicTileTickingEntity {
    private BasicInventory inventoryIn, inventoryOut;
    private ProcessingInventory processingInventory;
    private BasicEnergyStorage energy;

    public ElectricCrucibleTileEntity() {
        super(BlockRegistry.electricCrucibleBlock);
        this.inventoryIn = new BasicInventory(1, "insert").setListener(this);
        this.inventoryOut = new BasicInventory(6, "extract").setListener(this).setOutput();
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        this.processingInventory = new ProcessingInventory(5).setListener(this);
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
        super.update();
        doChemicalProcessUpdate(inventoryIn, inventoryOut, processingInventory, ChemicalProcessType.heat);
    }

    @Override
    protected boolean processingTick() {
        return tryAndExtractEnergy(energy, Config.energyConsumption);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        INBTWritable.readNBT(nbttagcompound, inventoryIn, inventoryOut, processingInventory, energy);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        return INBTWritable.writeNBT(super.writeToNBT(nbttagcompound), inventoryIn, inventoryOut, processingInventory, energy);
    }
}
