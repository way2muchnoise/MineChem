package minechem.proxy.client;

import minechem.Compendium;
import minechem.apparatus.tier1.centrifuge.CentrifugeTileEntity;
import minechem.apparatus.tier1.centrifuge.CentrifugeTileEntityRenderer;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleTileEntity;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleTileEntityRenderer;
import minechem.apparatus.tier1.electrolysis.ElectrolysisTileEntity;
import minechem.apparatus.tier1.electrolysis.ElectrolysisTileEntityRenderer;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeTileEntity;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeTileEntityRenderer;
import minechem.handler.ResourceReloadListener;
import minechem.helper.LogHelper;
import minechem.item.chemical.ChemicalItemColour;
import minechem.proxy.CommonProxy;
import minechem.registry.ItemRegistry;
import net.afterlifelochie.fontbox.api.exception.FontException;
import net.afterlifelochie.fontbox.api.font.GLFontBuilder;
import net.afterlifelochie.fontbox.api.font.IGLFontBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.logging.log4j.Level;

public class ClientProxy extends CommonProxy
{
    @GLFontBuilder
    public static IGLFontBuilder fontBuilder;

    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().world;
    }

    @Override
    public void registerRenderers()
    {
        ItemRegistry.initColors();

        OpticalMicroscopeTileEntityRenderer opticalMicroscopeRenderer = new OpticalMicroscopeTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(OpticalMicroscopeTileEntity.class, opticalMicroscopeRenderer);

        ElectrolysisTileEntityRenderer electrolysisRenderer = new ElectrolysisTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(ElectrolysisTileEntity.class, electrolysisRenderer);

        ElectricCrucibleTileEntityRenderer electricCrucibleRenderer = new ElectricCrucibleTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(ElectricCrucibleTileEntity.class, electricCrucibleRenderer);

        CentrifugeTileEntityRenderer centrifugeRenderer = new CentrifugeTileEntityRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(CentrifugeTileEntity.class, centrifugeRenderer);
    }

    @Override
    public void registerResourcesListener()
    {
        LogHelper.debug("Registering Resource Reload Listener...");
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager())
            .registerReloadListener(new ResourceReloadListener());
    }

    /**
     * Get the current lang code
     *
     * @return eg. 'en_US'
     */
    public String getCurrentLanguage()
    {
        return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
    }

    @Override
    public World getWorld(MessageContext context)
    {
        return Minecraft.getMinecraft().world;
    }

    @Override
    public EntityPlayer getPlayer(MessageContext context)
    {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public void registerFonts()
    {
        try
        {
            fontBuilder.fromTTF(Compendium.Fontbox.getManager(), 22.0f, new ResourceLocation(Compendium.Naming.id, "fonts/daniel.ttf"));
            fontBuilder.fromTTF(Compendium.Fontbox.getManager(), 22.0f, new ResourceLocation(Compendium.Naming.id, "fonts/notethis.ttf"));
            fontBuilder.fromTTF(Compendium.Fontbox.getManager(), 22.0f, new ResourceLocation(Compendium.Naming.id, "fonts/ampersand.ttf"));
        } catch (FontException font)
        {
            LogHelper.exception(font, Level.ERROR);
        }
    }
}
