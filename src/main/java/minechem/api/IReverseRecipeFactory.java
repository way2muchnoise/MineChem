package minechem.api;

public interface IReverseRecipeFactory<T> {
    IReverseRecipe getWrapped(T recipe);
}
