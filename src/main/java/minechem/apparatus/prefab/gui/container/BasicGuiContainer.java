package minechem.apparatus.prefab.gui.container;

import minechem.Compendium;
import minechem.apparatus.prefab.gui.element.GuiElement;
import minechem.helper.LocalizationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Basic GUI container class for extending
 */
public abstract class BasicGuiContainer extends GuiContainer
{
    protected ResourceLocation texture;
    protected String name;
    private BasicContainer container;
    private List<GuiElement> elements;

    public BasicGuiContainer(BasicContainer container)
    {
        super(container);
        this.container = container;
        this.elements = new LinkedList<>();
    }

    public void addGuiElement(GuiElement element) {
        this.elements.add(element);
    }

    public BasicContainer getContainer()
    {
        return container;
    }

    public int getXSize()
    {
        return xSize;
    }

    public int getYSize()
    {
        return ySize;
    }

    public EntityPlayer getPlayer()
    {
        return Minecraft.getMinecraft().player;
    }

    public World getWorld()
    {
        return FMLClientHandler.instance().getWorldClient();
    }

    public void bindTexture(ResourceLocation texture) {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (texture != null) {
            bindTexture(texture);
            drawTexturedModalRect(guiLeft, guiTop, 0, 0,176, 166);
        }
        for (GuiElement element : elements) {
            GlStateManager.pushMatrix();
            element.drawBackground(guiLeft, guiTop, mouseX, mouseY);
            GlStateManager.popMatrix();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawString(fontRenderer, LocalizationHelper.getLocalString(name), 5, 5, Compendium.Color.TrueColor.white);
        for (GuiElement element : elements) {
            GlStateManager.pushMatrix();
            element.drawForeground(guiLeft, guiTop, mouseX, mouseY);
            GlStateManager.popMatrix();
        }
    }
}
