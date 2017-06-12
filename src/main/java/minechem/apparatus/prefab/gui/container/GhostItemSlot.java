package minechem.apparatus.prefab.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class GhostItemSlot extends Slot {
    private Supplier<ItemStack> currentStack;

    public GhostItemSlot(int xPosition, int yPosition, Supplier<ItemStack> currentStack) {
        super(null, 0, xPosition, yPosition);
        this.currentStack = currentStack;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack getStack() {
        return currentStack.get();
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public void putStack(ItemStack stack) {

    }

    @Override
    public void onSlotChanged() {

    }
}
