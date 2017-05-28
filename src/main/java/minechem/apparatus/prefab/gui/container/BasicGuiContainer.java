package minechem.apparatus.prefab.gui.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.fml.client.FMLClientHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Basic GUI container class for extending
 */
public abstract class BasicGuiContainer extends GuiContainer
{
    protected ResourceLocation texture;
    protected String name;
    private BasicContainer container;

    public BasicGuiContainer(BasicContainer container)
    {
        super(container);
        this.container = container;
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
}
