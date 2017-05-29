package minechem.item.prefab;

import minechem.apparatus.prefab.block.BasicBlock;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BasicItemBlock extends ItemBlock {

    public BasicItemBlock(BasicBlock block) {
        super(block);
        setRegistryName(block.getRegistryName());
    }

    public BasicItemBlock(BasicBlockContainer block) {
        super(block);
        setRegistryName(block.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
