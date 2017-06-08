package minechem.item.prefab;

import minechem.Compendium;
import minechem.registry.CreativeTabRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Defines properties of a basic item
 */
public class BasicItem extends Item {
    public BasicItem(String itemName) {
        super();
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        setUnlocalizedName(itemName);
        setRegistryName(Compendium.Naming.id, itemName);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
