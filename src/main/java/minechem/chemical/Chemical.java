package minechem.chemical;

import minechem.item.chemical.ChemicalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Chemical {
    public static final Chemical EMPTY = new Chemical(null, 0);

    private ChemicalBase chemicalBase;
    private int count;

    public Chemical(ItemStack stack) {
        this.chemicalBase = ChemicalItem.getChemicalBase(stack);
        this.count = stack.getCount();
    }

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

    public boolean isEmpty() {
        return chemicalBase == null || count <= 0;
    }

    public boolean matches(Chemical chemical) {
        if (chemicalBase == null) {
            return chemical.chemicalBase == null && count >= chemical.count;
        } else {
            return chemicalBase.equals(chemical.chemicalBase) && count >= chemical.count;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chemical that = (Chemical) o;

        return this.chemicalBase.equals(that.chemicalBase) && this.count == that.count;
    }

    @Override
    public String toString() {
        return count + "*" + chemicalBase.getFormula();
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

    public static List<Chemical> flatten(List<Chemical> chemicals) {
        Map<ChemicalBase, Integer> map = new HashMap<>();
        for (Chemical chemical : chemicals) {
            Integer amount = map.get(chemical.chemicalBase);
            if (amount == null) {
                amount = 0;
            }
            map.put(chemical.chemicalBase, amount + chemical.count);
        }
        return map.entrySet().stream().map(entry -> new Chemical(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }
}
