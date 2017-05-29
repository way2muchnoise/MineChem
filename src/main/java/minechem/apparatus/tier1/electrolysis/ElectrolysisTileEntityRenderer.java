package minechem.apparatus.tier1.electrolysis;

import minechem.Compendium;
import minechem.apparatus.prefab.renderer.BasicTileEntityRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class ElectrolysisTileEntityRenderer extends BasicTileEntityRenderer<ElectrolysisTileEntity>
{
    ElectrolysisModel model;

    public ElectrolysisTileEntityRenderer()
    {
        super(0.4F, 0.0625F);

        setOffset(0.5D, 0.6D, 0.5D);

        model = new ElectrolysisModel();
        texture = Compendium.Resource.Model.electrolysis;
    }

    @Override
    public void renderTileEntityAt(ElectrolysisTileEntity tileEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        float rotation = (tileEntity == null ? 0: tileEntity.getBlockMetadata()) * 90.0F;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + xOffset, y + yOffset, z + zOffset);
        GlStateManager.rotate(180f, 0f, 0f, 1f);
        GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.scale(xScale, yScale, zScale);
        bindTexture(texture);
        model.setLeftTube(tileEntity != null && tileEntity.getLeftTube() != null);
        model.setRightTube(tileEntity != null && tileEntity.getRightTube() != null);
        model.render(this.rotation);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

}
