package minechem.registry;

import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class RecipeRegistry
{

    private static final RecipeRegistry recipes = new RecipeRegistry();

    public static RecipeRegistry getInstance()
    {
        return recipes;
    }

    public void init()
    {
    }

    public List<ItemStack> getBreakdown(ItemStack itemStack) {
        return Collections.singletonList(itemStack);
    }
}
