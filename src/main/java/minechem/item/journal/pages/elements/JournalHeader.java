package minechem.item.journal.pages.elements;

import minechem.helper.LocalizationHelper;
import net.afterlifelochie.fontbox.api.data.FormattedString;
import net.afterlifelochie.fontbox.document.Element;
import net.afterlifelochie.fontbox.document.Heading;
import net.afterlifelochie.fontbox.document.Link;
import net.minecraft.entity.player.EntityPlayer;

public class JournalHeader extends JournalElement
{
    private String titleKey;

    public JournalHeader(String pageKey)
    {
        super(pageKey);
        titleKey = "journal" + (pageKey.isEmpty() ? "" : "." + pageKey) + ".title";
    }

    @Override
    public Element getElement(EntityPlayer player)
    {
        return getHeading();
    }

    @Override
    public Element getElement(String[] keys)
    {
        return getHeading();
    }

    public Element getHeading() {
        return new Heading(getKey(), getString(0));
    }

    public Element getLink(int indent) {
        return new Link(getString(indent), getKey());
    }

    public Element getHeadingOrLink(int indent) {
        return indent == 0 ? getHeading() : getLink(indent);
    }

    private FormattedString getString(int indent)
    {
        String sIndent = "";
        for (int i = 0; i < indent; i++)
        {
            sIndent += "--";
        }
        return new FormattedString(sIndent + " " + LocalizationHelper.getLocalString(titleKey));
    }
}
