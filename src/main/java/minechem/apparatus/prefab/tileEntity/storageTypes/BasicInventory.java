package minechem.apparatus.prefab.tileEntity.storageTypes;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

/**
 * Defines basic properties for TileEntities
 */
public class BasicInventory extends InventoryBasic
{
    private static final String prefix = "minechem:basicinv:";

    public BasicInventory(int inventorySize)
    {
        this(inventorySize, "basicInventory");
    }

    public BasicInventory(int inventorySize, String inventoryName)
    {
        super(inventoryName, true, inventorySize);
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack stack = getStackInSlot(i);
            NBTTagCompound tag = new NBTTagCompound();
            stack.writeToNBT(tag);
            nbttaglist.appendTag(tag);
        }
        tagCompound.setTag(prefix + getName(), nbttaglist);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        NBTTagList nbttaglist = nbttagcompound.getTagList( prefix + getName(), Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < getSizeInventory(); i++)
        {
            setInventorySlotContents(i, new ItemStack(nbttaglist.getCompoundTagAt(i)));
        }
    }
}
