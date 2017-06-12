package minechem;

import minechem.breakdown.DefaultReverseRecipes;
import minechem.chemical.process.ChemicalFuelHandler;
import minechem.chemical.process.DefaultProcesses;
import minechem.command.CommandHandler;
import minechem.handler.*;
import minechem.helper.LogHelper;
import minechem.proxy.CommonProxy;
import minechem.registry.BlockRegistry;
import minechem.registry.CreativeTabRegistry;
import minechem.registry.ItemRegistry;
import minechem.registry.JournalRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Compendium.Naming.id, name = Compendium.Naming.name, version = Compendium.Version.full, useMetadata = false, guiFactory = "minechem.proxy.client.gui.GuiFactory", acceptedMinecraftVersions = "[1.11.2,)", dependencies = "required-after:forge@[13.20.0.2282,);required-after:fontbox@[0.0.3,)")
public class Minechem {
    // Instancing
    @Instance(value = Compendium.Naming.id)
    public static Minechem INSTANCE;

    // Public extra data about our mod that Forge uses in the mods listing page for more information.
    @Mod.Metadata(Compendium.Naming.id)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "minechem.proxy.client.ClientProxy", serverSide = "minechem.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.debug("Setting up ModMetaData");
        metadata = Compendium.MetaData.init(metadata);

        // Load configuration.
        LogHelper.debug("Loading configuration...");
        Config.init();
        MinecraftForge.EVENT_BUS.register(new Config());

        LogHelper.debug("Registering Messages...");
        MessageHandler.init();

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

        LogHelper.debug("Initializing CreativeTabs...");
        CreativeTabRegistry.init();

        // Register Event Handlers
        LogHelper.debug("Registering Event Handlers...");
        proxy.registerEventHandlers();

        LogHelper.debug("Registering Fuel Handler...");
        GameRegistry.registerFuelHandler(new ChemicalFuelHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        LogHelper.debug("Registering crafting recipes...");
        Recipes.register();

        LogHelper.debug("Registering Journal...");
        JournalRegistry.init();

        LogHelper.debug("Registering default chemical processes...");
        DefaultProcesses.register();

        LogHelper.debug("Registering custom chemical processes...");
        ReactionHandler.init();

        LogHelper.debug("Registering Chemical fuels...");
        FuelHandler.init();

        LogHelper.debug("Registering Item Printing Recipes...");
        PrintingHandler.init();

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
    public void postInit(FMLPostInitializationEvent event) {
        LogHelper.debug("Registering Reverse Recipes...");
        DefaultReverseRecipes.init();

        LogHelper.info("Minechem has loaded");
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        LogHelper.debug("Loading player research...");
        ResearchHandler.readPlayerResearch();

        LogHelper.debug("Registering Commands...");
        event.registerServerCommand(new CommandHandler());
    }

    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        LogHelper.debug("Saving player research...");
        ResearchHandler.saveResearch();
    }
}
