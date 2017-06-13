package minechem.api;

import net.minecraft.item.ItemStack;

public interface IMinechemHelper {
    /**
     * Get the {@link ItemStack} for a chemical
     * A chemical is name or formula, might be prepended by a count
     * eg. 3*H2O will give you an itemstack of size 3 for the chemical H2O
     *
     * @param chemical string chemical representation
     * @return an itemstack for the given chemical
     */
    ItemStack getStack(String chemical);
}
