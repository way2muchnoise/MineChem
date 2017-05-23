package minechem.apparatus.prefab.tileEntity;

import net.minecraft.tileentity.TileEntity;

public abstract class BaseTileEntity extends TileEntity
{
    protected String name;

    public BaseTileEntity(String name) {
        this.name = name;
    }

    @Override
    public int getBlockMetadata()
    {
        return world != null ? super.getBlockMetadata() : 0;
    }
}
