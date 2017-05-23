package minechem.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import minechem.Compendium;
import minechem.apparatus.tier1.centrifuge.CentrifugeBlock;
import minechem.apparatus.tier1.centrifuge.CentrifugeTileEntity;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleBlock;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleTileEntity;
import minechem.apparatus.tier1.electrolysis.ElectrolysisBlock;
import minechem.apparatus.tier1.electrolysis.ElectrolysisTileEntity;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeBlock;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeTileEntity;

public class BlockRegistry
{
    public static OpticalMicroscopeBlock opticalMicroscope;
    public static ElectrolysisBlock electrolysisBlock;
    public static ElectricCrucibleBlock electricCrucibleBlock;
    public static CentrifugeBlock centrifugeBlock;

    public static void init()
    {
        opticalMicroscope = new OpticalMicroscopeBlock();
        //GameRegistry.registerBlock(opticalMicroscope, opticalMicroscope.getUnlocalizedName());
        GameRegistry.registerTileEntity(OpticalMicroscopeTileEntity.class, Compendium.Naming.opticalMicroscope + "TileEntity");

        electricCrucibleBlock = new ElectricCrucibleBlock();
        //GameRegistry.registerBlock(electricCrucibleBlock, electricCrucibleBlock.getUnlocalizedName());
        GameRegistry.registerTileEntity(ElectricCrucibleTileEntity.class, Compendium.Naming.electricCrucible + "TileEntity");

        centrifugeBlock = new CentrifugeBlock();
        //GameRegistry.registerBlock(centrifugeBlock, centrifugeBlock.getUnlocalizedName());
        GameRegistry.registerTileEntity(CentrifugeTileEntity.class, Compendium.Naming.centrifuge + "TileEntity");

        electrolysisBlock = new ElectrolysisBlock();
        //GameRegistry.registerBlock(electrolysisBlock, electrolysisBlock.getUnlocalizedName());
        GameRegistry.registerTileEntity(ElectrolysisTileEntity.class, Compendium.Naming.electrolysis + "TileEntity");
    }
}
