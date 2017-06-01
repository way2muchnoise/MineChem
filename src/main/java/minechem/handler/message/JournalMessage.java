package minechem.handler.message;

import io.netty.buffer.ByteBuf;
import minechem.item.journal.JournalItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

/**
 * Message used to write knowledge in on an journal on the server side
 */
public class JournalMessage extends BaseTEMessage
{
    private String uuid;

    public JournalMessage() {

    }

    public JournalMessage(EntityPlayer player)
    {
        this(player.getUniqueID());
    }

    public JournalMessage(UUID uuid)
    {
        this.uuid = uuid.toString();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        int length = buf.readInt();
        this.uuid = new String(buf.readBytes(length).array());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.uuid.length());
        buf.writeBytes(this.uuid.getBytes());
    }

    public static class Handler extends MessageHandler<JournalMessage> {
        @Override
        public void handle(JournalMessage message, MessageContext ctx) {
            EntityPlayer player = getPlayer(ctx);
            if (player.getUniqueID().equals(UUID.fromString(message.uuid))) {
                if (player.getActiveItemStack().getItem() instanceof JournalItem) {
                    ItemStack journalStack = player.getActiveItemStack();
                    JournalItem journalItem = (JournalItem) journalStack.getItem();
                    journalItem.writeKnowledge(journalStack, player, false);
                }
            }
        }
    }
}
