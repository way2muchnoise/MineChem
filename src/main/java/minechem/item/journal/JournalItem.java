package minechem.item.journal;

import minechem.Compendium;
import minechem.Config;
import minechem.handler.MessageHandler;
import minechem.handler.message.JournalMessage;
import minechem.helper.AchievementHelper;
import minechem.helper.ArrayHelper;
import minechem.helper.LocalizationHelper;
import minechem.item.prefab.BasicItem;
import minechem.registry.ResearchRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class JournalItem extends BasicItem {
    public JournalItem() {
        super(Compendium.Naming.journal);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            if (!Config.playerPrivateKnowledge) {
                writeKnowledge(stack, player, world.isRemote);
            }
        } else {
            if (world.isRemote) {
                Minecraft.getMinecraft().displayGuiScreen(Journal.createJournalGui(player, getKnowledgeKeys(stack), getAuthors(stack)));
            }
        }
        AchievementHelper.giveAchievement(player, this.getUnlocalizedName(), world.isRemote);
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    /**
     * Writes knowledge to the journal
     *
     * @param itemStack the journal stack
     * @param player    the player that writes the knowledge
     * @param isRemote  is the world remote on true it will send a {@link minechem.handler.message.JournalMessage} to the server
     */
    public void writeKnowledge(ItemStack itemStack, EntityPlayer player, boolean isRemote) {
        if (isRemote) {
            MessageHandler.INSTANCE.sendToServer(new JournalMessage(player));
            return;
        }

        player.sendStatusMessage(new TextComponentTranslation("journal.scribble"), false);

        NBTTagCompound tagCompound = itemStack.getTagCompound();
        Set<String> playerKnowledge = ResearchRegistry.getInstance().getResearchFor(player);
        if (playerKnowledge == null) {
            return;
        }
        Set<String> bookKnowledge = new LinkedHashSet<String>();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
        } else if (tagCompound.hasKey("research")) {
            NBTTagList bookKnowledgeTag = tagCompound.getTagList("research", 8);
            for (int i = 0; i < bookKnowledgeTag.tagCount(); i++) {
                bookKnowledge.add(bookKnowledgeTag.getStringTagAt(i));
            }
        }
        bookKnowledge.addAll(playerKnowledge);
        NBTTagList bookKnowledgeTag = new NBTTagList();
        for (String knowledgeKey : bookKnowledge) {
            bookKnowledgeTag.appendTag(new NBTTagString(knowledgeKey));
        }
        tagCompound.setTag("research", bookKnowledgeTag);

        Set<String> authors = new LinkedHashSet<String>();
        if (tagCompound.hasKey("authors")) {
            NBTTagList authorsTag = tagCompound.getTagList("authors", 8);
            for (int i = 0; i < authorsTag.tagCount(); i++) {
                authors.add(authorsTag.getStringTagAt(i));
            }
        }
        authors.add(player.getDisplayNameString());
        NBTTagList authorsTag = new NBTTagList();
        for (String author : authors) {
            authorsTag.appendTag(new NBTTagString(author));
        }
        tagCompound.setTag("authors", authorsTag);
        itemStack.setTagCompound(tagCompound);
    }

    /**
     * Gets a list of authors
     *
     * @param itemStack the journal Stack
     * @return an array of authors can be empty
     */
    public String[] getAuthors(ItemStack itemStack) {
        if (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("authors")) {
            NBTTagList authorsTag = itemStack.getTagCompound().getTagList("authors", 8);
            String[] authors = new String[authorsTag.tagCount()];
            for (int i = 0; i < authorsTag.tagCount(); i++) {
                authors[i] = authorsTag.getStringTagAt(i);
            }
            return ArrayHelper.removeNulls(authors, String.class);
        }
        return new String[0];
    }

    /**
     * Gets a list of knowledgeKeys
     *
     * @param itemStack the journal Stack
     * @return an array of knowledgeKeys can be empty
     */
    public String[] getKnowledgeKeys(ItemStack itemStack) {
        if (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("research")) {
            NBTTagList authorsTag = itemStack.getTagCompound().getTagList("research", 8);
            String[] knowledgeKeys = new String[authorsTag.tagCount()];
            for (int i = 0; i < authorsTag.tagCount(); i++) {
                knowledgeKeys[i] = authorsTag.getStringTagAt(i);
            }
            return ArrayHelper.removeNulls(knowledgeKeys, String.class);
        }
        return new String[0];
    }

    /**
     * Writes the tooltip with author info
     *
     * @param itemStack the ItemStack
     * @param player    the player
     * @param lines     lines to print
     * @param advanced  is an advanced tooltip
     */
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List lines, boolean advanced) {
        super.addInformation(itemStack, player, lines, advanced);
        if (!Config.playerPrivateKnowledge && GuiScreen.isShiftKeyDown()) {
            String[] authors = getAuthors(itemStack);
            if (authors == null || authors.length < 1) {
                return;
            }
            lines.add(LocalizationHelper.getLocalString("gui.journal.writtenBy") + ":");
            for (String author : authors) {
                lines.add("- " + author);
            }
        }
    }
}
