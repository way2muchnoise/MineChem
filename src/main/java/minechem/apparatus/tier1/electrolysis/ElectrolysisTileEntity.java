package minechem.apparatus.tier1.electrolysis;

import minechem.apparatus.prefab.tileEntity.BaseTileEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicEnergyStorage;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import minechem.chemical.ChemicalBase;
import minechem.item.chemical.ChemicalItem;
import minechem.registry.BlockRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

public class ElectrolysisTileEntity extends BaseTileEntity
{
    public static final byte LEFT_SIDE = 0;
    public static final byte RIGHT_SIDE = 1;

    private BasicInventory inventory;
    private BasicEnergyStorage energy;

    public ElectrolysisTileEntity()
    {
        super(BlockRegistry.electrolysisBlock);
        this.inventory = new BasicInventory(2, getName()).setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory.asCapability());
        attachCapability(CapabilityEnergy.ENERGY, this.energy);
    }

    public byte addItem(ItemStack chemicalItemStack)
    {
        if (!chemicalItemStack.isEmpty()&& chemicalItemStack.getItem() instanceof ChemicalItem)
        {
            if (inventory.getStackInSlot(LEFT_SIDE).isEmpty())
            {
                inventory.setInventorySlotContents(LEFT_SIDE, chemicalItemStack);
                return LEFT_SIDE;
            } else if (inventory.getStackInSlot(RIGHT_SIDE).isEmpty())
            {
                inventory.setInventorySlotContents(RIGHT_SIDE, chemicalItemStack);
                return RIGHT_SIDE;
            }
        }
        return -1;
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
            inventory.setInventorySlotContents(LEFT_SIDE, chemicalItemStack);
        }
        if (side == RIGHT_SIDE)
        {
            inventory.setInventorySlotContents(RIGHT_SIDE, chemicalItemStack);
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
            if (!inventory.getStackInSlot(LEFT_SIDE).isEmpty())
            {
                ChemicalItem chemical = (ChemicalItem) inventory.getStackInSlot(1).getItem();
                inventory.decrStackSize(LEFT_SIDE, 1);
                return chemical;
            }
        }
        if (side == RIGHT_SIDE)
        {
            if (!inventory.getStackInSlot(RIGHT_SIDE).isEmpty())
            {
                ChemicalItem chemical = (ChemicalItem) inventory.getStackInSlot(0).getItem();
                inventory.decrStackSize(RIGHT_SIDE, 1);
                return chemical;
            }
        }
        return null;
    }

    public boolean hasLeftTube() {
        return !inventory.getStackInSlot(LEFT_SIDE).isEmpty();
    }

    public boolean hasRightTube() {
        return !inventory.getStackInSlot(RIGHT_SIDE).isEmpty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        inventory.writeToNBT(nbttagcompound);
        energy.writeToNBT(nbttagcompound);
        return nbttagcompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        inventory.readFromNBT(nbttagcompound);
        energy.readFromNBT(nbttagcompound);
    }
}
