package minechem.apparatus.tier1.centrifuge;

import minechem.Compendium;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *
 * @author jakimfett
 */
public class CentrifugeBlock extends BasicBlockContainer
{
    public CentrifugeBlock()
    {
        super(Compendium.Naming.centrifuge, Material.ANVIL, SoundType.METAL);

        //setBlockBounds(0.18F, 0F, 0.18F, 0.82F, 0.46F, 0.82F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new CentrifugeTileEntity();
    }
}
