package minechem.apparatus.tier1.opticalMicroscope;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BasicInventoryTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class OpticalMicroscopeTileEntity extends BasicInventoryTileEntity
{

    public OpticalMicroscopeTileEntity()
    {
        super(Compendium.Naming.opticalMicroscope, 1);
    }

    @Override
    public String getName()
    {
        return Compendium.Naming.opticalMicroscope;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public void openInventory(EntityPlayer player) {

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

    }
}
