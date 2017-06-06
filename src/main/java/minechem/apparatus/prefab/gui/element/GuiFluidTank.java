package minechem.apparatus.prefab.gui.element;

import minechem.Compendium;
import minechem.helper.ColourHelper;
import minechem.helper.LocalizationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.lwjgl.opengl.GL11;

import java.util.LinkedList;
import java.util.List;

/**
 * A tank to display in a GUI
 *
 * @author way2muchnoise
 */
public class GuiFluidTank extends GuiElement {
    private IFluidTank tank;
    private int colour;

    /**
     * Make a take with given properties
     *
     * @param tank   the tank to display
     * @param posX   the x pos of the element (origin is the left-top of the parent gui)
     * @param posY   the y pos of the element (origin is the left-top of the parent gui)
     * @param width  the width of the element
     * @param height the height of the element
     */
    public GuiFluidTank(IFluidTank tank, int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        this.tank = tank;
        this.colour = Compendium.Color.TrueColor.blue;
    }

    /**
     * Make a tank using the default width and height
     *
     * @param tank the tank to display
     * @param posX the x pos of the element (origin is the left-top of the parent gui)
     * @param posY the y pos of the element (origin is the left-top of the parent gui)
     */
    public GuiFluidTank(IFluidTank tank, int posX, int posY) {
        this(tank, posX, posY, 18, 39);
    }

    /**
     * Set the colour of the line around the tank
     *
     * @param colour in Integer form
     * @return the GuiFluidTank
     */
    public GuiFluidTank setColour(int colour) {
        this.colour = colour;
        return this;
    }

    @Override
    public void drawBackground(int guiLeft, int guiTop, int mouseX, int mouseY) {
        GlStateManager.color(ColourHelper.getRed(colour), ColourHelper.getGreen(colour), ColourHelper.getBlue(colour));
        bindTexture(Compendium.Resource.GUI.guiElements);
        drawTexturedModalRectScaled(guiLeft + posX, guiTop + posY, 0, 0, 18, 39, width, height);

        GlStateManager.color(1.0F, 1.0F, 1.0F);
        FluidStack fluidStack = tank.getFluid();
        if (fluidStack != null && fluidStack.amount > 0) {
            TextureAtlasSprite icon = getStillTexture(fluidStack);

            int width = this.width - 4;
            int height = this.height - 4;
            int renderAmount = Math.max(Math.min(height, tank.getFluidAmount() * height / tank.getCapacity()), 1);
            int posY = guiTop + this.posY + 2 + height - renderAmount;

            bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            int color = fluidStack.getFluid().getColor(fluidStack);
            GlStateManager.color((color >> 16 & 0xFF), (color >> 8 & 0xFF), (color & 0xFF));

            GlStateManager.enableBlend();
            for (int i = 0; i < width; i += 16) {
                for (int j = 0; j < renderAmount; j += 16) {
                    int drawWidth = Math.min(width - i, 16);
                    int drawHeight = Math.min(renderAmount - j, 16);

                    int drawX = guiLeft + this.posX + 2 + i;
                    int drawY = posY + j;

                    double minU = icon.getMinU();
                    double maxU = icon.getMaxU();
                    double minV = icon.getMinV();
                    double maxV = icon.getMaxV();

                    Tessellator tessellator = Tessellator.getInstance();
                    VertexBuffer tes = tessellator.getBuffer();
                    tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                    tes.pos(drawX, drawY + drawHeight, 0).tex(minU, minV + (maxV - minV) * drawHeight / 16F).endVertex();
                    tes.pos(drawX + drawWidth, drawY + drawHeight, 0).tex(minU + (maxU - minU) * drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F).endVertex();
                    tes.pos(drawX + drawWidth, drawY, 0).tex(minU + (maxU - minU) * drawWidth / 16F, minV).endVertex();
                    tes.pos(drawX, drawY, 0).tex(minU, minV).endVertex();
                    tessellator.draw();
                }
            }
            GlStateManager.disableBlend();

            GlStateManager.color(1.0F, 1.0F, 1.0F);
            bindTexture(Compendium.Resource.GUI.guiElements);
            drawTexturedModalRectScaled(guiLeft + posX + 2, guiTop + posY + 1, 1, 1, 15, 37 - ((int) ((38) * ((float) fluidStack.amount / tank.getCapacity()))), width - 3, height - 2 - ((int) ((height - 1) * ((float) fluidStack.amount / tank.getCapacity()))));
        }

        bindTexture(Compendium.Resource.GUI.guiElements);
        drawTexturedModalRectScaled(guiLeft + posX, guiTop + posY, 18, 0, 18, 39, width, height);
    }

    @Override
    public void drawForeground(int guiLeft, int guiTop, int mouseX, int mouseY) {
        drawTooltip(guiLeft, guiTop, mouseX, mouseY);
    }

    public void drawTooltip(int guiLeft, int guiTop, int mouseX, int mouseY) {
        mouseX -= guiLeft;
        mouseY -= guiTop;
        if (!mouseInElement(mouseX, mouseY)) {
            return;
        }

        List<String> description = new LinkedList<>();
        FluidStack fluidStack = tank.getFluid();

        if (fluidStack == null || fluidStack.getFluid() == null) {
            description.add(LocalizationHelper.getLocalString("gui.element.tank.empty"));
        } else {
            if (fluidStack.amount > 0) {
                String amountToText = fluidStack.amount + LocalizationHelper.getLocalString("gui.element.tank.mb");

                description.add(fluidStack.getLocalizedName());
                description.add(amountToText);
            }
        }
        drawHoveringText(description, mouseX, mouseY, getFontRenderer());
    }

    private static TextureAtlasSprite getStillTexture(FluidStack fluid) {
        ResourceLocation iconKey = fluid.getFluid().getStill();
        final TextureAtlasSprite textureEntry = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(iconKey.toString());
        return textureEntry != null ? textureEntry : Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
    }
}
