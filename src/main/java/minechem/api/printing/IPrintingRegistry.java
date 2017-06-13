package minechem.api.printing;

import net.minecraft.item.ItemStack;

public interface IPrintingRegistry {
    /**
     * Add an item printing recipe
     * eg. registerPrint(new ItemStack(Blocks.clay), new String[] {
     *     new String[] {"", "Au", ""},
     *     new String[] {"2*Fe", "", "2*Au"},
     *     new String[] {"", "Fe", ""},
     * });
     *
     * A chemical is represented by it's name or formula, prepended with an amount.
     *
     * @param result the resulting {@link ItemStack}
     * @param chemicals a 3x3 matrix for inputs
     */
    void registerPrint(ItemStack result, String[][] chemicals);

    /**
     * Add an item printing recipe
     * eg. registerPrint(new ItemStack(Blocks.clay),
     *     ";Au;",
     *     "2*Fe;;2*Au",
     *     ";Fe;");
     *
     * A chemical is represented by it's name or formula, prepended with an amount.
     *
     * @param result the resulting {@link ItemStack}
     * @param chemicals 3 strings with the recipe where slots are separated by semi-colons
     */
    void registerPrint(ItemStack result, String... chemicals);
}
