package minechem.api;

public interface IReverseRecipeRegistry {
    <T> void registerFactory(Class<T> clazz, IReverseRecipeFactory<T> factory);

    void registerRecipe(Object o);

    void registerReverseRecipe(IReverseRecipe reverseRecipe);
}
