package minechem.item.chemical;

import minechem.chemical.ChemicalBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ChemicalItemColour implements IItemColor {
    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        if (tintIndex == 1) {
            ChemicalBase chemicalBase = ChemicalItem.getChemicalBase(stack);
            if (chemicalBase != null) {
                return chemicalBase.getColour();
            }
        }
        return -1;
    }
}
