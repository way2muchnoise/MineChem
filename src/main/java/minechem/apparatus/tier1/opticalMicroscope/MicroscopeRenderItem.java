package minechem.apparatus.tier1.opticalMicroscope;

import minechem.proxy.client.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class MicroscopeRenderItem
{
    public OpticalMicroscopeGUI microscopeGui;

    public MicroscopeRenderItem()
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
        if (slot.getStack() != null)
        {
            GL11.glPushMatrix();
            RenderHelper.setScissor(microscopeGui.getXSize(), microscopeGui.getYSize(), OpticalMicroscopeGUI.eyePieceX, OpticalMicroscopeGUI.eyePieceY, OpticalMicroscopeGUI.eyePieceW, OpticalMicroscopeGUI.eyePieceH);
            int renderX = microscopeGui.getGuiLeft() + slot.xPos;
            int renderY = microscopeGui.getGuiTop() + slot.yPos;
            GL11.glTranslatef(renderX, renderY, 0.0F);
            GL11.glScalef(3.0F, 3.0F, 1.0F);
            GL11.glTranslatef(-renderX - 5.4F, -renderY - 4.5F, 540.0F);
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(slot.getStack(), renderX, renderY);
            RenderHelper.stopScissor();
            GL11.glPopMatrix();
        }

        if (itemStack == microscopeGui.getContainer().getInventoryPlayer().getItemStack() && microscopeGui.isMouseInMicroscope())
        {
            GL11.glPushMatrix();
            RenderHelper.setScissor(microscopeGui.getXSize(), microscopeGui.getYSize(), OpticalMicroscopeGUI.eyePieceX, OpticalMicroscopeGUI.eyePieceY, OpticalMicroscopeGUI.eyePieceW, OpticalMicroscopeGUI.eyePieceH);
            GL11.glTranslatef(x, y, 0.0F);
            GL11.glScalef(3.0F, 3.0F, 1.0F);
            GL11.glTranslatef(-x - 8.0F, -y - 8.0F, 540.0F);
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
            RenderHelper.stopScissor();
            GL11.glPopMatrix();
        }
    }
}
