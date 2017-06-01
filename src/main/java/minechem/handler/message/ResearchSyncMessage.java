package minechem.handler.message;

import io.netty.buffer.ByteBuf;
import minechem.registry.ResearchRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.Collection;

public class ResearchSyncMessage implements IMessage {
    private Collection<String> keys;

    public ResearchSyncMessage() {

    }

    public ResearchSyncMessage(Collection<String> keys) {
        this.keys = keys;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int size = buf.readInt();
        this.keys = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int length = buf.readInt();
            byte[] bytes = buf.readBytes(length).array();
            this.keys.add(new String(bytes));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.keys.size());
        for (String key : this.keys) {
            byte[] bytes = key.getBytes();
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        }
    }

    public static class Handler extends MessageHandler<ResearchSyncMessage> {
        @Override
        public void handle(ResearchSyncMessage message, MessageContext ctx) {
            EntityPlayer player = getPlayer(ctx);
            for (String key : message.keys) {
                ResearchRegistry.getInstance().addResearch(player, key);
            }
        }
    }
}
