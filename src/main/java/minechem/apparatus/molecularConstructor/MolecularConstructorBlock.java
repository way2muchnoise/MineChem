package minechem.apparatus.molecularConstructor;

import minechem.Compendium;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.handler.GuiHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MolecularConstructorBlock extends BasicBlockContainer {

    public MolecularConstructorBlock() {
        super(Compendium.Naming.molecularConstructor, Material.ANVIL, SoundType.METAL);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new MolecularConstructorTileEntity();
    }

    @Override
    public int getGuiId() {
        return GuiHandler.MOLECULAR_CONSTRUCTOR;
    }
}
