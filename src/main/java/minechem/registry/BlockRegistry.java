package minechem.registry;

import minechem.apparatus.prefab.block.BasicBlock;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.item.prefab.BasicItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.ForgeHooksClient;
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
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class BlockRegistry
{
    public static OpticalMicroscopeBlock opticalMicroscope;
    public static ElectrolysisBlock electrolysisBlock;
    public static ElectricCrucibleBlock electricCrucibleBlock;
    public static CentrifugeBlock centrifugeBlock;

    private static List<BasicItemBlock> itemBlocks;

    public static void init(Side side)
    {
        itemBlocks = new LinkedList<>();

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

        if (side == Side.CLIENT) {
            // TODO: doesn't work, dunno what to do
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

    private static void register(BasicBlock block) {
        GameRegistry.register(block);
        BasicItemBlock itemBlock = new BasicItemBlock(block);
        GameRegistry.register(itemBlock);
        itemBlocks.add(itemBlock);
    }

    private static void register(BasicBlockContainer block) {
        GameRegistry.register(block);
        BasicItemBlock itemBlock = new BasicItemBlock(block);
        GameRegistry.register(itemBlock);
        itemBlocks.add(itemBlock);
    }

    private static void register(Class<? extends TileEntity> clazz, String name) {
        GameRegistry.registerTileEntity(clazz, Compendium.Naming.id + ":" + name);
    }

    private static void registerItemBlockRenderer(Class<? extends TileEntity> clazz, Block block) {
        Item item = Item.getItemFromBlock(block);
        ForgeHooksClient.registerTESRItemStack(item, 0, clazz);
    }
}
