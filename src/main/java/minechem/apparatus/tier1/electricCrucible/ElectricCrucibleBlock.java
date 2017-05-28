package minechem.apparatus.tier1.electricCrucible;

import minechem.Compendium;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 *
 * @author jakimfett
 */
public class ElectricCrucibleBlock extends BasicBlockContainer
{
    public ElectricCrucibleBlock()
    {
        super(Compendium.Naming.electricCrucible, Material.ANVIL, SoundType.METAL);
        setBlockBounds(0.12F, 0F, 0.12F, 0.88F, 0.93F, 0.88F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new ElectricCrucibleTileEntity();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
}
