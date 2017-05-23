package minechem.apparatus.prefab.tileEntity.storageTypes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.Arrays;

/**
 * Defines basic properties for TileEntities
 */
public class BasicInventory implements IInventory
{
    private ItemStack[] inventory;
    private String inventoryName;

    public BasicInventory(int inventorySize)
    {
        this(inventorySize, "basicInventory");
    }

    public BasicInventory(int inventorySize, String inventoryName)
    {
        inventory = new ItemStack[inventorySize];
        Arrays.fill(inventory, ItemStack.EMPTY);
        this.inventoryName = inventoryName;
    }

    @Override
    public void closeInventory(EntityPlayer player) {

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
        inventory[slot].shrink(amount);
        return inventory[slot];
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = inventory[index];
        inventory[index] = ItemStack.EMPTY;
        return stack;
    }

    /**
     * Get the inventory name
     *
     * @return the inventory name
     */
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(inventoryName);
    }

    /**
     * Get the stack size limit for the inventory, override if necessary
     *
     * @return 64
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Get the size of the inventory
     *
     * @return int size of the inventory object
     */
    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
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
        return inventory[slot];
    }

    @Override
    public String getName() {
        return inventoryName;
    }

    /**
     * Check if the inventory has a custom name
     *
     * @return false
     */
    @Override
    public boolean hasCustomName()
    {
        return false;
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
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = ItemStack.EMPTY;
        }
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
        return true;
    }

    public boolean isUsableByPlayer(EntityPlayer entityPlayer, BlockPos pos)
    {
        return entityPlayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void markDirty()
    {
    }

    @Override
    public void openInventory(EntityPlayer player) {

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
        if (inventory.length >= slot)
        {
            inventory[slot] = stack;
            markDirty();
        }
    }

    public ItemStack[] getInventory()
    {
        return this.inventory;
    }
}
