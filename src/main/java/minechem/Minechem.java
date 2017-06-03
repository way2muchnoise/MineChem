package minechem;

import minechem.chemical.process.DefaultProcesses;
import minechem.handler.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import minechem.helper.LogHelper;
import minechem.proxy.CommonProxy;
import minechem.registry.BlockRegistry;
import minechem.registry.CreativeTabRegistry;
import minechem.registry.ItemRegistry;
import minechem.registry.JournalRegistry;
import minechem.registry.RecipeRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = Compendium.Naming.id, name = Compendium.Naming.name, version = Compendium.Version.full, useMetadata = false, guiFactory = "minechem.proxy.client.gui.GuiFactory", acceptedMinecraftVersions = "[1.11.2,)", dependencies = "required-after:forge@[13.20.0.2282,);required-after:fontbox@[0.0.3,)")
public class Minechem
{
    // Instancing
    @Instance(value = Compendium.Naming.id)
    public static Minechem INSTANCE;

    // Public extra data about our mod that Forge uses in the mods listing page for more information.
    @Mod.Metadata(Compendium.Naming.id)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "minechem.proxy.client.ClientProxy", serverSide = "minechem.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Load configuration.
        LogHelper.debug("Loading configuration...");
        Config.init();
        MinecraftForge.EVENT_BUS.register(new Config());

        LogHelper.debug("Registering Packets...");
        MessageHandler.init();

        LogHelper.debug("Setting up ModMetaData");
        metadata = Compendium.MetaData.init(metadata);

        // Register Elements and Molecules before constructing items
        LogHelper.debug("Registering Elements...");
        ElementHandler.init();

        LogHelper.debug("Registering Molecules...");
        MoleculeHandler.init();

        // Register items and blocks.
        LogHelper.debug("Registering Items...");
        ItemRegistry.init(event.getSide());

        LogHelper.debug("Registering Blocks...");
        BlockRegistry.init(event.getSide());

        LogHelper.debug("Registering CreativeTabs...");
        CreativeTabRegistry.init();

        // Register Event Handlers
        LogHelper.debug("Registering Event Handlers...");
        proxy.registerEventHandlers();

        LogHelper.debug("Registering Journal...");
        JournalRegistry.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.debug("Registering Recipes...");
        RecipeRegistry.getInstance().init();

        LogHelper.debug("Registering default chemical processes...");
        DefaultProcesses.register();

        LogHelper.debug("Registering custom chemical processes...");
        ReactionsHandler.init();

        LogHelper.debug("Registering GUI and Container handlers...");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        LogHelper.debug("Registering Renderers...");
        proxy.registerRenderers();

        LogHelper.debug("Registering Fonts...");
        proxy.registerFonts();

        LogHelper.debug("Registering Achievements...");
        AchievementHandler.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Minechem has loaded");
    }

    @EventHandler
    public void onServerStarted(FMLServerStartedEvent event)
    {
        ResearchHandler.readPlayerResearch();
    }

    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event)
    {
        ResearchHandler.saveResearch();
    }
}
