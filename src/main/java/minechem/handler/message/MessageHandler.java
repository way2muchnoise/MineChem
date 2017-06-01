package minechem.handler.message;

import minechem.Minechem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class MessageHandler<M extends IMessage> implements IMessageHandler<M, IMessage> {
    @Override
    public IMessage onMessage(M message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
        } else {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
        }
        return null;
    }

    public abstract void handle(M message, MessageContext ctx);

    /**
     * Get the World from the MessageContext
     *
     * @param ctx
     * @return the current World
     */
    public World getWorld(MessageContext ctx)
    {
        return Minechem.proxy.getWorld(ctx);
    }

    /**
     * Get the EntityPlayer from the MessageContext
     *
     * @param ctx
     * @return the current EntityPlayer
     */
    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return Minechem.proxy.getPlayer(ctx);
    }

    /**
     * Gets the TileEntity
     *
     * @param message
     * @param ctx
     * @return can be null
     */
    public  <T extends TileEntity> T getTileEntity(BaseTEMessage<T> message, MessageContext ctx)
    {
        TileEntity tileEntity = getWorld(ctx).getTileEntity(message.getPos());
        return tileEntity == null ? null : (T) tileEntity;
    }
}
