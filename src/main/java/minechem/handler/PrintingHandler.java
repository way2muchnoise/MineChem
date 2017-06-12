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
import minechem.helper.FileHelper;
import minechem.helper.Jenkins;
import minechem.helper.LogHelper;
import minechem.printing.PrintingRecipe;
import minechem.registry.ChemicalProcessRegistry;
import minechem.registry.PrintingRegistry;
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

public class PrintingHandler {
    public static void init() {
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.printingJson;
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.printingJson;

        InputStream inputStream = FileHelper.getJsonFile(PrintingHandler.class, fileDestSource, Config.useDefaultPrinting);
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
        PrintingRegistry.getInstance().clear();
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.printingJson;
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.printingJson;

        InputStream inputStream = FileHelper.getJsonFile(ReactionHandler.class, fileDestSource, Config.useDefaultPrinting);
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
                processPrint(element.getAsJsonObject());
            }
        } catch (IllegalStateException e) {
            LogHelper.exception("Bad json format", e, Level.WARN);
        }
    }

    private static void processPrint(JsonObject object) {
        JsonObject inputObject = object.get("result").getAsJsonObject();
        String itemName = inputObject.get("name").getAsString();
        int meta = inputObject.has("meta") ? inputObject.get("meta").getAsInt() : 0;
        int count = inputObject.has("count") ? inputObject.get("count").getAsInt() : 1;
        String nbt = inputObject.has("nbt") ? inputObject.get("nbt").getAsString() : null;
        ItemStack result = GameRegistry.makeItemStack(itemName, meta, count, nbt);

        JsonArray recipeArray = object.get("recipe").getAsJsonArray();
        Chemical[][] recipe = new Chemical[PrintingRecipe.SIZE][PrintingRecipe.SIZE];
        int i = 0, j;
        for (JsonElement element : recipeArray) {
            JsonArray row = element.getAsJsonArray();
            j = 0;
            for (JsonElement item : row) {
                if (item.isJsonNull()) {
                    recipe[i][j] = Chemical.EMPTY;
                } else {
                    recipe[i][j] = Jenkins.getChemical(item.getAsString());
                }
                j++;
            }
            i++;
        }
        PrintingRegistry.getInstance().addRecipe(new PrintingRecipe(result, recipe));
    }
}
