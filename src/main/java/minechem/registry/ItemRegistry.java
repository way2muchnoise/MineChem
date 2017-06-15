package minechem.registry;

import minechem.Compendium;
import minechem.item.chemical.ChemicalItem;
import minechem.item.chemical.ChemicalItemColour;
import minechem.item.crafting.CeramicBowlItem;
import minechem.item.journal.JournalItem;
import minechem.item.prefab.BasicItem;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRegistry {
    public static JournalItem journal;
    public static ChemicalItem chemicalItem;

    public static BasicItem concaveLens;
    public static BasicItem convexLens;
    public static BasicItem microscopeLens;
    public static BasicItem ceramicBowl;
    public static BasicItem heatingCore;
    public static BasicItem electricCore;
    public static BasicItem centrifugeCore;
    public static BasicItem printingHead;
    public static BasicItem coolantTank;

    public static void init(Side side) {
        //Basic items
        concaveLens = register(Compendium.Naming.concaveLens);
        convexLens = register(Compendium.Naming.convexLens);
        microscopeLens = register(Compendium.Naming.microscopeLens);
        heatingCore = register(Compendium.Naming.heatingCore);
        electricCore = register(Compendium.Naming.electricCore);
        centrifugeCore = register(Compendium.Naming.centrifugeCore);
        printingHead = register(Compendium.Naming.printingHead);
        coolantTank = register(Compendium.Naming.coolantTank);

        // Complex items
        journal = register(new JournalItem());
        chemicalItem = register(new ChemicalItem());
        ceramicBowl = register(new CeramicBowlItem());

        // On the client textures need to be inited
        if (side == Side.CLIENT) {
            initModels();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        concaveLens.initModel();
        convexLens.initModel();
        microscopeLens.initModel();
        heatingCore.initModel();
        electricCore.initModel();
        centrifugeCore.initModel();
        printingHead.initModel();
        coolantTank.initModel();

        chemicalItem.initModel();
        journal.initModel();
        ceramicBowl.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initColors() {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ChemicalItemColour(), chemicalItem);
    }

    private static <T extends BasicItem> T register(T item) {
        GameRegistry.<Item>register(item);
        return item;
    }

    private static BasicItem register(String name) {
        BasicItem item = new BasicItem(name);
        register(item);
        return item;
    }
}
