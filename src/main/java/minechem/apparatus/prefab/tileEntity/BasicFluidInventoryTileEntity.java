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
public abstract class BasicFluidInventoryTileEntity extends BasicInventoryTileEntity implements IFluidHandler
{
    private BasicFluidTank fluidInventory;

    public BasicFluidInventoryTileEntity(String name, int inventorySize, int fluidInventorySize)
    {
        super(name, inventorySize);
        fluidInventory = new BasicFluidTank(fluidInventorySize);
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
    public IFluidTankProperties[] getTankProperties() {
        return fluidInventory.getTankProperties();
    }

    @Override
    public void markDirty()
    {
        super.markDirty();
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
        fluidInventory.readFromNBT(nbttagcompound);
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
        fluidInventory.writeToNBT(nbttagcompound);
        return nbttagcompound;
    }
}
