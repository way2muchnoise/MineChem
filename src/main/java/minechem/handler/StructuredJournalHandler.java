package minechem.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import minechem.Compendium;
import minechem.Config;
import minechem.chemical.Element;
import minechem.chemical.Molecule;
import minechem.helper.FileHelper;
import minechem.helper.Jenkins;
import minechem.helper.LogHelper;
import minechem.item.journal.pages.EntryPage;
import minechem.item.journal.pages.IJournalPage;
import minechem.item.journal.pages.SectionPage;
import minechem.item.journal.pages.elements.IJournalElement;
import minechem.item.journal.pages.elements.JournalHeader;
import minechem.item.journal.pages.elements.JournalImage;
import minechem.item.journal.pages.elements.JournalText;
import minechem.registry.AcidRegistry;
import minechem.registry.ElementRegistry;
import minechem.registry.JournalRegistry;
import minechem.registry.MoleculeRegistry;
import net.afterlifelochie.fontbox.api.formatting.layout.AlignmentMode;
import net.afterlifelochie.fontbox.api.formatting.layout.FloatMode;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class StructuredJournalHandler {
    public static void init() {
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.researchPagesJson;
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.researchPagesJson;

        InputStream inputStream = FileHelper.getJsonFile(StructuredJournalHandler.class, fileDestSource, Config.useDefaultResearchPages);
        readFromStream(inputStream);
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                LogHelper.exception("Cannot close stream!", e, Level.WARN);
            }
        }
    }

    public static void reload() {
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.researchPagesJson;
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.researchPagesJson;

        InputStream inputStream = FileHelper.getJsonFile(StructuredJournalHandler.class, fileDestSource, false);
        readFromStream(inputStream);
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                LogHelper.exception("Cannot close stream!", e, Level.WARN);
            }
        }
    }

    private static void readFromStream(InputStream inputStream) {
        JsonReader jReader = new JsonReader(new InputStreamReader(inputStream));
        JsonParser parser = new JsonParser();

        SectionPage journal = getJournal(parser.parse(jReader).getAsJsonObject());
        LogHelper.info("Total of " + journal.getSubPages() + " pages registered");
        JournalRegistry.setJournal(journal);
    }

    public static SectionPage getJournal(JsonObject object) {
        SectionPage result = new SectionPage("");
        for (IJournalPage page : getPagesFromJsonObject("", object)) {
            result.addSubPage(page);
        }
        return result;
    }

    public static IJournalPage getPageFromJSONObject(String name, String chapter, JsonObject object) {
        IJournalPage result;
        if (object.has("section")) {
            result = new SectionPage(name, chapter, new ArrayList<>());
            for (IJournalPage page : getPagesFromJsonObject((chapter.isEmpty() ? "" : chapter + ".") + name, object.getAsJsonObject("section"))) {
                result.addSubPage(page);
            }
        } else {
            List<IJournalElement> elements = getElementsFromJsonObject((chapter.isEmpty() ? "" : chapter + ".") + name, object);
            result = new EntryPage(name, chapter, elements);
        }
        return result;
    }

    public static List<IJournalPage> getPagesFromJsonObject(String chapter, JsonObject object) {
        List<IJournalPage> pages = new ArrayList<>();
        if (chapter.equals("chemicals.compounds")) {
            pages.addAll(getCompoundPages(chapter));
        } else if (chapter.equals("chemicals.elements")) {
            pages.addAll(getElementPages(chapter));
        } else {
            for (Map.Entry<String, JsonElement> pageEntry : object.entrySet()) {
                if (pageEntry.getValue().isJsonNull()) {
                    pages.add(new EntryPage(pageEntry.getKey(), chapter, new JournalText(chapter + "." + pageEntry.getKey())));
                } else if (!pageEntry.getValue().isJsonObject()) {
                    continue;
                } else {
                    pages.add(getPageFromJSONObject(pageEntry.getKey(), chapter, pageEntry.getValue().getAsJsonObject()));
                }
            }
        }
        return pages;
    }

    public static List<IJournalElement> getElementsFromJsonObject(String page, JsonObject object) {
        List<IJournalElement> pages = new ArrayList<>();
        Set<Map.Entry<String, JsonElement>> entrySet = object.entrySet();
        if (entrySet.size() == 0) {
            pages.add(new JournalText(page));
        } else {
            for (Map.Entry<String, JsonElement> elementEntry : entrySet) {
                IJournalElement element = getElementFromJsonElement(page, elementEntry.getKey(), elementEntry.getValue());
                if (element != null) {
                    pages.add(element);
                } else {
                    LogHelper.info(page + " object " + elementEntry.getKey() + " failed to parse");
                }
            }
        }
        return pages;
    }

    public static IJournalElement getElementFromJsonElement(String page, String key, JsonElement element) {
        if (element.isJsonNull()) {
            return new JournalText(page, page + "." + key);
        }
        if (element.isJsonObject()) {
            return getElementFromJsonObject(page, key, element.getAsJsonObject());
        }
        try {
            String unlockString = element.getAsString();
            return new JournalText(unlockString, page + "." + key);
        } catch (Exception e) {
        }
        return null;
    }

    private static final String prefix = "textures/journal/";
    private static final String suffix = ".png";

    public static IJournalElement getElementFromJsonObject(String page, String key, JsonObject object) {
        String unlock = page;
        if (object.has("unlock")) {
            unlock = object.get("unlock").getAsString();
        }
        if (object.entrySet().size() == 0) {
            return new JournalText(page, page + "." + key);
        }
        if (object.entrySet().size() == 1 && object.has("unlock")) {
            return new JournalText(unlock, page + "." + key);
        }
        if (object.has("width")) {
            int width = object.get("width").getAsInt();
            int height = object.get("height").getAsInt();
            AlignmentMode align = object.has("align") ? AlignmentMode.valueOf(object.get("align").getAsString()) : AlignmentMode.JUSTIFY;
            FloatMode floatMode = object.has("float") ? FloatMode.valueOf(object.get("float").getAsString()) : FloatMode.NONE;
            if (object.has("image") || key.endsWith(".png")) {
                String imageDir = object.has("image") ? object.get("image").getAsString() : key;
                if (!imageDir.startsWith(prefix)) {
                    imageDir = prefix + imageDir;
                }
                if (!imageDir.endsWith(suffix)) {
                    imageDir += suffix;
                }
                return new JournalImage(unlock, imageDir, width, height, align, floatMode);
            } else {
                ItemStack stack = null;
                String id;
                String[] split;
                int damage = object.has("damage") ? object.get("damage").getAsInt() : 0;
                NBTTagCompound tagCompound;
                try {
                    tagCompound = object.has("nbt") ? JsonToNBT.getTagFromJson(object.get("nbt").getAsString()) : null;
                } catch (Exception e) {
                    tagCompound = null;
                }
                if (object.has("item")) {
                    id = object.get("item").getAsString();
                    if (!id.contains(":")) {
                        return null;
                    }
                    split = id.split(":");
                    if (split.length != 2) {
                        return null;
                    }
                    Item item = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(split[0], split[1]));
                    if (item != null) {
                        stack = new ItemStack(item, 1, damage);
                    }
                } else if (object.has("block")) {
                    id = object.get("block").getAsString();
                    if (!id.contains(":")) {
                        return null;
                    }
                    split = id.split(":");
                    if (split.length != 2) {
                        return null;
                    }
                    Block block = GameRegistry.findRegistry(Block.class).getValue(new ResourceLocation(split[0], split[1]));
                    if (block != null) {
                        stack = new ItemStack(block, 1, damage % 16);
                    }
                } else if (object.has("chemical")) {
                    stack = Jenkins.getStack(object.get("chemical").getAsString());
                }
                if (stack != null) {
                    if (tagCompound != null) {
                        stack.setTagCompound(tagCompound);
                    }
                    return new JournalImage(unlock, stack, width, height, align, floatMode);
                }
            }
        }
        return null;
    }

    public static List<IJournalPage> getCompoundPages(String chapter) {
        List<IJournalPage> compoundPages = new LinkedList<>();
        for (Molecule molecule : MoleculeRegistry.getInstance().getMolecules()) {
            List<IJournalElement> pageElements = new LinkedList<>();
            pageElements.add(new JournalHeader(molecule.getResearchKey()).setTitle(molecule.fullName));
            pageElements.add(new JournalImage(molecule.getResearchKey(), molecule.getItemStack(), 54, 54, AlignmentMode.LEFT, FloatMode.LEFT));
            pageElements.add(new JournalText(molecule.getResearchKey()).setText(molecule.getFormula()).setAlignment(AlignmentMode.LEFT));
            pageElements.add(new JournalText(molecule.getResearchKey()).setText("chemical.structure"));
            pageElements.add(new JournalImage(molecule.getResearchKey(), molecule.getStructureResource(), 200, 200, AlignmentMode.CENTER, FloatMode.NONE));
            if (molecule.isAcid()) {
                pageElements.add(new JournalText(molecule.getResearchKey()).setText("chemical.acidic").setParams(molecule.getAcidicValue()));
            }
            compoundPages.add(new EntryPage(molecule.fullName.toLowerCase(), chapter, pageElements));
        }
        return compoundPages;
    }

    public static List<IJournalPage> getElementPages(String chapter) {
        List<IJournalPage> elementPages = new LinkedList<>();
        for (Element element : ElementRegistry.getInstance().getElements()) {
            List<IJournalElement> pageElements = new LinkedList<>();
            String name = element.fullName.toLowerCase();
            pageElements.add(new JournalHeader(element.getResearchKey()));
            pageElements.add(new JournalImage(element.getResearchKey(), element.getItemStack(), 54, 54, AlignmentMode.LEFT, FloatMode.LEFT));
            pageElements.add(new JournalText(element.getResearchKey(), chapter + "." + name + ".text"));
            if (element.isAcid()) {
                pageElements.add(new JournalText(element.getResearchKey()).setText("chemical.acidic").setParams(element.getAcidicValue()));
            }
            elementPages.add(new EntryPage(name, chapter, pageElements));
        }
        return elementPages;
    }
}
