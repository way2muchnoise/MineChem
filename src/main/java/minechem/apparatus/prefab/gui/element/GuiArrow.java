package minechem.apparatus.prefab.gui.element;

import minechem.Compendium;
import minechem.helper.LocalizationHelper;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class GuiArrow extends GuiElement {
    private static int arrowX = 90, arrowY = 2, arrowW = 22, arrowH = 15;

    private Supplier<Integer> progression;

    /**
     * Create a element
     *
     * @param posX   the x pos of the arrow (origin is the left-top of the parent gui)
     * @param posY   the y pos of the arrow (origin is the left-top of the parent gui)
     * @param progression a {@link Supplier} of the current progress between 0 and 100
     */
    public GuiArrow(int posX, int posY, Supplier<Integer> progression) {
        super(posX, posY, arrowW, arrowH);
        this.progression = progression;
    }

    @Override
    public void drawBackground(int guiLeft, int guiTop, int mouseX, int mouseY) {
        if (progression.get() > 0) {
            bindTexture(Compendium.Resource.GUI.guiElements);
            int width = MathHelper.floor((progression.get() / 100.0F) * arrowW);
            drawTexturedModalRect(guiLeft + posX, guiTop + posY, arrowX, arrowY, width, arrowH);
        }
    }

    @Override
    public void drawForeground(int guiLeft, int guiTop, int mouseX, int mouseY) {
        mouseX -= guiLeft;
        mouseY -= guiTop;
        if (mouseInElement(mouseX, mouseY)) {
            List<String> tooltip = new ArrayList<>(1);
            tooltip.add(LocalizationHelper.getFormattedString("apparatus.progress", progression.get()));
            drawHoveringText(tooltip, mouseX, mouseY, getFontRenderer());
        }
    }
}
