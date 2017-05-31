package minechem.apparatus.prefab.tileEntity.storageTypes;

import minechem.apparatus.prefab.tileEntity.IChangeable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class BasicEnergyStorage extends EnergyStorage implements INBTWritable {
    private static final String prefix = "minechem:basicenergy";

    private IChangeable listener = new IChangeable.NoListener();

    public BasicEnergyStorage(int capacity) {
        super(capacity);
    }

    public BasicEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public BasicEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public BasicEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public BasicEnergyStorage setListener(IChangeable changeable) {
        this.listener = changeable;
        return this;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if (!simulate && extracted > 0) this.listener.onChange();
        return extracted;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = super.receiveEnergy(maxReceive, simulate);
        if (!simulate && received > 0) this.listener.onChange();
        return received;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        NBTTagCompound energyTag = new NBTTagCompound();
        energyTag.setInteger("energy", energy);
        energyTag.setInteger("capacity", capacity);
        energyTag.setInteger("maxExtract", maxExtract);
        energyTag.setInteger("maxReceive", maxReceive);
        tagCompound.setTag(prefix, energyTag);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        NBTTagCompound energyTag = nbttagcompound.getCompoundTag(prefix);
        energy = energyTag.getInteger("energy");
        capacity = energyTag.getInteger("capacity");
        maxExtract = energyTag.getInteger("maxExtract");
        maxReceive = energyTag.getInteger("maxReceive");
    }
}
