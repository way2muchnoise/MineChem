package minechem.apparatus.itemPrinter;

import minechem.Compendium;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.handler.GuiHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemPrinterBlock extends BasicBlockContainer {
    public ItemPrinterBlock() {
        super(Compendium.Naming.itemPrinter, Material.ANVIL, SoundType.METAL);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new ItemPrinterTileEntity();
    }

    @Override
    public int getGuiId() {
        return GuiHandler.ITEM_PRINTER;
    }
}
