package minechem.apparatus.molecularConstructor;

import minechem.Compendium;
import minechem.apparatus.prefab.gui.ScaledButton;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.apparatus.prefab.gui.element.GuiArrow;
import minechem.apparatus.prefab.gui.element.GuiEnergyBar;
import minechem.helper.LocalizationHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;

import java.io.IOException;

public class MolecularConstructorGUI extends BasicGuiContainer {
    private static int arrowX = 110, arrowY = 38;

    private GuiButton craftButton;
    private MolecularConstructorTileEntity molecularConstructor;

    public MolecularConstructorGUI(InventoryPlayer inventoryPlayer, MolecularConstructorTileEntity molecularConstructor) {
        super(new MolecularConstructorContainer(inventoryPlayer, molecularConstructor));
        this.texture = Compendium.Resource.GUI.molecularConstructor;
        this.name = molecularConstructor.getName();
        this.molecularConstructor = molecularConstructor;
        addGuiElement(new GuiArrow(arrowX - 1, arrowY - 1, molecularConstructor::getProgression, true));
        addGuiElement(new GuiEnergyBar(molecularConstructor.getEnergy(), 15, 25));
    }

    @Override
    public void initGui() {
        super.initGui();
        craftButton = addButton(new ScaledButton(0, guiLeft + 120, guiTop + 17, 42, 0.75f, LocalizationHelper.getLocalString("gui.construct")));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (craftButton.id == button.id) {
            molecularConstructor.startProcessing();
        }
    }
}
