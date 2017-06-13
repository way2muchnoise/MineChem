package minechem.api;

import minechem.api.breakdown.IReverseRecipeRegistry;
import minechem.api.printing.IPrintingRegistry;

/**
 * Class holding information about the support for minechem
 */
public interface IMinechemSupport {
    /**
     * Register any special reverse recipe handling, only for custom processes or new IRecipe implementations
     */
    void registerReverseRecipes(IReverseRecipeRegistry registry);

    /**
     * Register recipes for the Item Printer
     */
    void registerPritingRecipes(IPrintingRegistry registry);

    /**
     * Delivers a helper class, this instance should be stored if needed
     */
    void deliverHelper(IMinechemHelper helper);
}
