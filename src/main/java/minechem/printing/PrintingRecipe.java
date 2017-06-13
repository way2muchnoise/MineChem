package minechem.printing;

import minechem.chemical.Chemical;
import net.minecraft.item.ItemStack;

public class PrintingRecipe {
    public static final int SIZE = 3;

    private Chemical[][] recipe;
    private ItemStack result;
    private int count;

    public PrintingRecipe(ItemStack result, Chemical[][] recipe) {
        this.result = result.copy();
        this.recipe = recipe;
        this.count = 0;
        for (int i = 0; i < recipe.length; i++) {
            for (int j = 0; j < recipe.length; j++) {
                this.count += recipe[i][j].getCount();
            }
        }
    }

    public ItemStack getResult() {
        return result.copy();
    }

    public boolean matches(Chemical[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!grid[i][j].matches(recipe[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getCount() {
        return count;
    }

    public Chemical[][] getRecipe() {
        return recipe;
    }
}
