package minechem.apparatus.tier1.opticalMicroscope;

import minechem.Compendium;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.chemical.ChemicalBase;
import minechem.chemical.Element;
import minechem.chemical.Molecule;
import minechem.helper.AchievementHelper;
import minechem.helper.LocalizationHelper;
import minechem.helper.ResearchHelper;
import minechem.item.chemical.ChemicalItem;
import minechem.proxy.client.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class OpticalMicroscopeGUI extends BasicGuiContainer
{
    private ItemStack prevStack;
    protected OpticalMicroscopeTileEntity opticalMicroscope;
    protected static final int eyePieceX = 13;
    protected static final int eyePieceY = 16;
    protected static final int eyePieceW = 54;
    protected static final int eyePieceH = 54;
    private MicroscopeRenderItem renderItem;
    private int mouseX, mouseY;

    public OpticalMicroscopeGUI(InventoryPlayer inventoryPlayer, OpticalMicroscopeTileEntity opticalMicroscope)
    {
        super(new OpticalMicroscopeContainer(inventoryPlayer, opticalMicroscope));
        this.opticalMicroscope = opticalMicroscope;
        this.name = opticalMicroscope.getName();
        this.texture = Compendium.Resource.GUI.opticalMicroscope;
        this.renderItem = new MicroscopeRenderItem(this);
    }

    public boolean isMouseInMicroscope()
    {
        return mouseX >= eyePieceX && mouseX <= eyePieceX + eyePieceW && mouseY >= eyePieceY && mouseY <= eyePieceY + eyePieceH;
    }

    private void drawMicroscopeOverlay()
    {
        RenderHelper.resetOpenGLColour();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        float z = this.zLevel;
        this.zLevel = 300.0F;
        GlStateManager.disableBlend();
        drawTexturedModalRect(eyePieceX, eyePieceY, 176, eyePieceH, eyePieceH, eyePieceW);
        GlStateManager.enableBlend();
        renderItem.renderItemAndEffectIntoGUI(opticalMicroscope.getInventory().getStackInSlot(0), mouseX, mouseY, this.zLevel);
        renderItem.renderItemAndEffectIntoGUI(getContainer().getInventoryPlayer().getItemStack(), mouseX, mouseY, this.zLevel);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(eyePieceX, eyePieceY, 176, 0, eyePieceH, eyePieceW);
        this.zLevel = z;
    }

    private void drawInfo()
    {
        Slot slot = inventorySlots.getSlotFromInventory(opticalMicroscope.getInventory(), 0);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            if (itemStack.getItem() instanceof ChemicalItem)
            {
                ChemicalBase chemicalBase = ChemicalBase.readFromNBT(itemStack.getTagCompound());
                fontRenderer.drawString(chemicalBase.fullName, eyePieceX + eyePieceH + 3, eyePieceY, 0);
                fontRenderer.drawString(LocalizationHelper.getLocalString("chemical.formula"), eyePieceX + eyePieceH + 3, eyePieceY + 10, 0);
                fontRenderer.drawString(chemicalBase.getFormula(), eyePieceX + eyePieceH + 3, eyePieceY + 20, 0);

                if (prevStack != itemStack) {
                    prevStack = itemStack;
                    if (chemicalBase.isElement()) {
                        AchievementHelper.giveAchievement(getPlayer(), (Element) chemicalBase, getWorld().isRemote);
                    }
                    ResearchHelper.addResearch(getPlayer(), chemicalBase.getResearchKey(), getWorld().isRemote);
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.mouseX = mouseX - guiLeft;
        this.mouseY = mouseY - guiTop;
        drawMicroscopeOverlay();
        drawInfo();
    }
}
