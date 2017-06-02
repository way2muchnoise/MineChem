package minechem.apparatus.prefab.tileEntity.storageTypes;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTWritable {
    void writeToNBT(NBTTagCompound tagCompound);

    void readFromNBT(NBTTagCompound nbttagcompound);

    static NBTTagCompound writeToNBT(NBTTagCompound tagCompound, INBTWritable... writables) {
        for (INBTWritable writable : writables) {
            writable.writeToNBT(tagCompound);
        }
        return tagCompound;
    }

    static void readFromNBT(NBTTagCompound tagCompound, INBTWritable... writables) {
        for (INBTWritable writable : writables) {
            writable.readFromNBT(tagCompound);
        }
    }
}
