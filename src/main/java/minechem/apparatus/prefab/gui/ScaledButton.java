package minechem.apparatus.prefab.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public class ScaledButton extends GuiButton {
    private float scale;

    public ScaledButton(int buttonId, int x, int y, int width, float scale, String buttonText) {
        super(buttonId, x, y, MathHelper.floor(width / scale), 20, buttonText);
        this.scale = scale;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.xPosition, this.yPosition, 0);
        GlStateManager.scale(scale, scale, 0);
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = isHovered(mouseX, mouseY);
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(0, 0, 0, 46 + i * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.width / 2, 0, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);

            int j = 14737632;
            if (packedFGColour != 0) {
                j = packedFGColour;
            } else if (!this.enabled) {
                j = 10526880;
            } else if (this.hovered) {
                j = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.width / 2, (this.height - 8) / 2, j);
        }
        GlStateManager.popMatrix();
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && isHovered(mouseX, mouseY);
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseY >= this.yPosition
            && mouseX < this.xPosition + this.width * scale
            && mouseY < this.yPosition + this.height * scale;
    }
}
