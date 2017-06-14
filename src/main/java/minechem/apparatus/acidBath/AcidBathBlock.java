package minechem.apparatus.acidBath;

import minechem.Compendium;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.handler.GuiHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AcidBathBlock extends BasicBlockContainer {

    public AcidBathBlock() {
        super(Compendium.Naming.acidBath, Material.ANVIL, SoundType.METAL);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new AcidBathTileEntity();
    }

    @Override
    public int getGuiId() {
        return GuiHandler.ACID_BATH;
    }
}
