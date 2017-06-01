package minechem.handler.message;

import io.netty.buffer.ByteBuf;
import minechem.chemical.Element;
import minechem.helper.AchievementHelper;
import minechem.registry.ElementRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Used for triggering achievements from Client only code
 */
public class AchievementMessage extends BaseTEMessage
{
    private String achievement;
    private boolean isElement;

    public AchievementMessage() {

    }

    public AchievementMessage(String achievement)
    {
        this.achievement = achievement;
        this.isElement = false;
    }

    public AchievementMessage(Element element)
    {
        this.achievement = element.shortName;
        this.isElement = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.isElement = buf.readBoolean();
        int length = buf.readInt();
        this.achievement = new String(buf.readBytes(length).array());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(this.isElement);
        buf.writeInt(this.achievement.length());
        buf.writeBytes(this.achievement.getBytes());
    }

    public static class Handler extends MessageHandler<AchievementMessage> {
        @Override
        public void handle(AchievementMessage message, MessageContext ctx) {
            if (message.isElement) {
                AchievementHelper.giveAchievement(getPlayer(ctx), AchievementHelper.getAchievement(ElementRegistry.getInstance().getElement(message.achievement)));
            } else {
                AchievementHelper.giveAchievement(getPlayer(ctx), AchievementHelper.getAchievement(message.achievement));
            }
        }
    }
}
