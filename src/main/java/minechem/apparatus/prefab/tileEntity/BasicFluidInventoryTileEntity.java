package minechem.apparatus.prefab.tileEntity;

import minechem.apparatus.prefab.tileEntity.storageTypes.BasicFluidTank;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

/**
 * Defines basic properties for TileEntities
 */
public abstract class BasicFluidInventoryTileEntity extends BaseTileEntity implements IInventory, IFluidHandler
{
    private BasicFluidTank fluidInventory;
    private BasicInventory inventory;

    public BasicFluidInventoryTileEntity(String name, int inventorySize, int fluidInventorySize)
    {
        super(name);
        inventory = new BasicInventory(inventorySize);
        fluidInventory = new BasicFluidTank(fluidInventorySize);
    }

    /**
     * Decrease the stack in a given slot by a given amount
     *
     * @param slot   the inventory slot
     * @param amount the amount to decrease the stack by
     * @return ItemStack the stack from the slot
     */
    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        return inventory.decrStackSize(slot, amount);
    }


    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain)
    {
        return fluidInventory.drain(resource, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        return fluidInventory.drain(maxDrain, doDrain);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        return fluidInventory.fill(resource, doFill);
    }

    @Override
    public String getName() {
        return inventory.getName();
    }

    /**
     * Get the inventory name
     *
     * @return the inventory name
     */
    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(inventory.getName());
    }

    /**
     * Get the stack size limit for the inventory, override if necessary
     *
     * @return 64
     */
    @Override
    public int getInventoryStackLimit()
    {
        return inventory.getInventoryStackLimit();
    }

    /**
     * Get the size of the inventory
     *
     * @return int size of the inventory object
     */
    @Override
    public int getSizeInventory()
    {
        return inventory.getSizeInventory();
    }

    /**
     * Get the item from a specific slot
     *
     * @param slot slot to get the item from
     * @return the itemstack from the slot
     */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return fluidInventory.getTankProperties();
    }

    /**
     * Check if the inventory has a custom name
     *
     * @return false
     */
    @Override
    public boolean hasCustomName()
    {
        return inventory.hasCustomName();
    }

    /**
     * Can an item be put into the slot
     *
     * @param slot  slot to check
     * @param stack itemstack to check
     * @return true
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return inventory.isItemValidForSlot(slot, stack);
    }

    /**
     * Check if the player can use the inventory
     *
     * @param entityPlayer the player object
     * @return boolean based on distance and tileEntity status
     */
    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer)
    {
        return inventory.isUsableByPlayer(entityPlayer, pos);
    }

    @Override
    public void markDirty()
    {
    }


    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return inventory.removeStackFromSlot(index);
    }

    @Override
    public void openInventory(EntityPlayer player) {
        inventory.openInventory(player);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        inventory.closeInventory(player);
    }

    @Override
    public int getField(int id) {
        return inventory.getField(id);
    }

    @Override
    public void setField(int id, int value) {
        inventory.setField(id, value);
    }

    @Override
    public int getFieldCount() {
        return inventory.getFieldCount();
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    /**
     * Read saved values from NBT
     *
     * @param nbttagcompound
     */
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);

        NBTTagList nbttaglist = nbttagcompound.getTagList(inventory.getName(), Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < inventory.getInventory().length; i++)
        {
            inventory.setInventorySlotContents(i, new ItemStack(nbttaglist.getCompoundTagAt(i)));
        }

    }

    /**
     * Set the inventory slot to a given itemstack
     *
     * @param slot  which slot should the itemstack go into
     * @param stack the stack to put into the slot
     */
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory.setInventorySlotContents(slot, stack);
    }

    /**
     * Save data to NBT
     *
     * @param nbttagcompound
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (ItemStack stack : inventory.getInventory())
        {
            if (stack != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                stack.writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            } else
            {
                nbttaglist.appendTag(new NBTTagCompound());
            }
        }
        nbttagcompound.setTag(inventory.getName(), nbttaglist);
        return nbttagcompound;
    }
}
