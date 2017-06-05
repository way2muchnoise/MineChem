package minechem.chemical;

import minechem.item.chemical.ChemicalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.LinkedList;
import java.util.List;

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

    public Chemical copy() {
        return new Chemical(chemicalBase.copy(), count);
    }

    public void applyChance(float chance) {
        float newCount = count * chance;
        count = MathHelper.floor(newCount);
        if (Math.random() < (newCount - count)) {
            count += 1;
        }
    }

    public static List<Chemical> applyProbability(List<Chemical> chemicals, float chance) {
        List<Chemical> realChemicals = new LinkedList<>();
        for (Chemical chemical : chemicals) {
            Chemical realChemical = chemical.copy();
            realChemical.applyChance(chance);
            if (realChemical.getCount() > 0) {
                realChemicals.add(realChemical);
            }
        }
        return realChemicals;
    }


}
