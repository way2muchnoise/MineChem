package minechem.handler;

import minechem.apparatus.tier1.centrifuge.CentrifugeContainer;
import minechem.apparatus.tier1.centrifuge.CentrifugeGUI;
import minechem.apparatus.tier1.centrifuge.CentrifugeTileEntity;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleContainer;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleGUI;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleTileEntity;
import minechem.apparatus.tier1.electrolysis.ElectrolysisContainer;
import minechem.apparatus.tier1.electrolysis.ElectrolysisGUI;
import minechem.apparatus.tier1.electrolysis.ElectrolysisTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.IGuiHandler;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeContainer;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeGUI;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    public static final int MICROSCOPE = 0;
    public static final int CENTRIFUGE = 1;
    public static final int ELECTROLYSIS = 2;
    public static final int ELECTRIC_CRUCIBLE = 3;

    /**
     * Get the GUI container object for the server
     *
     * @param ID     GUI element ID, unused
     * @param player Player entity
     * @param world  World object
     * @param x      World x coordinate
     * @param y      World y coordinate
     * @param z      World z coordinate
     * @return GUI object for the TileEntity
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity != null)
        {
            switch (ID) {
                case MICROSCOPE:
                    return new OpticalMicroscopeGUI(player.inventory, (OpticalMicroscopeTileEntity) tileEntity);
                case CENTRIFUGE:
                    return new CentrifugeGUI(player.inventory, (CentrifugeTileEntity) tileEntity);
                case ELECTROLYSIS:
                    return new ElectrolysisGUI(player.inventory, (ElectrolysisTileEntity) tileEntity);
                case ELECTRIC_CRUCIBLE:
                    return new ElectricCrucibleGUI(player.inventory, (ElectricCrucibleTileEntity) tileEntity);
            }
        }
        return null;
    }

    /**
     * Get the GUI container object for the server
     *
     * @param ID     GUI element ID, unused
     * @param player Player entity
     * @param world  World object
     * @param x      World x coordinate
     * @param y      World y coordinate
     * @param z      World z coordinate
     * @return Container object for the TileEntity
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity != null)
        {
            switch (ID) {
                case MICROSCOPE:
                    return new OpticalMicroscopeContainer(player.inventory, (OpticalMicroscopeTileEntity) tileEntity);
                case CENTRIFUGE:
                    return new CentrifugeContainer(player.inventory, (CentrifugeTileEntity) tileEntity);
                case ELECTROLYSIS:
                    return new ElectrolysisContainer(player.inventory, (ElectrolysisTileEntity) tileEntity);
                case ELECTRIC_CRUCIBLE:
                    return new ElectricCrucibleContainer(player.inventory, (ElectricCrucibleTileEntity) tileEntity);
            }
        }
        return null;
    }
}
