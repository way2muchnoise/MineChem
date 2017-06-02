package minechem.proxy.client;

import minechem.Compendium;
import minechem.apparatus.centrifuge.CentrifugeTileEntity;
import minechem.apparatus.centrifuge.CentrifugeTileEntityRenderer;
import minechem.apparatus.electricCrucible.ElectricCrucibleTileEntity;
import minechem.apparatus.electricCrucible.ElectricCrucibleTileEntityRenderer;
import minechem.apparatus.electrolysis.ElectrolysisTileEntity;
import minechem.apparatus.electrolysis.ElectrolysisTileEntityRenderer;
import minechem.apparatus.opticalMicroscope.OpticalMicroscopeTileEntity;
import minechem.apparatus.opticalMicroscope.OpticalMicroscopeTileEntityRenderer;
import minechem.helper.LogHelper;
import minechem.proxy.CommonProxy;
import minechem.registry.ItemRegistry;
import net.afterlifelochie.fontbox.api.exception.FontException;
import net.afterlifelochie.fontbox.api.font.GLFontBuilder;
import net.afterlifelochie.fontbox.api.font.IGLFontBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
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
        if (context.side == Side.CLIENT) {
            return Minecraft.getMinecraft().world;
        } else {
            return super.getWorld(context);
        }
    }

    @Override
    public EntityPlayer getPlayer(MessageContext context)
    {
        if (context.side == Side.CLIENT) {
            return Minecraft.getMinecraft().player;
        } else {
            return super.getPlayer(context);
        }
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
