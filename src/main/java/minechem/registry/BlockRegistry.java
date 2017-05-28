package minechem.registry;

import minechem.apparatus.prefab.block.BasicBlock;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.item.prefab.BasicItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
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
import net.minecraftforge.fml.relauncher.Side;

public class BlockRegistry
{
    public static OpticalMicroscopeBlock opticalMicroscope;
    public static ElectrolysisBlock electrolysisBlock;
    public static ElectricCrucibleBlock electricCrucibleBlock;
    public static CentrifugeBlock centrifugeBlock;

    public static void init(Side side)
    {
        opticalMicroscope = new OpticalMicroscopeBlock();
        register(opticalMicroscope);
        register(OpticalMicroscopeTileEntity.class, Compendium.Naming.opticalMicroscope);

        electricCrucibleBlock = new ElectricCrucibleBlock();
        register(electricCrucibleBlock);
        register(ElectricCrucibleTileEntity.class, Compendium.Naming.electricCrucible);

        centrifugeBlock = new CentrifugeBlock();
        register(centrifugeBlock);
        register(CentrifugeTileEntity.class, Compendium.Naming.centrifuge);

        electrolysisBlock = new ElectrolysisBlock();
        register(electrolysisBlock);
        register(ElectrolysisTileEntity.class, Compendium.Naming.electrolysis);
    }

    private static void register(BasicBlock block) {
        GameRegistry.<Block>register(block);
        GameRegistry.<Item>register(new ItemBlock(block));
    }

    private static void register(BasicBlockContainer block) {
        GameRegistry.<Block>register(block);
        GameRegistry.<Item>register(new BasicItemBlock(block));
    }

    private static void register(Class<? extends TileEntity> clazz, String name) {
        GameRegistry.registerTileEntity(clazz, Compendium.Naming.id + ":" + name);
    }
}
