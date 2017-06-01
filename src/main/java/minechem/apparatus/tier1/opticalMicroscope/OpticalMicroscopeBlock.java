package minechem.apparatus.tier1.opticalMicroscope;

import minechem.Compendium;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.handler.GuiHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class OpticalMicroscopeBlock extends BasicBlockContainer
{
    public OpticalMicroscopeBlock()
    {
        super(Compendium.Naming.opticalMicroscope, Material.IRON, SoundType.METAL);
        setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 1.0F, 0.8F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new OpticalMicroscopeTileEntity();
    }

    @Override
    public int getGuiId() {
        return GuiHandler.MICROSCOPE;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
