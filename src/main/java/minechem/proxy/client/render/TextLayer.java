package minechem.proxy.client.render;

import minechem.proxy.client.font.Font;
import net.minecraft.client.renderer.GlStateManager;

public class TextLayer implements ILayer
{
    private final String text;
    private final boolean colour;
    private final Font font;

    public TextLayer(String text, boolean colour, Font font)
    {
        this.text = text;
        this.colour = colour;
        this.font = font;
    }

    @Override
    public void render(int z, int colour)
    {
        RenderHelper.resetOpenGLColour();
        if (this.colour)
        {
            RenderHelper.setOpenGLColour(colour);
        }
        this.font.printWithZ(this.text, 1, 2, z, 0x000000);
        this.font.printWithZ(this.text, 1, 1, z, 0xEEEEEE);
    }

    @Override
    public void render2D(int colour)
    {
        // NOOP
    }

    @Override
    public void render3D(int colour)
    {
        RenderHelper.resetOpenGLColour();
        if (this.colour)
        {
            RenderHelper.setOpenGLColour(colour);
        }
        float scale = 0.0325F;
        GlStateManager.pushMatrix();
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.translate(-16.0F, -16.0F, -scale);
        this.font.print(this.text, 1, 2, 0x000000);
        GlStateManager.translate(0.0F, 0.0F, -scale);
        this.font.print(this.text, 1, 1, 0xEEEEEE);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.translate(-16.0F, -16.0F, scale);
        this.font.print(this.text, 1, 2, 0x000000);
        GlStateManager.translate(0.0F, 0.0F, -scale * 2);
        this.font.print(this.text, 1, 1, 0xEEEEEE);
        GlStateManager.popMatrix();
    }
}
