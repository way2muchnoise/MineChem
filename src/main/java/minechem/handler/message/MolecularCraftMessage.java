package minechem.handler.message;

import minechem.apparatus.molecularConstructor.MolecularConstructorTileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MolecularCraftMessage extends BaseTEMessage<MolecularConstructorTileEntity> {

    public MolecularCraftMessage() {

    }

    public MolecularCraftMessage(MolecularConstructorTileEntity molecularConstructor) {
        super(molecularConstructor);
    }

    public static class Handler extends MessageHandler<MolecularCraftMessage> {

        @Override
        public void handle(MolecularCraftMessage message, MessageContext ctx) {
            MolecularConstructorTileEntity molecularConstructor = getTileEntity(message, ctx);
            if (molecularConstructor != null) {
                molecularConstructor.startProcessing();
            }
        }
    }
}
