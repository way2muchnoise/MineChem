package minechem.api;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Used to calculate drops for items that are craftable
 * The count in the passed {@link ItemStack} should not be taken in to consideration
 * It might not even have to be interpreted at all.
 */
public interface IReverseRecipe {
    /**
     * @param stack a stack to be produced
     * @return whether this recipe can output the passed stack
     */
    boolean canOutput(final ItemStack stack);

    /**
     * @param stack a stack to be produced
     * @return inputs when the given stack is the output
     */
    List<ItemStack> getInputs(ItemStack stack);

    /**
     * @param stack a stack to be produced
     * @return the amount that is outputted
     */
    int getOutputAmount(ItemStack stack);

    /**
     * By default this is false and should only be set on true for processes that are considered the basic way over other methods
     * eg. ingot furnace recipes are considered simple, as it is the main way of producing ingots
     * instead of using the 9 nuggets recipe which is considered more basic.
     *
     * @param stack a stack to be produced
     * @return is this recipe generally simple, will have priority on non-simple recipes
     */
    default boolean isSimple(ItemStack stack) {
        return false;
    }
}
