package minechem.handler.message;

import minechem.apparatus.itemPrinter.ItemPrinterTileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ItemPrinterMessage extends BaseTEMessage<ItemPrinterTileEntity> {

    public ItemPrinterMessage() {

    }

    public ItemPrinterMessage(ItemPrinterTileEntity itemPrinter) {
        super(itemPrinter);
    }

    public static class Handler extends MessageHandler<ItemPrinterMessage> {

        @Override
        public void handle(ItemPrinterMessage message, MessageContext ctx) {
            ItemPrinterTileEntity itemPrinter = getTileEntity(message, ctx);
            if (itemPrinter != null) {
                itemPrinter.startProcessing();
            }
        }
    }
}