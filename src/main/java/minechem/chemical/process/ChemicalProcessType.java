package minechem.chemical.process;

import minechem.registry.ChemicalProcessRegistry;

public class ChemicalProcessType implements Comparable<ChemicalProcessType>
{
    public static final ChemicalProcessType heat = ChemicalProcessRegistry.getInstance().addProcess("heat");
    public static final ChemicalProcessType ionization = ChemicalProcessRegistry.getInstance().addProcess("ionization");
    public static final ChemicalProcessType acid = ChemicalProcessRegistry.getInstance().addProcess("acid");
    public static final ChemicalProcessType vaporize = ChemicalProcessRegistry.getInstance().addProcess("vaporize");
    public static final ChemicalProcessType dry = ChemicalProcessRegistry.getInstance().addProcess("dry");
    public static final ChemicalProcessType condensation = ChemicalProcessRegistry.getInstance().addProcess("condensation");
    public static final ChemicalProcessType centrifuge = ChemicalProcessRegistry.getInstance().addProcess("centrifuge");
    public static final ChemicalProcessType electrolysis = ChemicalProcessRegistry.getInstance().addProcess("electrolysis");

    protected String name;

    public ChemicalProcessType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    /**
     * Compare method to sort a list by name
     *
     * @param otherType
     * @return
     */
    @Override
    public int compareTo(ChemicalProcessType otherType)
    {
        return this.name.compareTo(otherType.name);
    }
}
