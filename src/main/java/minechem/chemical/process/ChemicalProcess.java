package minechem.chemical.process;

import minechem.chemical.Chemical;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class ChemicalProcess
{
    public static Chemical[] empty = new Chemical[0];
    protected ChemicalProcessType type;
    protected Chemical[] output;

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

    public ChemicalProcess(ChemicalProcessType type, Collection<Chemical> components) {
        this(type, components.toArray(new Chemical[components.size()]));
    }

    public Chemical[] getOutput(ChemicalProcessType type, ItemStack stack)
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
