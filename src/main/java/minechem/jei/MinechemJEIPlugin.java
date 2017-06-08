package minechem.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import minechem.item.crafting.CeramicBowlItem;
import minechem.registry.ItemRegistry;

@JEIPlugin
public class MinechemJEIPlugin implements IModPlugin {
    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.useNbtForSubtypes(ItemRegistry.ceramicBowl);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {

    }

    @Override
    public void register(IModRegistry registry) {
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}
