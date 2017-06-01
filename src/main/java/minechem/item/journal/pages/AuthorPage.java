package minechem.item.journal.pages;

import minechem.helper.LocalizationHelper;
import net.afterlifelochie.fontbox.api.data.FormattedString;
import net.afterlifelochie.fontbox.api.layout.IElement;
import net.afterlifelochie.fontbox.document.Heading;
import net.afterlifelochie.fontbox.document.Paragraph;

import java.util.LinkedList;
import java.util.List;

public class AuthorPage {
    private String[] authors;

    public AuthorPage(String[] authors) {
        this.authors = authors;
    }

    public List<IElement> getElements() {
        List<IElement> elements = new LinkedList<>();
        elements.add(new Heading(null, new FormattedString(LocalizationHelper.getLocalString("gui.journal.writtenBy"))));
        for (String author : authors) {
            elements.add(new Paragraph(new FormattedString(author)));
        }
        return elements;
    }
}
