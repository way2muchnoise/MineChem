package minechem.registry;

import minechem.chemical.Chemical;
import minechem.collections.ItemStackMap;
import minechem.printing.PrintingRecipe;
import net.minecraft.item.ItemStack;

public class PrintingRegistry {
    private static PrintingRegistry instance;
    private ItemStackMap<PrintingRecipe> recipes;

    public static PrintingRegistry getInstance() {
        if (instance == null) {
            instance = new PrintingRegistry();
        }
        return instance;
    }

    public PrintingRegistry() {
        recipes = new ItemStackMap<>(true);
    }

    public void addRecipe(PrintingRecipe recipe) {
        recipes.put(recipe.getResult(), recipe);
    }

    public PrintingRecipe matches(Chemical[][] grid) {
        PrintingRecipe currentPick = null;
        for (PrintingRecipe recipe : recipes.values()) {
            if (recipe.matches(grid)) {
                if (currentPick == null) {
                    currentPick = recipe;
                } else if (recipe.getCount() > currentPick.getCount()) {
                    currentPick = recipe;
                }
            }
        }
        return currentPick;
    }

    public PrintingRecipe removeRecipe(ItemStack stack) {
        return recipes.remove(stack);
    }

    public void clear() {
        recipes.clear();
    }
}
