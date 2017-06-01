package minechem.helper;

import minechem.handler.MessageHandler;
import minechem.handler.message.ResearchMessage;
import minechem.registry.ResearchRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class ResearchHelper
{
    public static void addResearch(EntityPlayer player, String key, boolean isRemote)
    {
        if (isRemote)
            MessageHandler.INSTANCE.sendToServer(new ResearchMessage(key));
        ResearchRegistry.getInstance().addResearch(player, key);
    }

    public static boolean hasResearch(EntityPlayer player, String key) {
        return ResearchRegistry.getInstance().hasUnlockedResearch(player, key);
    }
}
