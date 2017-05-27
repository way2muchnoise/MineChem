package minechem.achievement;

import betterachievements.api.components.page.ICustomBackground;
import betterachievements.api.components.page.ICustomScale;
import minechem.Compendium;
import minechem.proxy.client.render.RenderHelper;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class ElementAchievementPage extends AchievementPage implements ICustomBackground, ICustomScale
{
    public ElementAchievementPage(String name, Achievement... achievements)
    {
        super(name, achievements);
    }

    @Override
    public void drawBackground(int left, int top, int width, int height, float z, float scale) {
        RenderHelper.drawTexturedRectUV(0, 0, z, 0, 0, 640, 480, width, height, Compendium.Resource.GUI.achievements);
    }

    @Override
    public boolean resetScaleOnLoad() {
        return true;
    }

    @Override
    public float setScale() {
        return 2.0F;
    }

    @Override
    public float getMaxScale() {
        return 3.0F;
    }

    @Override
    public float getMinScale() {
        return 1.0F;
    }
}
