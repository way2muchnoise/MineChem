package minechem.item.chemical;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ChemicalItemColour implements IItemColor {
    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return tintIndex == 0 ? -1 : ChemicalItem.getChemicalBase(stack).getColour();
    }
}
