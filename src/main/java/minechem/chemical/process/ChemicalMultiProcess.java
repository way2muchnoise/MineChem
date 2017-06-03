package minechem.chemical.process;

import java.util.Random;

import minechem.chemical.Chemical;
import minechem.chemical.ChemicalBase;
import net.minecraft.item.ItemStack;

public class ChemicalMultiProcess extends ChemicalProcess
{
    private Chemical[][] outputs;

    /**
     * Process that has different possible outputs, chance will be equally divided
     *
     * @param type       the ChemicalProcessType
     * @param components arrays of possible outputs
     */
    public ChemicalMultiProcess(ChemicalProcessType type, Chemical[]... components)
    {
        super(type);
        outputs = components;
    }

    @Override
    public Chemical[] getOutput(ChemicalProcessType type, ItemStack stack)
    {
        if (super.getOutput(type, stack) != empty) {
            return outputs[new Random().nextInt(outputs.length)];
        }
        return empty;
    }
}
