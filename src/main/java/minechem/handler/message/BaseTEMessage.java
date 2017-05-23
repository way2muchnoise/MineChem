package minechem.handler.message;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Basic message for {@link net.minecraft.tileentity.TileEntity} T represents the {@link net.minecraft.tileentity.TileEntity}
 */
public abstract class BaseTEMessage<T extends TileEntity> extends BaseMessage implements IMessage
{
    private BlockPos pos;

    /**
     * Constructor needed for reflection
     */
    public BaseTEMessage()
    {
    }

    /**
     * Basic Constructor using the TileEntity Use super(myTE); in subClasses
     *
     * @param entity
     */
    public BaseTEMessage(T entity)
    {
        this.pos = entity.getPos();
    }

    /**
     * Read values from ByteBuf Use super(buf); in subClasses
     *
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.pos = BlockPos.fromLong(buf.readLong());
    }

    /**
     * Write values to ByteBuf Use super(buf); in subClasses
     *
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(this.pos.toLong());
    }

    /**
     * Gets the TileEntity
     *
     * @param message
     * @param ctx
     * @return can be null
     */
    public T getTileEntity(BaseTEMessage message, MessageContext ctx)
    {
        TileEntity tileEntity = getWorld(ctx).getTileEntity(message.pos);
        return tileEntity == null ? null : (T) tileEntity;
    }
}
