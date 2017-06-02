package minechem.apparatus.prefab.renderer;

import minechem.apparatus.prefab.model.BasicModel;
import minechem.apparatus.prefab.tileEntity.BasicTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class BasicTileEntityRenderer<T extends BasicTileEntity, M extends BasicModel> extends TileEntitySpecialRenderer<T>
{
    protected M model;
    protected float rotation;
    protected ResourceLocation texture;

    protected double xOffset;
    protected double yOffset;
    protected double zOffset;
    protected float xScale;
    protected float yScale;
    protected float zScale;

    public BasicTileEntityRenderer()
    {
        setScale(1.0F);
    }

    public BasicTileEntityRenderer(float scale)
    {
        setScale(scale);
        setRotation(0.0625F);
    }

    public BasicTileEntityRenderer(float scale, float rotation)
    {
        setScale(scale);
        setRotation(rotation);
        setOffset(0.5D, 1.5D, 0.5D);
    }

    @Override
    public void renderTileEntityAt(T tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        float rotation = (tileEntity == null ? 0: tileEntity.getBlockMetadata()) * 90.0F;
        GlStateManager.pushMatrix();

        GlStateManager.enableRescaleNormal();
        if (tileEntity != null) {
            // rendering in the world
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        }
        if (Minecraft.isAmbientOcclusionEnabled()) GlStateManager.shadeModel(GL11.GL_SMOOTH);
        else GlStateManager.shadeModel(GL11.GL_FLAT);

        GlStateManager.translate(x + xOffset, y + yOffset, z + zOffset);
        GlStateManager.rotate(180f, 0f, 0f, 1f);
        GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(xScale, yScale, zScale);

        bindTexture(texture);
        applyChangesToModel(tileEntity);
        model.render(this.rotation);

        if (tileEntity != null) {
            GlStateManager.disableBlend();
        }

        GlStateManager.disableRescaleNormal();

        GlStateManager.popMatrix();
    }

    public void applyChangesToModel(T tileEntity) {

    }

    public final void setOffset(double xOffset, double yOffset, double zOffset)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }

    private void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public final void setScale(float scale)
    {
        this.xScale = scale;
        this.yScale = scale;
        this.zScale = scale;
    }

    public final void setScale(float xScale, float yScale, float zScale)
    {
        this.xScale = xScale;
        this.yScale = yScale;
        this.zScale = zScale;

    }

}
