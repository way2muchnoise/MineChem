package minechem.item.journal.pages;

import minechem.item.journal.pages.elements.JournalHeader;
import net.afterlifelochie.fontbox.api.data.FormattedString;
import net.afterlifelochie.fontbox.api.formatting.layout.CompilerHint;
import net.afterlifelochie.fontbox.api.layout.IElement;
import net.afterlifelochie.fontbox.document.CompilerHintElement;
import net.afterlifelochie.fontbox.document.Element;
import net.afterlifelochie.fontbox.document.Link;
import net.minecraft.entity.player.EntityPlayer;

import java.util.*;
import java.util.regex.Matcher;

public class SectionPage extends JournalPage
{
    private final Map<String, IJournalPage> pages = new LinkedHashMap<>();
    private JournalHeader heading;

    public SectionPage(String page)
    {
        this(page, new ArrayList<>());
    }

    public SectionPage(String page, List<IJournalPage> pageList)
    {
        this(page, "", pageList);
    }

    public SectionPage(String page, String chapter, List<IJournalPage> pageList)
    {
        super(page, chapter);
        for (IJournalPage jPage : pageList)
        {
            pages.put(jPage.getPageName(), jPage);
        }
        heading = new JournalHeader(getPageKey());
    }

    @Override
    public IJournalPage getPage(String key)
    {
        IJournalPage result = super.getPage(key);
        if (result == null)
        {
            if (pages.containsKey(key))
            {
                return pages.get(key);
            }
            Matcher matcher = subPagePattern.matcher(key);
            if (matcher.find())
            {
                if (pages.containsKey(matcher.group(1)))
                {
                    return pages.get(matcher.group(1)).getPage(matcher.group(2));
                }
            }
        }
        return result;
    }

    @Override
    public boolean hasSubPages()
    {
        return true;
    }

    @Override
    public void addSubPage(IJournalPage page)
    {
        pages.put(page.getPageKey(), page);
    }

    @Override
    public int getSubPages()
    {
        int total = pages.size();
        for (IJournalPage page : pages.values())
        {
            total += page.getSubPages();
        }
        return total;
    }

    public List<Element> getPageElements(EntityPlayer player)
    {
        List<Element> result = new LinkedList<>();
        for (IJournalPage page : pages.values())
        {
            if (page.isUnlocked(player))
            {
                // @TODO: for every unlocked page add a link.
            }
        }
        return result;
    }

    public List<Element> getPageElements(String[] keys)
    {
        List<Element> result = new LinkedList<>();
        for (IJournalPage page : pages.values())
        {
            if (page.isUnlocked(keys))
            {
                // @TODO: for every unlocked page add a link.
            }
        }
        return result;
    }

    @Override
    public List<IElement> getElements(EntityPlayer player)
    {
        List<IElement> result = new LinkedList<>();
        for (IJournalPage page : pages.values())
        {
            List<IElement> elements = page.getElements(player);
            if (elements.size() > 0 && page instanceof SectionPage)
            {
                result.addAll(((SectionPage) page).getIndexPage(player, 0));
                result.add(new CompilerHintElement(CompilerHint.PAGE_BREAK));
            }
            result.addAll(elements);
            result.addAll(page.getElements(player));
        }
        if (!result.isEmpty())
        {
            result.addAll(0, getPageElements(player));
        }
        return result;
    }

    @Override
    public List<IElement> getElements(String[] keys)
    {
        List<IElement> result = new LinkedList<>();
        for (IJournalPage page : pages.values())
        {
            List<IElement> elements = page.getElements(keys);
            if (elements.size() > 0 && page instanceof SectionPage)
            {
                result.addAll(((SectionPage) page).getIndexPage(keys, 0));
                result.add(new CompilerHintElement(CompilerHint.PAGE_BREAK));
            }
            result.addAll(elements);
        }
        if (!result.isEmpty())
        {
            result.addAll(0, getPageElements(keys));
        }
        return result;
    }

    @Override
    public boolean isUnlocked(EntityPlayer player)
    {
        for (IJournalPage page : pages.values())
        {
            if (page.isUnlocked(player))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUnlocked(String[] keys)
    {
        for (IJournalPage page : pages.values())
        {
            if (page.isUnlocked(keys))
            {
                return true;
            }
        }
        return false;
    }

    public List<IElement> getIndexPage(String[] keys, int indent)
    {
        List<IElement> result = new LinkedList<>();
        result.add(heading.getHeadingOrLink(indent));
        if (indent > 1)
        {
            return result;
        }
        for (IJournalPage page : pages.values())
        {
            if (page.isUnlocked(keys))
            {
                if (page instanceof SectionPage)
                {
                    result.addAll(((SectionPage) page).getIndexPage(keys, indent + 1));
                } else if (indent < 1)
                {
                    String sIndent = "";
                    for (int i = 0; i < indent + 1; i++)
                    {
                        sIndent += "--";
                    }
                    result.add(new Link(new FormattedString(sIndent + " " + page.getPageTitle()), page.getPageKey()));
                }
            }
        }
        return result;
    }

    public List<IElement> getIndexPage(EntityPlayer player, int indent)
    {
        List<IElement> result = new LinkedList<>();
        result.add(heading.getHeadingOrLink(indent));
        if (indent > 1)
        {
            return result;
        }
        for (IJournalPage page : pages.values())
        {
            if (page.isUnlocked(player))
            {
                if (page instanceof SectionPage)
                {
                    result.addAll(((SectionPage) page).getIndexPage(player, indent + 1));
                } else
                {
                    String sIndent = "";
                    for (int i = 0; i < indent + 1; i++)
                    {
                        sIndent += "--";
                    }
                    result.add(new Link(new FormattedString(sIndent + " " + page.getPageTitle()), page.getPageKey()));
                }
            }
        }
        return result;
    }
}
