package minechem.handler.message;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Basic message for {@link net.minecraft.tileentity.TileEntity} T represents the {@link net.minecraft.tileentity.TileEntity}
 */
public abstract class BaseTEMessage<T extends TileEntity> implements IMessage
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

    public BlockPos getPos() {
        return pos;
    }
}
