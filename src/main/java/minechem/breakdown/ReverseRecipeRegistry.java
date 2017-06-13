package minechem.breakdown;

import minechem.api.breakdown.IReverseRecipe;
import minechem.api.breakdown.IReverseRecipeFactory;
import minechem.api.breakdown.IReverseRecipeRegistry;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class ReverseRecipeRegistry implements IReverseRecipeRegistry {
    private static ReverseRecipeRegistry instance;
    private Map<Class, IReverseRecipeFactory> factoryMap;
    private List<IReverseRecipe> reverseRecipes;

    public static ReverseRecipeRegistry getInstance() {
        if (instance == null) {
            instance = new ReverseRecipeRegistry();
        }
        return instance;
    }

    private ReverseRecipeRegistry() {
        factoryMap = new HashMap<>();
        reverseRecipes = new LinkedList<>();
    }

    @Override
    public <T> void registerFactory(Class<T> clazz, IReverseRecipeFactory<T> factory) {
        factoryMap.put(clazz, factory);
    }

    @Override
    public void registerRecipe(Object o) {
        IReverseRecipeFactory factory = factoryMap.get(o.getClass());
        if (factory != null) {
            IReverseRecipe wrapped = factory.getWrapped(o);
            if (wrapped != null) {
                reverseRecipes.add(wrapped);
            }
        }
    }

    @Override
    public void registerReverseRecipe(IReverseRecipe reverseRecipe) {
        if (reverseRecipe != null) {
            reverseRecipes.add(reverseRecipe);
        }
    }

    public List<IReverseRecipe> getReverseRecipes(final ItemStack stack) {
        return reverseRecipes.stream().filter(rr -> rr.canOutput(stack)).collect(Collectors.toList());
    }
}
