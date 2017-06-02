package minechem.apparatus.opticalMicroscope;

import minechem.proxy.client.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MicroscopeRenderItem
{
    public OpticalMicroscopeGUI microscopeGui;

    public MicroscopeRenderItem(OpticalMicroscopeGUI microscopeGui)
    {
        this.microscopeGui = microscopeGui;
    }

    public void renderItemAndEffectIntoGUI(ItemStack itemStack, int mouseX, int mouseY, float z)
    {
        if (itemStack == null)
        {
            return;
        }

        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        RenderHelper.setScissor(microscopeGui.getXSize(), microscopeGui.getYSize(), OpticalMicroscopeGUI.eyePieceX, OpticalMicroscopeGUI.eyePieceY, OpticalMicroscopeGUI.eyePieceW, OpticalMicroscopeGUI.eyePieceH);

        Slot slot = microscopeGui.inventorySlots.getSlotFromInventory(microscopeGui.opticalMicroscope.getInventory(), 0);
        if (slot!= null && !slot.getStack().isEmpty())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(slot.xPos - 16.0F, slot.yPos - 13.0F, z);
            GlStateManager.scale(3.0F, 3.0F, 1.0F);
            Minecraft.getMinecraft().getRenderItem().zLevel = z;
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(slot.getStack(), 0, 0);
            GlStateManager.popMatrix();
        }

        if (itemStack == microscopeGui.getContainer().getInventoryPlayer().getItemStack() && microscopeGui.isMouseInMicroscope())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(mouseX - OpticalMicroscopeGUI.eyePieceX - 8.0F, mouseY - OpticalMicroscopeGUI.eyePieceY - 8.0F, z);
            GlStateManager.scale(3.0F, 3.0F, 1.0F);
            Minecraft.getMinecraft().getRenderItem().zLevel = z;
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, 0, 0);
            GlStateManager.popMatrix();
        }

        if (microscopeGui.isMouseInMicroscope()) {
            GlStateManager.pushMatrix();
            GlStateManager.disableTexture2D();
            Gui.drawRect(OpticalMicroscopeGUI.eyePieceX, OpticalMicroscopeGUI.eyePieceY,
                OpticalMicroscopeGUI.eyePieceX + OpticalMicroscopeGUI.eyePieceW,
                OpticalMicroscopeGUI.eyePieceY + OpticalMicroscopeGUI.eyePieceH,
                0xFFE5E5E5);
            GlStateManager.enableTexture2D();
            GlStateManager.popMatrix();
        }

        RenderHelper.stopScissor();
        GlStateManager.popMatrix();
    }
}
