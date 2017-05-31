package minechem.item.journal.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import minechem.item.journal.pages.elements.IJournalElement;
import minechem.item.journal.pages.elements.JournalHeader;
import net.afterlifelochie.fontbox.api.formatting.layout.CompilerHint;
import net.afterlifelochie.fontbox.api.layout.IElement;
import net.afterlifelochie.fontbox.document.CompilerHintElement;
import net.afterlifelochie.fontbox.document.Element;
import net.minecraft.entity.player.EntityPlayer;

public class EntryPage extends JournalPage
{
    private List<IJournalElement> elements;

    public EntryPage(String page, String chapter, IJournalElement... elements)
    {
        this(page, chapter, new ArrayList<>(Arrays.asList(elements)));
    }

    public EntryPage(String page, String chapter, List<IJournalElement> elements)
    {
        super(page, chapter);
        if (elements.size() == 0 || !(elements.get(0) instanceof JournalHeader))
        {
            elements.add(0, new JournalHeader(getPageKey()));
        }
        this.elements = elements;
    }

    @Override
    public List<IElement> getElements(EntityPlayer player)
    {
        List<IElement> result = new ArrayList<>();
        if (isUnlocked(player))
        {
            for (IJournalElement element : elements)
            {
                IElement e = element.getElement(player);
                if (e != null)
                {
                    result.add(e);
                }
            }
            result.add(new CompilerHintElement(CompilerHint.PAGE_BREAK));
        }
        return result;
    }

    @Override
    public List<IElement> getElements(String[] keys)
    {
        List<IElement> result = new ArrayList<>();
        if (isUnlocked(keys))
        {
            for (IJournalElement element : elements)
            {
                Element e = element.getElement(keys);
                if (e != null)
                {
                    result.add(e);
                }
            }
            result.add(new CompilerHintElement(CompilerHint.PAGE_BREAK));
        }
        return result;
    }
}
