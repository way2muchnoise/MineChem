package minechem.item.journal;

import minechem.Compendium;
import minechem.proxy.client.render.RenderHelper;
import net.afterlifelochie.fontbox.api.data.IBookProperties;
import net.afterlifelochie.fontbox.api.formatting.PageMode;
import net.afterlifelochie.fontbox.api.formatting.PageProperties;
import net.afterlifelochie.fontbox.api.formatting.layout.Layout;
import net.afterlifelochie.fontbox.api.formatting.style.TextFormat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class JournalProperties implements IBookProperties {

    private int ptr = -1;
    private int lastPage = -1;

    @Override
    public PageProperties getPageProperties() {
        TextFormat defaultFormat = new TextFormat(Compendium.Fontbox.getManager().fromName("Note this"));
        TextFormat headingFormat = new TextFormat(Compendium.Fontbox.getManager().fromName("Ampersand"));

        PageProperties properties = new PageProperties(221, 380, defaultFormat);
        properties.headingFormat(headingFormat);
        properties.bothMargin(2).lineHeightSize(4).spaceSize(10).densitiy(0.33f);

        return properties;
    }

    @Override
    public PageMode getPageMode() {
        return new PageMode(new Layout(10, 5), new Layout(138, 5));
    }

    @Override
    public void onPageChanged(GuiScreen gui, int whatPtr, int lastPointer) {
        ptr = whatPtr;
        lastPage = lastPointer;
    }

    @Override
    public void drawBackground(int width, int height, int mx, int my, float frame, float zLevel) {
        RenderHelper.bindTexture(Compendium.Resource.GUI.journal);
        RenderHelper.drawTexturedRectUV(0, 0, 0, 0, 0, 256, 188);
        if (ptr != -1)
        {
            drawFoldedPages(ptr);
        }
    }

    @Override
    public int getBookHeight() {
        return 188;
    }

    @Override
    public int getBookWidth() {
        return 256;
    }

    @Override
    public void drawForeground(int width, int height, int mx, int my, float frame, float zLevel) {

    }

    private void drawFoldedPages(int ptr)
    {
        if (ptr > 1)
        {
            // Draw folded page on the left
            RenderHelper.drawTexturedRectUV(5, 163, 2, 0, 188, 21, 21);
        }
        if (ptr + 1 < lastPage)
        {
            // Draw folded page on the right
            RenderHelper.drawTexturedRectUV(230, 160, 2, 21, 188, 21, 21);
        }
    }
}
