package minechem.apparatus.opticalMicroscope;

import minechem.apparatus.prefab.tileEntity.BasicTileEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import minechem.apparatus.prefab.tileEntity.storageTypes.INBTWritable;
import minechem.registry.BlockRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;

public class OpticalMicroscopeTileEntity extends BasicTileEntity
{
    private BasicInventory inventory;

    public OpticalMicroscopeTileEntity()
    {
        super(BlockRegistry.opticalMicroscope);
        this.inventory = new BasicInventory(1, getName()).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory.asCapability());
        registerDropableInventories(inventory);
    }

    public BasicInventory getInventory() {
        return inventory;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        return INBTWritable.writeToNBT(super.writeToNBT(nbttagcompound), inventory);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        INBTWritable.readFromNBT(nbttagcompound, inventory);
    }
}
