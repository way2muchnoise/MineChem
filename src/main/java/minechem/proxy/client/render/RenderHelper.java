package minechem.proxy.client.render;

import minechem.helper.ColourHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Holds short hands for common used {@link org.lwjgl.opengl.GL11} methods
 */
public class RenderHelper extends net.minecraft.client.renderer.RenderHelper
{
    /**
     * Executes GlStateManager.color() for given int colour
     *
     * @param colour in int form
     */
    public static void setOpenGLColour(int colour)
    {
        GlStateManager.color(ColourHelper.getRed(colour), ColourHelper.getGreen(colour), ColourHelper.getBlue(colour), ColourHelper.getAlpha(colour));
    }

    /**
     * Executes GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F)
     */
    public static void resetOpenGLColour()
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Executes GL11.glColor4f(greyscale, greyscale, greyscale, 1.0F)
     *
     * @param greyscale the greyscale in float form where 0.0F is black and 1.0F is white
     */
    public static void setGreyscaleOpenGLColour(float greyscale)
    {
        GlStateManager.color(greyscale, greyscale, greyscale, 1.0F);
    }

    /**
     * Executes GlStateManager.color1.0F, 1.0F, 1.0F, opacity) Used in combination with blend
     *
     * @param opacity the opacity in float form where 1.0F is opaque and 0.0F is transparent
     */
    public static void setOpacity(float opacity)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, opacity);
    }

    /**
     * Executes GlStateManager.color1.0F, 1.0F, 1.0F, 1.0F) back to full opaque
     */
    public static void resetOpacity()
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Draw with the current {@link net.minecraft.util.ResourceLocation} on given coords
     *
     * @param x        xPos
     * @param y        yPos
     * @param z        zPos
     * @param u        uPos on the {@link net.minecraft.util.ResourceLocation}
     * @param v        vPos on the {@link net.minecraft.util.ResourceLocation}
     * @param w        width
     * @param h        height
     */
    public static void drawTexturedRectUV(float x, float y, float z, float u, float v, float w, float h)
    {
        float textScale = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(x, y + h, z).tex(u * textScale, (v + h) * textScale).endVertex();
        vertexBuffer.pos(x + w, y + h, z).tex((u + w) * textScale, (v + h) * textScale).endVertex();
        vertexBuffer.pos(x + w, y, z).tex((u + w) * textScale, v * textScale).endVertex();
        vertexBuffer.pos(x, y, z).tex(u * textScale, v * textScale).endVertex();
        tessellator.draw();
    }

    /**
     * Draw a {@link net.minecraft.util.ResourceLocation} on given coords
     *
     * @param x        xPos
     * @param y        yPos
     * @param z        zPos
     * @param u        uPos on the {@link net.minecraft.util.ResourceLocation}
     * @param v        vPos on the {@link net.minecraft.util.ResourceLocation}
     * @param w        width
     * @param h        height
     * @param resource the {@link net.minecraft.util.ResourceLocation}
     */
    public static void drawTexturedRectUV(float x, float y, float z, float u, float v, float w, float h, ResourceLocation resource)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        float textScale = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(x, y + h, z).tex(u * textScale, (v + h) * textScale).endVertex();
        vertexBuffer.pos(x + w, y + h, z).tex((u + w) * textScale, (v + h) * textScale).endVertex();
        vertexBuffer.pos(x + w, y, z).tex((u + w) * textScale, v * textScale).endVertex();
        vertexBuffer.pos(x, y, z).tex(u * textScale, v * textScale).endVertex();
        tessellator.draw();
    }

    /**
     * Draw a {@link net.minecraft.util.ResourceLocation} on given coords with given scaling
     *
     * @param x        xPos
     * @param y        yPos
     * @param z        zPos
     * @param u        uPos on the {@link net.minecraft.util.ResourceLocation}
     * @param v        vPos on the {@link net.minecraft.util.ResourceLocation}
     * @param w        width
     * @param h        height
     * @param scale    the scale to draw 1.0F is exact, less is smaller and bigger is larger
     * @param resource the {@link net.minecraft.util.ResourceLocation}
     */
    public static void drawScaledTexturedRectUV(float x, float y, float z, float u, float v, float w, float h, float scale, ResourceLocation resource)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        float drawH = h * scale;
        float drawW = w * scale;
        float xScale = 1.0F / w;
        float yScale = 1.0F / h;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(x, y + drawH, z).tex(u * xScale, (v + h) * yScale).endVertex();
        vertexBuffer.pos(x + drawW, y + drawH, z).tex((u + w) * xScale, (v + h) * yScale).endVertex();
        vertexBuffer.pos(x + drawW, y, z).tex((u + w) * xScale, v * yScale).endVertex();
        vertexBuffer.pos(x, y, z).tex(u * xScale, v * yScale).endVertex();
        tessellator.draw();
    }

    /**
     * Draw a {@link net.minecraft.util.ResourceLocation} on given coords with given draw width and draw height
     *
     * @param x        xPos
     * @param y        yPos
     * @param z        zPos
     * @param u        uPos on the {@link net.minecraft.util.ResourceLocation}
     * @param v        vPos on the {@link net.minecraft.util.ResourceLocation}
     * @param w        actual width
     * @param h        actual height
     * @param drawW    the draw width
     * @param drawH    the draw height
     * @param resource the {@link net.minecraft.util.ResourceLocation}
     */
    public static void drawTexturedRectUV(float x, float y, float z, float u, float v, float w, float h, float drawW, float drawH, ResourceLocation resource)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        float xScale = 1.0F / w;
        float yScale = 1.0F / h;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(x, y + drawH, z).tex(u * xScale, (v + h) * yScale).endVertex();
        vertexBuffer.pos(x + drawW, y + drawH, z).tex((u + w) * xScale, (v + h) * yScale).endVertex();
        vertexBuffer.pos(x + drawW, y, z).tex((u + w) * xScale, v * yScale).endVertex();
        vertexBuffer.pos(x, y, z).tex(u * xScale, v * yScale).endVertex();
        tessellator.draw();
    }

    /**
     * Start a GL_SCISSOR_TEST
     *
     * @param guiWidth  {@link net.minecraft.client.gui.GuiScreen#width}
     * @param guiHeight {@link net.minecraft.client.gui.GuiScreen#height}
     * @param x         xPos to start scissor
     * @param y         yPos to start scissor
     * @param w         width of the scissor
     * @param h         height of the scissor
     */
    public static void setScissor(int guiWidth, int guiHeight, float x, float y, int w, int h)
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledRes = new ScaledResolution(mc);
        int scale = scaledRes.getScaleFactor();
        x *= scale;
        y *= scale;
        w *= scale;
        h *= scale;
        float guiScaledWidth = (guiWidth * scale);
        float guiScaledHeight = (guiHeight * scale);
        float guiLeft = ((mc.displayWidth / 2) - guiScaledWidth / 2);
        float guiTop = ((mc.displayHeight / 2) + guiScaledHeight / 2);
        int scissorX = Math.round((guiLeft + x));
        int scissorY = Math.round(((guiTop - h) - y));
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(scissorX, scissorY, w, h);
    }

    /**
     * Stop a GL_SCISSOR_TEST
     */
    public static void stopScissor()
    {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    /**
     * Bind given texture
     *
     * @param texture
     */
    public static void bindTexture(ResourceLocation texture)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    }
}
