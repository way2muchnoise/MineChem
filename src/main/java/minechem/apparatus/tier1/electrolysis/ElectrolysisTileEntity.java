package minechem.apparatus.tier1.electrolysis;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BasicFluidInventoryTileEntity;
import minechem.chemical.ChemicalBase;
import minechem.item.chemical.ChemicalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ElectrolysisTileEntity extends BasicFluidInventoryTileEntity
{
    public static final byte LEFT_SIDE = 0;
    public static final byte RIGHT_SIDE = 1;

    public ElectrolysisTileEntity()
    {
        super(Compendium.Naming.electrolysis, 2, 3);
    }

    public byte addItem(ItemStack chemicalItemStack)
    {
        if (!chemicalItemStack.isEmpty()&& chemicalItemStack.getItem() instanceof ChemicalItem)
        {
            if (this.getStackInSlot(LEFT_SIDE) == null)
            {
                this.setInventorySlotContents(LEFT_SIDE, chemicalItemStack);
                return LEFT_SIDE;
            } else if (this.getStackInSlot(RIGHT_SIDE) == null)
            {
                this.setInventorySlotContents(RIGHT_SIDE, chemicalItemStack);
                return RIGHT_SIDE;
            }
        }
        return -1;
    }

    @Override
    public String getName() {
        return Compendium.Naming.electrolysis;
    }

    /**
     * Fill a specific side of the TileEntity with a ChemicalBase
     *
     * @param chemicalBase
     * @param side         0 is left, 1 is right
     */
    public void fillWithChemicalBase(ChemicalBase chemicalBase, byte side)
    {
        ItemStack chemicalItemStack = ChemicalItem.getItemStackForChemical(chemicalBase);
        if (side == LEFT_SIDE)
        {
            this.setInventorySlotContents(LEFT_SIDE, chemicalItemStack);
        }
        if (side == RIGHT_SIDE)
        {
            this.setInventorySlotContents(RIGHT_SIDE, chemicalItemStack);
        }
    }

    /**
     * Remove a ChemicalItem from a side
     *
     * @param side 0 is left, 1 is right
     * @return
     */
    public ChemicalItem removeItem(int side)
    {
        if (side == LEFT_SIDE)
        {
            if (this.getStackInSlot(LEFT_SIDE) != null && !this.getStackInSlot(LEFT_SIDE).isEmpty())
            {
                ChemicalItem chemical = (ChemicalItem) getStackInSlot(1).getItem();
                this.decrStackSize(LEFT_SIDE, 1);
                return chemical;
            }
        }
        if (side == RIGHT_SIDE)
        {
            if (this.getStackInSlot(RIGHT_SIDE) != null && !this.getStackInSlot(RIGHT_SIDE).isEmpty())
            {
                ChemicalItem chemical = (ChemicalItem) getStackInSlot(0).getItem();
                this.decrStackSize(RIGHT_SIDE, 1);
                return chemical;
            }
        }
        return null;
    }

    public boolean hasLeftTube() {
        return !getStackInSlot(LEFT_SIDE).isEmpty();
    }

    public ChemicalItem getLeftTube()
    {
        ItemStack itemStack = decrStackSize(LEFT_SIDE, 1);
        if (itemStack != null)
        {
            if (itemStack.getItem() instanceof ChemicalItem)
            {
                return (ChemicalItem) itemStack.getItem();
            }
        }
        return null;
    }

    public boolean hasRightTube() {
        return !getStackInSlot(RIGHT_SIDE).isEmpty();
    }

    public ChemicalItem getRightTube()
    {
        ItemStack itemStack = decrStackSize(RIGHT_SIDE, 1);
        if (itemStack != null)
        {
            if (itemStack.getItem() instanceof ChemicalItem)
            {
                return (ChemicalItem) itemStack.getItem();
            }
        }
        return null;
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
        return nbttagcompound;
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
    }
}
