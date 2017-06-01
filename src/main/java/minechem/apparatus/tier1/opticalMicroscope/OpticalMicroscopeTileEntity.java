package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.tileEntity.BaseTileEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import minechem.registry.BlockRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;

public class OpticalMicroscopeTileEntity extends BaseTileEntity
{
    private BasicInventory inventory;

    public OpticalMicroscopeTileEntity()
    {
        super(BlockRegistry.opticalMicroscope);
        this.inventory = new BasicInventory(1, getName()).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory.asCapability());
    }

    public BasicInventory getInventory() {
        return inventory;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        inventory.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.readFromNBT(compound);
    }
}
