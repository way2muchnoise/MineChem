package minechem.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import minechem.item.crafting.CeramicBowlItem;
import minechem.registry.ItemRegistry;

@JEIPlugin
public class MinechemJEIPlugin extends BlankModPlugin {
    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.useNbtForSubtypes(ItemRegistry.chemicalItem);
        subtypeRegistry.useNbtForSubtypes(ItemRegistry.ceramicBowl);
    }
}
