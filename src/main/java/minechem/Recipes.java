package minechem;

import minechem.helper.Jenkins;
import minechem.item.crafting.CeramicBowlItem;
import minechem.registry.BlockRegistry;
import minechem.registry.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes {
    public static void register() {
        //region microscope
        addShapedRecipe(new ItemStack(ItemRegistry.convexLens),
            " G ",
            "GGG",
            " G ",
            'G', "paneGlass");

        addShapedRecipe(new ItemStack(ItemRegistry.concaveLens),
            "G G",
            "GGG",
            "G G",
            'G', "paneGlass");

        addShapedRecipe(new ItemStack(ItemRegistry.microscopeLens),
            "GXG",
            "G G",
            "GEG",
            'G', "paneGlass",
            'X', new ItemStack(ItemRegistry.convexLens),
            'E', new ItemStack(ItemRegistry.concaveLens));

        addShapedRecipe(new ItemStack(BlockRegistry.opticalMicroscope),
            " LI",
            " GI",
            "III",
            'G', "paneGlass",
            'I', "ingotIron",
            'L', new ItemStack(ItemRegistry.microscopeLens));
        //endregion
        //region crucible
        addShapedRecipe(CeramicBowlItem.getStack(false),
            "   ",
            "C C",
            " C ",
            'C', new ItemStack(Items.CLAY_BALL));
        GameRegistry.addSmelting(CeramicBowlItem.getStack(false), CeramicBowlItem.getStack(true), 0);
        addShapedRecipe(new ItemStack(ItemRegistry.heatingCore),
            "IGI",
            "IBI",
            "III",
            'G', "paneGlass",
            'I', "ingotIron",
            'B', new ItemStack(Items.BLAZE_POWDER));
        addShapedRecipe(new ItemStack(BlockRegistry.electricCrucibleBlock),
            "I I",
            "ICI",
            "IHI",
            'I', "ingotIron",
            'C', CeramicBowlItem.getStack(true),
            'H', new ItemStack(ItemRegistry.heatingCore));
        //endregion
        //region centrifuge
        addShapedRecipe(new ItemStack(ItemRegistry.centrifugeCore),
            " B ",
            "BIB",
            " B ",
            'I', "ingotIron",
            'B', new ItemStack(Items.GLASS_BOTTLE));
        addShapedRecipe(new ItemStack(BlockRegistry.centrifugeBlock),
            "GGG",
            "ICI",
            "III",
            'G', "paneGlass",
            'I', "ingotIron",
            'C', new ItemStack(ItemRegistry.centrifugeCore));
        //endregion
        //region electrolysis
        addShapedRecipe(new ItemStack(ItemRegistry.electricCore),
            "IGI",
            "I I",
            "I I",
            'I', "ingotIron",
            'G', "ingotGold");
        addShapedRecipe(new ItemStack(BlockRegistry.electrolysisBlock),
            "G G",
            "GCG",
            "GGG",
            'G', "paneGlass",
            'C', new ItemStack(ItemRegistry.electricCore));
        //endregion
        //region water
        addShapelessRecipe(Jenkins.getStack("water"), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER));
        addShapelessRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), Jenkins.getStack("water"));
        //endregion
    }

    private static void addShapedRecipe(ItemStack output, Object... params) {
        GameRegistry.addRecipe(new ShapedOreRecipe(output, params));
    }

    private static void addShapelessRecipe(ItemStack output, Object... params) {
        GameRegistry.addRecipe(new ShapelessOreRecipe(output, params));
    }
}
