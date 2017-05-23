package minechem.proxy;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import minechem.handler.EventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
    public static int RENDER_ID;
    public static int ISBRH_ID;

    public EntityPlayer findEntityPlayerByName(String name)
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(name);
    }

    public World getClientWorld()
    {
        return null;
    }

    public EntityPlayer getPlayer(MessageContext context)
    {
        return context.getServerHandler().player;
    }

    public World getWorld(MessageContext context)
    {
        return context.getServerHandler().player.world;
    }

    public void registerRenderers()
    {
        // NOOP
    }

    public void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public String getCurrentSaveDir()
    {
        return DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath();
    }

    public void registerResourcesListener()
    {
        // NOOP
    }

    public String getCurrentLanguage()
    {
        return "en_US";
    }

    public void registerFonts()
    {
        // NOOP
    }
}
