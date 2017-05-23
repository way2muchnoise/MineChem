package minechem.item.prefab;

import minechem.registry.CreativeTabRegistry;
import net.minecraft.item.Item;

/**
 * Defines properties of a basic item
 */
public abstract class BasicItem extends Item
{
    public BasicItem()
    {
        this("basicItem");
    }

    public BasicItem(String itemName)
    {
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        setUnlocalizedName(itemName);
        //setTextureName(itemName + "Icon");
    }
}
