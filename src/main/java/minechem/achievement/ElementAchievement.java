package minechem.achievement;

import betterachievements.api.components.achievement.ICustomBackgroundColour;
import betterachievements.api.components.achievement.ICustomIconRenderer;
import betterachievements.api.components.achievement.ICustomTitle;
import minechem.Compendium;
import minechem.chemical.Element;
import minechem.helper.ColourHelper;
import minechem.helper.LocalizationHelper;
import minechem.proxy.client.font.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.stats.Achievement;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * {@link net.minecraft.stats.Achievement} wrapper for {@link minechem.chemical.Element}s
 */
@Optional.InterfaceList({
    @Optional.Interface(iface = "betterachievements.api.components.achievement.ICustomBackgroundColour", modid = "betterachievements"),
    @Optional.Interface(iface = "betterachievements.api.components.achievement.ICustomIconRenderer", modid = "betterachievements"),
    @Optional.Interface(iface = "betterachievements.api.components.achievement.ICustomTitle", modid = "betterachievements"),
    })
public class ElementAchievement extends Achievement implements ICustomBackgroundColour, ICustomIconRenderer, ICustomTitle
{
    private final static String achievementPrefix = "achievement.";
    private final static String discoverTitle = "minechem.discover";
    private final static String defaultElementTitle = "achievement.minechem.element";
    private final static String defaultElementDescription = "achievement.minechem.element.desc";
    private final static Achievement nullAchievement = null;

    private final Element element;
    private static Font regularFont, smallFont;

    public ElementAchievement(Element element, int row, int column)
    {
        super(achievementPrefix + element.shortName, discoverTitle, column, row, element.getItemStack(), nullAchievement);
        this.element = element;
        this.initIndependentStat();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getDescription()
    {
        return LocalizationHelper.getFormattedString(defaultElementDescription, element.fullName);
    }

    /**
     * Background colour for the achievement icon
     *
     * @return an int representation of the colour
     */
    @Override
    public int recolourBackground(float greyScale)
    {
        return ColourHelper.blend(element.type.getColour(), ColourHelper.RGB(greyScale, greyScale, greyScale));
    }

    @Override
    public String getTitle() {
        return LocalizationHelper.getFormattedString(defaultElementTitle, element.shortName);
    }

    @Override
    public void renderIcon(int x, int y) {
        if (regularFont == null)
        {
            regularFont = new Font(Minecraft.getMinecraft().fontRenderer);
        }
        if (smallFont == null)
        {
            smallFont = new Font(Minecraft.getMinecraft().fontRenderer).setFontSize(8);
        }
        regularFont.print(element.shortName, x + 10 - (element.shortName.length() - 1) * 4, y + 11, Compendium.Color.TrueColor.white, true);
        smallFont.print(element.atomicNumber, x + 2, y + 2, Compendium.Color.TrueColor.white, true);
    }
}
