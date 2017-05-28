package minechem.item.prefab;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class BasicItemBlock extends ItemBlock {
    public BasicItemBlock(Block block) {
        super(block);
        setRegistryName(block.getRegistryName());
    }
}
