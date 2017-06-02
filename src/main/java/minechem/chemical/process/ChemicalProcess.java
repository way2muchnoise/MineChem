package minechem.chemical.process;

import minechem.chemical.Chemical;

public class ChemicalProcess
{
    public static Chemical[] empty = new Chemical[0];
    private ChemicalProcessType type;
    private Chemical[] output;

    /**
     * Create a process to bind to an ItemStack
     *
     * @param type       the ChemicalProcessType
     * @param components the output components
     */
    public ChemicalProcess(ChemicalProcessType type, Chemical... components)
    {
        this.type = type;
        this.output = components;
    }

    public Chemical[] getOutput(ChemicalProcessType type)
    {
        if (this.type == type) {
            return output;
        }
        return empty;
    }

    public ChemicalProcessType getType()
    {
        return type;
    }
}
