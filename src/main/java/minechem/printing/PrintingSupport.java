package minechem.printing;

import minechem.api.printing.IPrintingRegistry;
import minechem.chemical.Chemical;
import minechem.helper.Jenkins;
import minechem.registry.PrintingRegistry;
import net.minecraft.item.ItemStack;

public class PrintingSupport implements IPrintingRegistry {
    private static PrintingSupport instance;

    public static PrintingSupport getInstance() {
        if (instance == null) {
            instance = new PrintingSupport();
        }
        return instance;
    }

    public void registerPrint(ItemStack result, String[][] chemicals) {
        if (chemicals.length != 3
            || chemicals[0].length != 3
            || chemicals[1].length != 3
            || chemicals[2].length != 3) {
            throw new IllegalArgumentException("Chemicals bad size, needs to be a 3x3 matrix");
        }

        Chemical[][] recipe = new Chemical[PrintingRecipe.SIZE][PrintingRecipe.SIZE];
        for (int i = 0; i < PrintingRecipe.SIZE; i++) {
            for (int j = 0; j < PrintingRecipe.SIZE; j++) {
                recipe[i][j] = Jenkins.getChemical(chemicals[i][j]);
            }
        }

        PrintingRegistry.getInstance().addRecipe(new PrintingRecipe(result, recipe));
    }

    @Override
    public void registerPrint(ItemStack result, String... chemicals) {
        if (chemicals.length != 3
            || chemicals[0].split(";").length != 3
            || chemicals[1].split(";").length != 3
            || chemicals[2].split(";").length != 3) {
            throw new IllegalArgumentException("Chemicals bad size, should be 3 string and have 2 semi-colons");
        }

        Chemical[][] recipe = new Chemical[PrintingRecipe.SIZE][PrintingRecipe.SIZE];
        for (int i = 0; i < PrintingRecipe.SIZE; i++) {
            String[] split = chemicals[i].split(";");
            for (int j = 0; j < PrintingRecipe.SIZE; j++) {
                recipe[i][j] = Jenkins.getChemical(split[j]);
            }
        }

        PrintingRegistry.getInstance().addRecipe(new PrintingRecipe(result, recipe));
    }
}
