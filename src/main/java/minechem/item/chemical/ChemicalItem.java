package minechem.item.chemical;

import minechem.Compendium;
import minechem.chemical.ChemicalBase;
import minechem.helper.Jenkins;
import minechem.helper.LocalizationHelper;
import minechem.helper.ResearchHelper;
import minechem.item.prefab.BasicItem;
import minechem.registry.CreativeTabRegistry;
import minechem.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ChemicalItem extends BasicItem
{
    private static final String dustSuffix = "_dust";
    private static final String liquidSuffix = "_liquid";
    private static final String gasSuffix = "_gas";
    private static final String plasmaSuffix = "_plasma";
    private static final String moleculeSuffix = "_molecule";

    public ChemicalItem()
    {
        super(Compendium.Naming.chemical);
        setCreativeTab(CreativeTabRegistry.TAB_CHEMICALS);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelResourceLocation dust = new ModelResourceLocation(getRegistryName() + dustSuffix, "inventory");
        ModelResourceLocation liquid = new ModelResourceLocation(getRegistryName() + liquidSuffix, "inventory");
        ModelResourceLocation gas = new ModelResourceLocation(getRegistryName() + gasSuffix, "inventory");
        ModelResourceLocation plasma = new ModelResourceLocation(getRegistryName() + plasmaSuffix, "inventory");

        ModelResourceLocation dust_molecule = new ModelResourceLocation(getRegistryName() + dustSuffix + moleculeSuffix, "inventory");
        ModelResourceLocation liquid_molecule = new ModelResourceLocation(getRegistryName() + liquidSuffix + moleculeSuffix, "inventory");
        ModelResourceLocation gas_molecule = new ModelResourceLocation(getRegistryName() + gasSuffix + moleculeSuffix, "inventory");
        ModelResourceLocation plasma_molecule = new ModelResourceLocation(getRegistryName() + plasmaSuffix + moleculeSuffix, "inventory");

        ModelBakery.registerItemVariants(this, dust, liquid, gas, plasma, dust_molecule, liquid_molecule, gas_molecule, plasma_molecule);

        ModelLoader.setCustomMeshDefinition(this, stack -> {
            ChemicalBase chemical = getChemicalBase(stack);
            if (chemical == null) return liquid;
            boolean isElement = chemical.isElement();
            switch (chemical.form) {
                case solid:
                    return isElement ? dust : dust_molecule;
                case liquid:
                    return isElement ? liquid : liquid_molecule;
                case gas:
                    return isElement ? gas : gas_molecule;
                case plasma:
                    return isElement ? plasma : plasma_molecule;
                default:
                    return liquid;
            }
        });
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack)
    {
        boolean hasTag = itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("fullName");
        if (FMLCommonHandler.instance().getSide() == Side.SERVER && hasTag) {
            return itemStack.getTagCompound().getString("fullName");
        } else if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            ChemicalBase chemical = getChemicalBase(itemStack);
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (chemical != null && (player.isCreative() || ResearchHelper.hasResearch(player, chemical.getResearchKey()))) {
                return itemStack.getTagCompound().getString("fullName");
            }
        }
        return LocalizationHelper.getLocalString("chemical.unknown");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        ChemicalBase chemicalBase = getChemicalBase(itemStack);
        if (chemicalBase != null && ResearchHelper.hasResearch(player, chemicalBase.getResearchKey())) {
            tooltip.addAll(chemicalBase.getToolTip());
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        ItemStack itemStack;
        NBTTagCompound tagCompound;
        for (ChemicalBase element : Jenkins.getAll())
        {
            itemStack = new ItemStack(this);
            tagCompound = new NBTTagCompound();
            element.writeToNBT(tagCompound);
            itemStack.setTagCompound(tagCompound);
            subItems.add(itemStack);
        }
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 256;
    }

    public static ChemicalBase getChemicalBase(ItemStack itemStack)
    {
        return ChemicalBase.readFromNBT(itemStack.getTagCompound());
    }

    public static ItemStack getItemStackForChemical(ChemicalBase chemicalBase)
    {
        ItemStack itemStack = new ItemStack(ItemRegistry.chemicalItem);
        NBTTagCompound tag = new NBTTagCompound();
        chemicalBase.writeToNBT(tag);
        itemStack.setTagCompound(tag);
        return itemStack;
    }
}
