package minechem.registry;

import minechem.item.chemical.ChemicalItem;
import minechem.item.chemical.ChemicalItemColour;
import minechem.item.journal.JournalItem;
import minechem.item.prefab.BasicItem;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRegistry
{
    public static JournalItem journal;
    public static ChemicalItem chemicalItem;

    public static void init(Side side)
    {
        journal = new JournalItem();
        register(journal);

        chemicalItem = new ChemicalItem();
        register(chemicalItem);

        if (side == Side.CLIENT) {
            initModels();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        chemicalItem.initModels();
    }

    @SideOnly(Side.CLIENT)
    public static void initColors() {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ChemicalItemColour(), chemicalItem);
    }

    private static void register(BasicItem item) {
        GameRegistry.<Item>register(item);
    }
}
