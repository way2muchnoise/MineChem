package minechem.handler;

import minechem.handler.message.ResearchSyncMessage;
import minechem.registry.ResearchRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

/**
 * Holds all event triggered methods
 */
public class EventHandler
{
    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event)
    {
        ResearchHandler.saveResearch();
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event)
    {
        ResearchHandler.saveResearch();
    }

    @SubscribeEvent
    public void onClientConnnect(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
            Collection<String> keys = ResearchRegistry.getInstance().getResearchFor(player);
            if (keys != null && !keys.isEmpty()) {
                MessageHandler.INSTANCE.sendTo(new ResearchSyncMessage(keys), player);
            }
        }
    }
}
