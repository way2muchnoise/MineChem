package minechem.proxy.client.render;

import net.minecraft.util.ResourceLocation;

public class IconLayer implements ILayer
{
    private final ResourceLocation icon;
    private final boolean colour;

    public IconLayer(ResourceLocation icon, boolean colour)
    {
        this.icon = icon;
        this.colour = colour;
    }

    @Override
    public void render(int z, int colour)
    {
        RenderHelper.resetOpenGLColour();
        if (this.colour)
        {
            RenderHelper.setOpenGLColour(colour);
        }
        RenderHelper.drawTexturedRectUV(0, 0, z, 16, 16, 16, 16, this.icon);
    }

    @Override
    public void render2D(int colour)
    {
        RenderHelper.resetOpenGLColour();
        if (this.colour)
        {
            RenderHelper.setOpenGLColour(colour);
        }
        // RenderHelper.drawTextureIn2D(this.icon);
    }

    @Override
    public void render3D(int colour)
    {
        RenderHelper.resetOpenGLColour();
        if (this.colour)
        {
            RenderHelper.setOpenGLColour(colour);
        }
        // RenderHelper.drawTextureIn3D(this.icon);
    }
}
