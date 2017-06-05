package minechem.breakdown;

import minechem.api.IReverseRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class WrappedShapedRecipe implements IReverseRecipe {
    private List<ItemStack> inputs;
    private ItemStack output;

    public WrappedShapedRecipe(ShapedRecipes recipe) {
        inputs = new ArrayList<>(recipe.recipeItems.length);
        for (ItemStack stack : recipe.recipeItems) {
            if (!stack.isEmpty()) {
                inputs.add(stack.copy());
            }
        }
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
