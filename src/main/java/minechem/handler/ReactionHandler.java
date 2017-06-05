package minechem.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import minechem.Compendium;
import minechem.Config;
import minechem.chemical.Chemical;
import minechem.chemical.process.ChemicalProcess;
import minechem.chemical.process.ChemicalProcessType;
import minechem.chemical.process.DefaultProcesses;
import minechem.helper.FileHelper;
import minechem.helper.Jenkins;
import minechem.helper.LogHelper;
import minechem.registry.ChemicalProcessRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReactionHandler {
    public static void init() {
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.reactionsJson;
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.reactionsJson;

        InputStream inputStream = FileHelper.getJsonFile(ReactionHandler.class, fileDestSource, Config.useDefaultReactions);
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
        ChemicalProcessRegistry.getInstance().clear();
        DefaultProcesses.register();
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.reactionsJson;
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.reactionsJson;

        InputStream inputStream = FileHelper.getJsonFile(ReactionHandler.class, fileDestSource, Config.useDefaultReactions);
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

        try {
            JsonArray asJsonArray = parser.parse(jReader).getAsJsonArray();
            for (JsonElement element : asJsonArray) {
                processReaction(element.getAsJsonObject());
            }
        } catch (IllegalStateException e) {
            LogHelper.exception("Bad json format", e, Level.WARN);
        }
    }

    private static void processReaction(JsonObject object) {
        ChemicalProcessType processType = ChemicalProcessRegistry.getInstance().getProcess(object.get("process").getAsString());
        JsonElement inputElement = object.get("input");
        List<ItemStack> inputs = new ArrayList<>(1);
        if (inputElement.isJsonObject()) {
            JsonObject inputObject = inputElement.getAsJsonObject();
            String itemName = inputObject.get("name").getAsString();
            int meta = inputObject.has("meta") ? inputObject.get("meta").getAsInt() : 0;
            String nbt = inputObject.has("nbt") ? inputObject.get("nbt").getAsString() : null;
            inputs.add(GameRegistry.makeItemStack(itemName, meta, 1, nbt));
        } else {
            String input = inputElement.getAsString();
            ItemStack chemicalStack = Jenkins.getStack(input);
            if (!chemicalStack.isEmpty()) {
                inputs.add(chemicalStack);
            } else {
                inputs.addAll(OreDictionary.getOres(input));
            }
        }

        List<Chemical> chemicals = new LinkedList<>();
        for (JsonElement element : object.get("output").getAsJsonArray()) {
            Chemical chemical = Jenkins.getChemical(element.getAsString());
            if (chemical != null) {
                chemicals.add(chemical);
            }
        }

        if (processType != null && !chemicals.isEmpty()) {
            ChemicalProcess process = new ChemicalProcess(processType, chemicals);
            for (ItemStack input : inputs) {
                if (!input.isEmpty()) {
                    ChemicalProcessRegistry.getInstance().addItemStackProcess(input, process);
                }
            }
        } else {
            LogHelper.warn("Bad reaction in json: " + object.toString());
        }
    }
}
