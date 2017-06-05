package minechem.breakdown;

import minechem.api.IReverseRecipe;
import minechem.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class ReverseRecipeSorter implements Comparator<IReverseRecipe> {
    private final ItemStack output;

    public ReverseRecipeSorter(ItemStack output) {
        this.output = output.copy();
    }

    @Override
    public int compare(IReverseRecipe o1, IReverseRecipe o2) {
        if (o1.isSimple(output) && !o2.isSimple(output)) {
            return -1;
        } else if (!o1.isSimple(output) && o2.isSimple(output)) {
            return 1;
        }
        float countO1 = 1.0F * ItemStackHelper.getItemCount(o1.getInputs(output)) / o1.getOutputAmount(output);
        float countO2 = 1.0F * ItemStackHelper.getItemCount(o2.getInputs(output)) / o2.getOutputAmount(output);
        return Float.compare(countO1, countO2);
    }
}
