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
}
