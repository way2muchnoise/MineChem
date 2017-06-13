package minechem.breakdown;

import minechem.api.breakdown.IReverseRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.stream.Collectors;

public class WrappedShapelessRecipe implements IReverseRecipe {
    private List<ItemStack> inputs;
    private ItemStack output;

    public WrappedShapelessRecipe(ShapelessRecipes recipe) {
        inputs = recipe.recipeItems.stream().map(ItemStack::copy).collect(Collectors.toList());
        output = recipe.getRecipeOutput().copy();
    }

    @Override
    public boolean canOutput(final ItemStack stack) {
        return OreDictionary.itemMatches(stack, output, false);
    }

    @Override
    public List<ItemStack> getInputs(ItemStack stack) {
        return inputs;
    }

    @Override
    public int getOutputAmount(ItemStack stack) {
        return output.getCount();
    }
}
