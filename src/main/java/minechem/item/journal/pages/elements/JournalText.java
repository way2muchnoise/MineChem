package minechem.item.journal.pages.elements;

import minechem.Compendium;
import minechem.helper.LocalizationHelper;
import net.afterlifelochie.fontbox.api.data.FormattedString;
import net.afterlifelochie.fontbox.api.formatting.layout.AlignmentMode;
import net.afterlifelochie.fontbox.api.layout.IElement;
import net.afterlifelochie.fontbox.document.Image;
import net.afterlifelochie.fontbox.document.Paragraph;
import net.minecraft.entity.player.EntityPlayer;

public class JournalText extends JournalElement
{
    private String textKey;
    private AlignmentMode alignment;
    private Object[] params;

    public JournalText(String pageKey)
    {
        this(pageKey, pageKey + ".text");
    }

    public JournalText(String pageKey, String textKey, Object... params)
    {
        super(pageKey);
        this.textKey = "journal." + textKey;
        this.alignment = AlignmentMode.LEFT;
        this.params = params;
    }

    public JournalText setText(String text) {
        textKey = text;
        return this;
    }

    public JournalText setParams(Object... params) {
        this.params = params;
        return this;
    }

    public JournalText setAlignment(AlignmentMode alignment) {
        this.alignment = alignment;
        return this;
    }

    @Override
    public IElement getElement(EntityPlayer player)
    {
        if (isUnlocked(player, getKey()))
        {
            String s = LocalizationHelper.getFormattedString(textKey, params);
            return s.isEmpty() ? new Image(Compendium.Resource.GUI.noContent, 301, 294, AlignmentMode.JUSTIFY) : new Paragraph(new FormattedString(s), alignment);
        }
        return null;
    }

    @Override
    public IElement getElement(String[] keys)
    {
        if (isUnlocked(keys, getKey()))
        {
            String s = LocalizationHelper.getFormattedString(textKey, params);
            return s.isEmpty() ? new Image(Compendium.Resource.GUI.noContent, 301, 294, AlignmentMode.JUSTIFY) : new Paragraph(new FormattedString(s), alignment);
        }
        return null;
    }
}
