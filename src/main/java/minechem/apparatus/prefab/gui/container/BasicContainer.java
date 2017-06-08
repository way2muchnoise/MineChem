package minechem.apparatus.prefab.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Basic container abstract class for extending
 */
public abstract class BasicContainer extends Container
{
    private InventoryPlayer inventoryPlayer;

    public EntityPlayer getPlayer() {
        return inventoryPlayer.player;
    }

    /**
     * Add the player's inventory slots to the GUI
     *
     * @param inventoryPlayer the player's inventory
     */
    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        this.inventoryPlayer = inventoryPlayer;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    public InventoryPlayer getInventoryPlayer()
    {
        return inventoryPlayer;
    }

    /**
     * Determine if the player can interact with the container
     *
     * @param entityPlayer the player entity
     * @return boolean
     */
    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true;
    }

    /**
     * Shift click transfer mechanic
     *
     * @param player     the player object
     * @param slotNumber which slot
     * @return itemstack from the slot
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
    {
        Slot slot = inventorySlots.get(slotNumber);
        ItemStack stack = slot.getStack();

        if (!stack.isEmpty())
        {
            if (slotNumber < player.inventory.mainInventory.size())
            {
                if (!mergeItemStack(stack, player.inventory.mainInventory.size(), inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            } else
            {
                if (!mergeItemStack(stack, 0, player.inventory.mainInventory.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            } else
            {
                slot.onSlotChanged();
            }
        }
        return stack;
    }
}
