package minechem.registry;

import minechem.handler.StructuredJournalHandler;
import minechem.item.journal.pages.IJournalPage;
import minechem.item.journal.pages.SectionPage;
import net.afterlifelochie.fontbox.api.formatting.layout.CompilerHint;
import net.afterlifelochie.fontbox.api.layout.IElement;
import net.afterlifelochie.fontbox.document.CompilerHintElement;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class JournalRegistry
{
    private static SectionPage journal;

    public static void init()
    {
        StructuredJournalHandler.init();
    }

    public static IJournalPage addPage(IJournalPage page)
    {
        return addPage("", page);
    }

    public static IJournalPage addPage(String chapter, IJournalPage page)
    {
        IJournalPage section = journal.getPage(chapter);
        if (section != null && section.hasSubPages())
        {
            page.setChapter(chapter);
            section.addSubPage(page);
        }
        return page;
    }

    public static boolean hasPage(String key)
    {
        return journal.getPage(key) != null;
    }

    public static List<IElement> getJournalFor(EntityPlayer player)
    {
        List<IElement> result = getIndexPageFor(player);
        result.add(new CompilerHintElement(CompilerHint.PAGE_BREAK));
        result.addAll(journal.getElements(player));
        result.remove(result.size() - 1);
        return result;
    }

    public static List<IElement> getJournalFor(String[] keys)
    {
        List<IElement> result = getIndexPageFor(keys);
        result.add(new CompilerHintElement(CompilerHint.PAGE_BREAK));
        result.addAll(journal.getElements(keys));
        result.remove(result.size() - 1);
        return result;
    }

    public static List<IElement> getIndexPageFor(EntityPlayer player)
    {
        return journal.getIndexPage(player, 0);
    }

    public static List<IElement> getIndexPageFor(String[] keys)
    {
        return journal.getIndexPage(keys, 0);
    }

    public static SectionPage setJournal(SectionPage journal)
    {
        JournalRegistry.journal = journal;
        return JournalRegistry.journal;
    }
}
