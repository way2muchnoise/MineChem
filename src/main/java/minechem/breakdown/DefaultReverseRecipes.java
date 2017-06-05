package minechem.breakdown;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class DefaultReverseRecipes {
    public static void init() {
        ReverseRecipeRegistry.getInstance().registerFactory(ShapedOreRecipe.class, WrappedShapedOreRecipe::new);
        ReverseRecipeRegistry.getInstance().registerFactory(ShapedRecipes.class, WrappedShapedRecipe::new);
        ReverseRecipeRegistry.getInstance().registerFactory(ShapelessOreRecipe.class, WrappedShapelessOreRecipe::new);
        ReverseRecipeRegistry.getInstance().registerFactory(ShapelessRecipes.class, WrappedShapelessRecipe::new);

        for (IRecipe recipe : CraftingManager.getInstance().getRecipeList()) {
            ReverseRecipeRegistry.getInstance().registerRecipe(recipe);
        }

        WrappedFurnaceRecipes.register();
    }
}
