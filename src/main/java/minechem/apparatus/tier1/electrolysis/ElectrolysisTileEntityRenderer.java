package minechem.apparatus.tier1.electrolysis;

import minechem.Compendium;
import minechem.apparatus.prefab.renderer.BasicTileEntityRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class ElectrolysisTileEntityRenderer extends BasicTileEntityRenderer<ElectrolysisTileEntity, ElectrolysisModel>
{
    public ElectrolysisTileEntityRenderer()
    {
        super(0.4F, 0.0625F);

        setOffset(0.5D, 0.6D, 0.5D);

        model = new ElectrolysisModel();
        texture = Compendium.Resource.Model.electrolysis;
    }

    public void applyChangesToModel(ElectrolysisTileEntity tileEntity) {
        if (tileEntity != null) {
            model.setLeftTube(tileEntity.hasLeftTube());
            model.setRightTube(tileEntity.hasRightTube());
        }
    }

}
