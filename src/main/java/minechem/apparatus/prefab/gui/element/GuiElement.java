package minechem.apparatus.prefab.gui.element;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Base class for GuiElements Holds methods that can be useful when making GuiElements
 *
 * @author way2muchnoise
 */
public abstract class GuiElement extends Gui
{
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;

    /**
     * Create a element
     *
     * @param posX   the x pos of the element (origin is the left-top of the parent gui)
     * @param posY   the y pos of the element (origin is the left-top of the parent gui)
     * @param width  the width of the element
     * @param height the height of the element
     */
    public GuiElement(int posX, int posY, int width, int height)
    {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    /**
     * Draw the element background at the given location
     *
     * @param guiLeft the absolute x pos of the parent gui
     * @param guiTop  the absolute y pos of the parent gui
     * @param mouseX
     * @param mouseY
     */
    public abstract void drawBackground(int guiLeft, int guiTop, int mouseX, int mouseY);

    /**
     * Draw the element foreground at the given location
     *
     * @param guiLeft the absolute x pos of the parent gui
     * @param guiTop  the absolute y pos of the parent gui
     * @param mouseX
     * @param mouseY
     */
    public abstract void drawForeground(int guiLeft, int guiTop, int mouseX, int mouseY);

    /**
     * Shorthand for binding a Resource
     *
     * @param resource the ResourceLocation
     */
    protected void bindTexture(ResourceLocation resource)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
    }

    /**
     * Shorthand for getting the {@link net.minecraft.client.Minecraft}
     * {@link net.minecraft.client.gui.FontRenderer}
     *
     * @return the main MinecraftFontRenderer
     */
    protected FontRenderer getFontRenderer()
    {
        return Minecraft.getMinecraft().fontRenderer;
    }

    /**
     * Draws a textured rectangle at the current zLevel.
     *
     * @param x            x pos
     * @param y            y pos
     * @param u            u pos of texture
     * @param v            v pos of texture
     * @param actualWidth  width of texture
     * @param actualHeight height of texture
     * @param drawWidth    width to draw on
     * @param drawHeight   height to draw on
     */
    protected void drawTexturedModalRectScaled(float x, float y, float u, float v, int actualWidth, int actualHeight, int drawWidth, int drawHeight)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos((double) x, (double) (y + drawHeight), (double) this.zLevel).tex((double) (u * f), (double) ((v + actualHeight) * f1)).endVertex();
        vertexBuffer.pos((double) (x + drawWidth), (double) (y + drawHeight), (double) this.zLevel).tex((double) ((u + actualWidth) * f), (double) ((v + actualHeight) * f1)).endVertex();
        vertexBuffer.pos((double) (x + drawWidth), (double) (y), (double) this.zLevel).tex((double) ((u + actualWidth) * f), (double) (v * f1)).endVertex();
        vertexBuffer.pos((double) (x), (double) (y), (double) this.zLevel).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
    }

    /**
     * Draw a tooltip at the given location
     *
     * @param tooltip      strings of the tooltip
     * @param x            x pos
     * @param y            y pos
     * @param fontrenderer FontRenderer to draw
     */
    protected void drawHoveringText(List<String> tooltip, int x, int y, FontRenderer fontrenderer)
    {
        if (!tooltip.isEmpty())
        {
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int k = 0;

            for (String line : tooltip)
            {
                int l = fontrenderer.getStringWidth(line);

                if (l > k)
                {
                    k = l;
                }
            }

            int i1 = x + 12;
            int j1 = y - 12;
            int k1 = 8;

            if (tooltip.size() > 1)
            {
                k1 += 2 + (tooltip.size() - 1) * 10;
            }

            this.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < tooltip.size(); ++k2)
            {
                String s1 = tooltip.get(k2);
                fontrenderer.drawStringWithShadow(s1, i1, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableRescaleNormal();
        }
    }

    protected boolean mouseInElement(int x, int y)
    {
        return x >= this.posX && x < this.posX + width - 2 && y >= this.posY && y < this.posY + height - 2;
    }
}
