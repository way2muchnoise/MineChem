package minechem.item.journal.pages.elements;

import net.afterlifelochie.fontbox.api.layout.IElement;
import net.minecraft.entity.player.EntityPlayer;

public interface IJournalElement
{
    IElement getElement(EntityPlayer player);

    IElement getElement(String[] keys);
}
