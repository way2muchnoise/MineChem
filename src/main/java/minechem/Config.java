package minechem;

import minechem.helper.LocalizationHelper;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config
{
    public static Configuration config;

    // turns on extra logging printouts
    public static boolean debugMode = false;

    // turns on to copy the newest elements list from jar
    public static boolean useDefaultElements = true;
    // turns on to copy the newest molecules list from jar
    public static boolean useDefaultMolecules = true;
    // turns on to copy the newest researchPages list from jar
    public static boolean useDefaultResearchPages = true;
    // turns on to copy the newest reactions list from jar
    public static boolean useDefaultReactions = false;
    // turns on to copy the newest fuels list from jar
    public static boolean useDefaultFuels = false;

    // makes it that the player can only see his own work in the book
    public static boolean playerPrivateKnowledge = false;

    public static int energyConsumption = 10;
    public static int fluidConsumption = 10;

    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<>();
        list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
        return list;
    }

    public static void init()
    {

        if (config == null)
        {
            config = new Configuration(new File(Compendium.Config.configPrefix + "Minechem.cfg"));
            loadConfig();
        }
    }

    private static void loadConfig()
    {
        Property prop;
        List<String> configList = new ArrayList<String>();

        config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, LocalizationHelper.getLocalString("config.general.description"));

        prop = config.get(Configuration.CATEGORY_GENERAL, "debugMode", Config.debugMode);
        prop.setComment(LocalizationHelper.getLocalString("config.debugMode"));
        prop.setLanguageKey("config.debugMode.tooltip");
        debugMode = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultElements", Config.useDefaultElements);
        prop.setComment(LocalizationHelper.getLocalString("config.useDefaultElements"));
        prop.setLanguageKey("config.useDefaultElements.tooltip");
        useDefaultElements = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultMolecules", Config.useDefaultMolecules);
        prop.setComment(LocalizationHelper.getLocalString("config.useDefaultMolecules"));
        prop.setLanguageKey("config.useDefaultMolecules.tooltip");
        useDefaultMolecules = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultResearchPages", Config.useDefaultResearchPages);
        prop.setComment(LocalizationHelper.getLocalString("config.useDefaultResearchPages"));
        prop.setLanguageKey("config.useDefaultResearchPages.tooltip");
        useDefaultResearchPages = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultReactions", Config.useDefaultReactions);
        prop.setComment(LocalizationHelper.getLocalString("config.useDefaultReactions"));
        prop.setLanguageKey("config.useDefaultReactions.tooltip");
        useDefaultReactions = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "useDefaultFuels", Config.useDefaultFuels);
        prop.setComment(LocalizationHelper.getLocalString("config.useDefaultFuels"));
        prop.setLanguageKey("config.useDefaultFuels.tooltip");
        useDefaultFuels = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "playerPrivateKnowledge", Config.playerPrivateKnowledge);
        prop.setComment(LocalizationHelper.getLocalString("config.playerPrivateKnowledge"));
        prop.setLanguageKey("config.playerPrivateKnowledge.tooltip");
        playerPrivateKnowledge = prop.getBoolean();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "enegryConsumption", Config.energyConsumption);
        prop.setComment(LocalizationHelper.getLocalString("config.energyConsumption"));
        prop.setLanguageKey("config.energyConsumption.tooltip");
        energyConsumption = prop.getInt();
        configList.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "fluidConsumption", Config.fluidConsumption);
        prop.setComment(LocalizationHelper.getLocalString("config.fluidConsumption"));
        prop.setLanguageKey("config.fluidConsumption.tooltip");
        fluidConsumption = prop.getInt();
        configList.add(prop.getName());

        if (config.hasChanged())
        {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Compendium.Naming.id))
        {
            loadConfig();
        }
    }
}
