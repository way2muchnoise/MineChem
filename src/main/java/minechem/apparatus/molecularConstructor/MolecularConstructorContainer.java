package minechem.apparatus.molecularConstructor;

import minechem.apparatus.prefab.gui.container.BasicContainer;
import minechem.apparatus.prefab.gui.container.ChemicalItemSlot;
import minechem.apparatus.prefab.gui.container.GhostItemSlot;
import net.minecraft.entity.player.InventoryPlayer;

public class MolecularConstructorContainer extends BasicContainer {
    private static final int inFirstSlotX = 52, inFirstSlotY = 20;
    private static final int outSlotX = 141, outSlotY = 37;
    private static final int previewSlotX = 114, previewSlotY = 49;

    public MolecularConstructorContainer(InventoryPlayer inventoryPlayer, MolecularConstructorTileEntity molecularConstructor) {
        bindPlayerInventory(inventoryPlayer);
        for (int i = 0; i < molecularConstructor.getInput().getSizeInventory(); i++) {
            addSlotToContainer(new ChemicalItemSlot(molecularConstructor.getInput(), i, inFirstSlotX + (i / 3) * 18, inFirstSlotY + (i % 3) * 18));
        }
        addSlotToContainer(new GhostItemSlot(previewSlotX, previewSlotY, molecularConstructor::getCurrentRecipe));
        addSlotToContainer(new ChemicalItemSlot(molecularConstructor.getOutput(), 0, outSlotX, outSlotY));
    }
}
