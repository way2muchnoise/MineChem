package minechem.item.journal;

import minechem.Compendium;
import minechem.Config;
import minechem.registry.JournalRegistry;
import net.afterlifelochie.fontbox.api.DocumentBuilder;
import net.afterlifelochie.fontbox.api.IDocumentBuilder;
import net.afterlifelochie.fontbox.api.data.IDocument;
import net.afterlifelochie.fontbox.api.exception.LayoutException;
import net.afterlifelochie.fontbox.api.layout.IElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;
import java.util.List;

public class Journal {
    @DocumentBuilder
    public static IDocumentBuilder builder;

    public static GuiScreen createJournalGui(EntityPlayer who, String[] knowledgeKeys, String[] authors) {
        IDocument journal = builder.createDocument();
        /* Copy the list of elements */
        List<IElement> elements;
        if (Config.playerPrivateKnowledge)
        {
            elements = JournalRegistry.getJournalFor(who, authors);
        } else
        {
            elements = JournalRegistry.getJournalFor(knowledgeKeys, authors);
        }
        journal.addElements(elements);

        try {
            return journal.createBookGui(Compendium.Fontbox.getManager(), new JournalProperties());
        } catch (IOException | LayoutException e) {
            e.printStackTrace();
            return null;
        }
    }
}
