package minechem.apparatus.itemPrinter;

import minechem.Compendium;
import minechem.apparatus.prefab.gui.ScaledButton;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.apparatus.prefab.gui.element.GuiArrow;
import minechem.apparatus.prefab.gui.element.GuiEnergyBar;
import minechem.apparatus.prefab.gui.element.GuiFluidTank;
import minechem.helper.LocalizationHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;

import java.io.IOException;

public class ItemPrinterGUI extends BasicGuiContainer {
    private static int arrowX = 116, arrowY = 38;

    private GuiButton craftButton;
    private ItemPrinterTileEntity itemPrinter;

    public ItemPrinterGUI(InventoryPlayer inventoryPlayer, ItemPrinterTileEntity itemPrinter) {
        super(new ItemPrinterContainer(inventoryPlayer, itemPrinter));
        this.texture = Compendium.Resource.GUI.itemPrinter;
        this.name = itemPrinter.getName();
        this.itemPrinter = itemPrinter;
        addGuiElement(new GuiArrow(arrowX - 1, arrowY - 1, itemPrinter::getProgression, true));
        addGuiElement(new GuiEnergyBar(itemPrinter.getEnergy(), 10, 25));
        addGuiElement(new GuiFluidTank(itemPrinter.getTank(), 30, 25));
    }

    @Override
    public void initGui() {
        super.initGui();
        craftButton = addButton(new ScaledButton(0, guiLeft + 120, guiTop + 17, 42, 0.75f, LocalizationHelper.getLocalString("gui.print")));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (craftButton.id == button.id) {
            itemPrinter.startProcessing();
        }
    }
}
