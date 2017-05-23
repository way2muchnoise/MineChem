package minechem.registry;

import minechem.item.chemical.ChemicalItem;
import minechem.item.journal.JournalItem;

public class ItemRegistry
{
    public static JournalItem journal;
    public static ChemicalItem chemicalItem;

    public static void init()
    {
        journal = new JournalItem();
        //GameRegistry.registerItem(journal, journal.getUnlocalizedName());

        chemicalItem = new ChemicalItem();
        //GameRegistry.registerItem(chemicalItem, chemicalItem.getUnlocalizedName());
    }

}
