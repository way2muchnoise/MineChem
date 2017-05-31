package minechem.apparatus.prefab.tileEntity.storageTypes;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTWritable {
    void writeToNBT(NBTTagCompound tagCompound);

    void readFromNBT(NBTTagCompound nbttagcompound);
}
