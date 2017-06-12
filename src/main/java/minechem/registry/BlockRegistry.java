package minechem.registry;

import minechem.Compendium;
import minechem.apparatus.centrifuge.CentrifugeBlock;
import minechem.apparatus.centrifuge.CentrifugeTileEntity;
import minechem.apparatus.electricCrucible.ElectricCrucibleBlock;
import minechem.apparatus.electricCrucible.ElectricCrucibleTileEntity;
import minechem.apparatus.electrolysis.ElectrolysisBlock;
import minechem.apparatus.electrolysis.ElectrolysisTileEntity;
import minechem.apparatus.itemPrinter.ItemPrinterBlock;
import minechem.apparatus.itemPrinter.ItemPrinterTileEntity;
import minechem.apparatus.molecularConstructor.MolecularConstructorBlock;
import minechem.apparatus.molecularConstructor.MolecularConstructorTileEntity;
import minechem.apparatus.opticalMicroscope.OpticalMicroscopeBlock;
import minechem.apparatus.opticalMicroscope.OpticalMicroscopeTileEntity;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.item.prefab.BasicItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class BlockRegistry
{
    public static OpticalMicroscopeBlock opticalMicroscope;
    public static ElectrolysisBlock electrolysisBlock;
    public static ElectricCrucibleBlock electricCrucibleBlock;
    public static CentrifugeBlock centrifugeBlock;
    public static MolecularConstructorBlock molecularConstructor;
    public static ItemPrinterBlock itemPrinter;

    private static List<BasicItemBlock> itemBlocks;

    public static void init(Side side)
    {
        itemBlocks = new LinkedList<>();

        opticalMicroscope = register(new OpticalMicroscopeBlock());
        register(OpticalMicroscopeTileEntity.class, Compendium.Naming.opticalMicroscope);

        electricCrucibleBlock = register(new ElectricCrucibleBlock());
        register(ElectricCrucibleTileEntity.class, Compendium.Naming.electricCrucible);

        centrifugeBlock = register(new CentrifugeBlock());
        register(CentrifugeTileEntity.class, Compendium.Naming.centrifuge);

        electrolysisBlock = register(new ElectrolysisBlock());
        register(ElectrolysisTileEntity.class, Compendium.Naming.electrolysis);

        molecularConstructor = register(new MolecularConstructorBlock());
        register(MolecularConstructorTileEntity.class, Compendium.Naming.molecularConstructor);

        itemPrinter = register(new ItemPrinterBlock());
        register(ItemPrinterTileEntity.class, Compendium.Naming.itemPrinter);

        if (side == Side.CLIENT) {
            initModels();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        registerItemBlockRenderer(OpticalMicroscopeTileEntity.class, opticalMicroscope);
        registerItemBlockRenderer(ElectricCrucibleTileEntity.class, electricCrucibleBlock);
        registerItemBlockRenderer(CentrifugeTileEntity.class, centrifugeBlock);
        registerItemBlockRenderer(ElectrolysisTileEntity.class, electrolysisBlock);

        for (BasicItemBlock itemBlock : itemBlocks) {
            itemBlock.initModel();
        }

        itemBlocks = null;
    }

    private static <T extends BasicBlockContainer> T register(T block) {
        GameRegistry.register(block);
        BasicItemBlock itemBlock = new BasicItemBlock(block);
        GameRegistry.register(itemBlock);
        itemBlocks.add(itemBlock);
        return block;
    }

    private static void register(Class<? extends TileEntity> clazz, String name) {
        GameRegistry.registerTileEntity(clazz, Compendium.Naming.id + ":" + name);
    }

    private static void registerItemBlockRenderer(Class<? extends TileEntity> clazz, Block block) {
        Item item = Item.getItemFromBlock(block);
        ForgeHooksClient.registerTESRItemStack(item, 0, clazz);
    }
}
