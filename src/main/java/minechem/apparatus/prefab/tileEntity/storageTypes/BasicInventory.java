package minechem.apparatus.prefab.tileEntity.storageTypes;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.IChangeable;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Defines basic properties for TileEntities
 */
@SuppressWarnings("unchecked")
public class BasicInventory extends InventoryBasic implements INBTWritable {
    private IChangeable listener = IChangeable.NONE;
    private boolean sendUpdates = false;
    private boolean isOutput = false;

    public BasicInventory(int inventorySize) {
        this(inventorySize, "inventory");
    }

    public BasicInventory(int inventorySize, String inventoryName) {
        super(inventoryName, true, inventorySize);
    }

    public <T extends BasicInventory> T setListener(IChangeable changeable) {
        this.listener = changeable;
        return (T) this;
    }

    public <T extends BasicInventory> T sendUpdates() {
        this.sendUpdates = true;
        return (T) this;
    }

    public BasicInventory setOutput() {
        this.isOutput = true;
        return this;
    }

    public List<ItemStack> getAllStacks() {
        List<ItemStack> itemStacks = new LinkedList<>();
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getStackInSlot(i);
            if (!stack.isEmpty()) {
                itemStacks.add(stack);
            }
        }
        return itemStacks;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return !isOutput;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        this.listener.onChange(sendUpdates);
    }

    public IItemHandlerModifiable asCapability() {
        return new InvWrapper(this);
    }

    public static IItemHandlerModifiable asCapability(BasicInventory... others) {
        List<IItemHandlerModifiable> handlers = Arrays.stream(others).map(inv -> inv.asCapability()).collect(Collectors.toList());
        return new CombinedInvWrapper(handlers.toArray(new IItemHandlerModifiable[handlers.size()]));
    }

    @Override
    public void writeNBT(NBTTagCompound tagCompound) {
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getStackInSlot(i);
            NBTTagCompound tag = new NBTTagCompound();
            stack.writeToNBT(tag);
            nbttaglist.appendTag(tag);
        }
        tagCompound.setTag(Compendium.NBTTags.inventory + getName(), nbttaglist);
    }

    @Override
    public void readNBT(NBTTagCompound nbttagcompound) {
        NBTTagList nbttaglist = nbttagcompound.getTagList(Compendium.NBTTags.inventory + getName(), Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < getSizeInventory(); i++) {
            setInventorySlotContentNoUpdate(i, new ItemStack(nbttaglist.getCompoundTagAt(i)));
        }
    }

    public void setInventorySlotContentNoUpdate(int index, ItemStack stack) {
        this.inventoryContents.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    // Like the implementation in the item handler
    public ItemStack addItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        ItemStack stackInSlot = getStackInSlot(slot);

        int m;
        if (!stackInSlot.isEmpty())
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot))
                return stack;

            m = Math.min(stack.getMaxStackSize(), getInventoryStackLimit()) - stackInSlot.getCount();

            if (stack.getCount() <= m)
            {
                if (!simulate)
                {
                    ItemStack copy = stack.copy();
                    copy.grow(stackInSlot.getCount());
                    setInventorySlotContents(slot, copy);
                    markDirty();
                }

                return ItemStack.EMPTY;
            }
            else
            {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate)
                {
                    ItemStack copy = stack.splitStack(m);
                    copy.grow(stackInSlot.getCount());
                    setInventorySlotContents(slot, copy);
                    markDirty();
                    return stack;
                }
                else
                {
                    stack.shrink(m);
                    return stack;
                }
            }
        }
        else
        {
            m = Math.min(stack.getMaxStackSize(), getInventoryStackLimit());
            if (m < stack.getCount())
            {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate)
                {
                    setInventorySlotContents(slot, stack.splitStack(m));
                    markDirty();
                    return stack;
                }
                else
                {
                    stack.shrink(m);
                    return stack;
                }
            }
            else
            {
                if (!simulate)
                {
                    setInventorySlotContents(slot, stack);
                    markDirty();
                }
                return ItemStack.EMPTY;
            }
        }
    }
}
