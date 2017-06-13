package minechem.api.breakdown;

public interface IReverseRecipeFactory<T> {
    IReverseRecipe getWrapped(T recipe);
}
