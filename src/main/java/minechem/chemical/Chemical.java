package minechem.chemical;

import minechem.item.chemical.ChemicalItem;
import net.minecraft.item.ItemStack;

public class Chemical {
    private ChemicalBase chemicalBase;
    private int count;

    public Chemical(ChemicalBase chemicalBase, int count) {
        this.chemicalBase = chemicalBase;
        this.count = count;
    }

    public ChemicalBase getChemicalBase() {
        return chemicalBase;
    }

    public int getCount() {
        return count;
    }

    public ItemStack asItemStack() {
        ItemStack stack = ChemicalItem.getItemStackForChemical(chemicalBase);
        stack.setCount(count);
        return stack;
    }
}
