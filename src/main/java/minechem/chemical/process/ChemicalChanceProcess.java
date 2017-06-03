package minechem.chemical.process;

import minechem.chemical.Chemical;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ChemicalChanceProcess extends ChemicalProcess
{
    private float chance;

    /**
     * A process that has a chance to give output
     *
     * @param type       the ChemicalProcessType
     * @param chance     the chance in a float format min 0, max 1
     * @param components the output
     */
    public ChemicalChanceProcess(ChemicalProcessType type, float chance, Chemical... components)
    {
        super(type, components);
        this.chance = chance;
        if (this.chance > 1)
        {
            this.chance = 1;
        }
        if (this.chance < 0)
        {
            this.chance = 0;
        }
    }

    @Override
    public Chemical[] getOutput(ChemicalProcessType type, ItemStack stack)
    {
        if (new Random().nextFloat() > chance)
        {
            return super.getOutput(type, stack);
        }
        return empty;
    }
}
