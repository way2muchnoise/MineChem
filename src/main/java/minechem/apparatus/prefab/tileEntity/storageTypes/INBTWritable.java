package minechem.apparatus.prefab.tileEntity.storageTypes;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTWritable {
    void writeNBT(NBTTagCompound tagCompound);

    void readNBT(NBTTagCompound nbttagcompound);

    static NBTTagCompound writeNBT(NBTTagCompound tagCompound, INBTWritable... writables) {
        for (INBTWritable writable : writables) {
            writable.writeNBT(tagCompound);
        }
        return tagCompound;
    }

    static void readNBT(NBTTagCompound tagCompound, INBTWritable... writables) {
        for (INBTWritable writable : writables) {
            writable.readNBT(tagCompound);
        }
    }
}
