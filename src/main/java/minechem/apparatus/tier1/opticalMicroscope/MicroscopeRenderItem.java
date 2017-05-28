package minechem.apparatus.tier1.opticalMicroscope;

import minechem.proxy.client.render.RenderHelper;
import net.minecraft.client.Minecraft;
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

    public void renderItemAndEffectIntoGUI(ItemStack itemStack, int x, int y)
    {
        if (itemStack == null)
        {
            return;
        }

        RenderHelper.enableGUIStandardItemLighting();

        Slot slot = microscopeGui.inventorySlots.getSlotFromInventory(microscopeGui.opticalMicroscope, 0);
        if (slot.getStack() != null && !slot.getStack().isEmpty())
        {
            GlStateManager.pushMatrix();
            RenderHelper.setScissor(microscopeGui.getXSize(), microscopeGui.getYSize(), OpticalMicroscopeGUI.eyePieceX, OpticalMicroscopeGUI.eyePieceY, OpticalMicroscopeGUI.eyePieceW, OpticalMicroscopeGUI.eyePieceH);
            int renderX = microscopeGui.getGuiLeft() + slot.xPos;
            int renderY = microscopeGui.getGuiTop() + slot.yPos;
            GlStateManager.translate(renderX, renderY, 0.0F);
            GlStateManager.scale(3.0F, 3.0F, 1.0F);
            GlStateManager.translate(-renderX - 5.4F, -renderY - 4.5F, 540.0F);
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(slot.getStack(), renderX, renderY);
            RenderHelper.stopScissor();
            GlStateManager.popMatrix();
        }

        if (itemStack == microscopeGui.getContainer().getInventoryPlayer().getItemStack() && microscopeGui.isMouseInMicroscope())
        {
            GlStateManager.pushMatrix();
            RenderHelper.setScissor(microscopeGui.getXSize(), microscopeGui.getYSize(), OpticalMicroscopeGUI.eyePieceX, OpticalMicroscopeGUI.eyePieceY, OpticalMicroscopeGUI.eyePieceW, OpticalMicroscopeGUI.eyePieceH);
            GlStateManager.translate(x, y, 0.0F);
            GlStateManager.scale(3.0F, 3.0F, 1.0F);
            GlStateManager.translate(-x - 8.0F, -y - 8.0F, 540.0F);
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
            RenderHelper.stopScissor();
            GlStateManager.popMatrix();
        }
    }
}
