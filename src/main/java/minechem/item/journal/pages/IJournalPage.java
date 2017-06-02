package minechem.item.journal.pages;

import java.util.List;

import net.afterlifelochie.fontbox.api.layout.IElement;
import net.minecraft.entity.player.EntityPlayer;

public interface IJournalPage
{
    String getPageKey();

    IJournalPage getPage(String key);

    String getPageName();

    String getPageTitle();

    boolean hasSubPages();

    void addSubPage(IJournalPage page);

    void setChapter(String chapter);

    int getSubPages();

    List<IElement> getElements(EntityPlayer player);

    List<IElement> getElements(String[] keys);

    boolean isUnlocked(EntityPlayer player);

    boolean isUnlocked(String[] keys);
}
