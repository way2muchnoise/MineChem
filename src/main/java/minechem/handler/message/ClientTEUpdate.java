package minechem.handler.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import minechem.apparatus.prefab.tileEntity.BasicTileEntity;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientTEUpdate extends BaseTEMessage<BasicTileEntity> {
    private NBTTagCompound updateTag;

    public ClientTEUpdate() {

    }

    public ClientTEUpdate(BasicTileEntity entity) {
        super(entity);
        updateTag = entity.getUpdateTag();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        try {
            CompressedStreamTools.write(updateTag, new ByteBufOutputStream(buf));
        } catch (IOException e) {
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        try {
            updateTag = CompressedStreamTools.read(new DataInputStream(new ByteBufInputStream(buf)));
        } catch (IOException e) {
            updateTag = null;
        }
    }

    public static class Handler extends MessageHandler<ClientTEUpdate> {
        @Override
        public void handle(ClientTEUpdate message, MessageContext ctx) {
            if (message.updateTag != null) {
                getTileEntity(message, ctx).handleUpdateTag(message.updateTag);
            }
        }
    }
}
