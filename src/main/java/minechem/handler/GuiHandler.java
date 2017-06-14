package minechem.handler;

import minechem.apparatus.acidBath.AcidBathContainer;
import minechem.apparatus.acidBath.AcidBathGui;
import minechem.apparatus.acidBath.AcidBathTileEntity;
import minechem.apparatus.centrifuge.CentrifugeContainer;
import minechem.apparatus.centrifuge.CentrifugeGUI;
import minechem.apparatus.centrifuge.CentrifugeTileEntity;
import minechem.apparatus.electricCrucible.ElectricCrucibleContainer;
import minechem.apparatus.electricCrucible.ElectricCrucibleGUI;
import minechem.apparatus.electricCrucible.ElectricCrucibleTileEntity;
import minechem.apparatus.electrolysis.ElectrolysisContainer;
import minechem.apparatus.electrolysis.ElectrolysisGUI;
import minechem.apparatus.electrolysis.ElectrolysisTileEntity;
import minechem.apparatus.itemPrinter.ItemPrinterContainer;
import minechem.apparatus.itemPrinter.ItemPrinterGUI;
import minechem.apparatus.itemPrinter.ItemPrinterTileEntity;
import minechem.apparatus.molecularConstructor.MolecularConstructorContainer;
import minechem.apparatus.molecularConstructor.MolecularConstructorGUI;
import minechem.apparatus.molecularConstructor.MolecularConstructorTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.IGuiHandler;
import minechem.apparatus.opticalMicroscope.OpticalMicroscopeContainer;
import minechem.apparatus.opticalMicroscope.OpticalMicroscopeGUI;
import minechem.apparatus.opticalMicroscope.OpticalMicroscopeTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    public static final int MICROSCOPE = 0;
    public static final int CENTRIFUGE = 1;
    public static final int ELECTROLYSIS = 2;
    public static final int ELECTRIC_CRUCIBLE = 3;
    public static final int ACID_BATH = 4;
    public static final int MOLECULAR_CONSTRUCTOR = 5;
    public static final int ITEM_PRINTER = 6;

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
                case ACID_BATH:
                    return new AcidBathGui(player.inventory, (AcidBathTileEntity) tileEntity);
                case MOLECULAR_CONSTRUCTOR:
                    return new MolecularConstructorGUI(player.inventory, (MolecularConstructorTileEntity) tileEntity);
                case ITEM_PRINTER:
                    return new ItemPrinterGUI(player.inventory, (ItemPrinterTileEntity) tileEntity);
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
                case ACID_BATH:
                    return new AcidBathContainer(player.inventory, (AcidBathTileEntity) tileEntity);
                case MOLECULAR_CONSTRUCTOR:
                    return new MolecularConstructorContainer(player.inventory, (MolecularConstructorTileEntity) tileEntity);
                case ITEM_PRINTER:
                    return new ItemPrinterContainer(player.inventory, (ItemPrinterTileEntity) tileEntity);
            }
        }
        return null;
    }
}
