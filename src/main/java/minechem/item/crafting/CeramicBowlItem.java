package minechem.item.crafting;

import minechem.Compendium;
import minechem.helper.LocalizationHelper;
import minechem.item.prefab.BasicItem;
import minechem.registry.ItemRegistry;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CeramicBowlItem extends BasicItem {
    private static final String isFired = "isFired";

    public CeramicBowlItem() {
        super(Compendium.Naming.ceramicBowl);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelResourceLocation fired = new ModelResourceLocation(getRegistryName() + "_fired", "inventory");
        ModelResourceLocation unfired = new ModelResourceLocation(getRegistryName() + "_unfired", "inventory");

        ModelBakery.registerItemVariants(this, fired, unfired);

        ModelLoader.setCustomMeshDefinition(this, stack -> {
            if(isFired(stack)) {
                return fired;
            } else {
                return unfired;
            }
        });
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return LocalizationHelper.getLocalString(getUnlocalizedName(stack) + (isFired(stack) ? "_fired" : "_unfired") + ".name");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        subItems.add(getStack(false));
        subItems.add(getStack(true));
    }

    public static boolean isFired(ItemStack fired) {
        return fired.hasTagCompound() && fired.getTagCompound().hasKey(isFired) && fired.getTagCompound().getBoolean(isFired);
    }

    public static ItemStack getStack(boolean fired) {
        ItemStack stack = new ItemStack(ItemRegistry.ceramicBowl);
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean(isFired, fired);
        stack.setTagCompound(compound);
        return stack;
    }
}
