package minechem.proxy;

import minechem.handler.EventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
    public EntityPlayer findEntityPlayerByName(String name) {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(name);
    }

    public World getClientWorld() {
        return null;
    }

    public EntityPlayer getPlayer(MessageContext context) {
        return context.getServerHandler().player;
    }

    public EntityPlayer getPlayer() {
        return null;
    }

    public World getWorld(MessageContext context) {
        return context.getServerHandler().player.world;
    }

    public void registerRenderers() {
        // NOOP
    }

    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public String getCurrentSaveDir() {
        return DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath();
    }

    public String getCurrentLanguage() {
        return "en_US";
    }

    public void registerFonts() {
        // NOOP
    }
}
