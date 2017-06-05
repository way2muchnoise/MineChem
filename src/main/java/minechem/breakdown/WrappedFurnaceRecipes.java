package minechem.breakdown;

import minechem.api.IReverseRecipe;
import minechem.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WrappedFurnaceRecipes implements IReverseRecipe {
    private ItemStack input, output;

    public WrappedFurnaceRecipes(ItemStack input, ItemStack output) {
        this.input = input.copy();
        this.output = output.copy();
    }

    @Override
    public boolean canOutput(final ItemStack stack) {
        return OreDictionary.itemMatches(stack, output, false);
    }

    @Override
    public List<ItemStack> getInputs(ItemStack stack) {
        return Collections.singletonList(input);
    }

    @Override
    public int getOutputAmount(ItemStack stack) {
        return output.getCount();
    }

    @Override
    public boolean isSimple(ItemStack stack) {
        ItemStack toCheck = input;
        if (toCheck.getMetadata() == OreDictionary.WILDCARD_VALUE) {
            toCheck = new ItemStack(input.getItem(), input.getCount(), 0, stack.getTagCompound());
        }
        return Arrays.stream(OreDictionary.getOreIDs(toCheck))
            .mapToObj(OreDictionary::getOreName)
            .anyMatch(name -> name.startsWith("ore"));
    }

    public static void register() {
        for (Map.Entry<ItemStack, ItemStack> recipe : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
            ReverseRecipeRegistry.getInstance().registerReverseRecipe(new WrappedFurnaceRecipes(recipe.getKey(), recipe.getValue()));
        }
    }
}
