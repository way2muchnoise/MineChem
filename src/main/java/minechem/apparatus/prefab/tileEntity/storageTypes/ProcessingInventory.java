package minechem.apparatus.prefab.tileEntity.storageTypes;

import minechem.apparatus.prefab.tileEntity.IChangeable;
import net.minecraft.nbt.NBTTagCompound;

public class ProcessingInventory extends BasicInventory implements IUpdateable {
    private Timer timer;

    public ProcessingInventory(int duration) {
        this(1, "internal", duration);
    }

    public ProcessingInventory(int inventorySize, String inventoryName, int duration) {
        super(inventorySize, inventoryName);
        this.timer = new Timer(duration, inventoryName);
    }

    @Override
    public ProcessingInventory setListener(IChangeable changeable) {
        super.setListener(changeable);
        timer.setListener(changeable);
        return this;
    }

    @Override
    public Timer getTimer() {
        return this.timer;
    }

    @Override
    public void writeNBT(NBTTagCompound tagCompound) {
        super.writeNBT(tagCompound);
        timer.writeNBT(tagCompound);
    }

    @Override
    public void readNBT(NBTTagCompound nbttagcompound) {
        super.readNBT(nbttagcompound);
        timer.readNBT(nbttagcompound);
    }
}
