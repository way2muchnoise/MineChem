package minechem.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import minechem.Compendium;
import minechem.Config;
import minechem.chemical.ChemicalBase;
import minechem.helper.FileHelper;
import minechem.helper.Jenkins;
import minechem.helper.LogHelper;
import minechem.registry.AcidRegistry;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class AcidHandler {
    public static void init() {
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.acidJson;
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.acidJson;

        InputStream inputStream = FileHelper.getJsonFile(AcidHandler.class, fileDestSource, Config.useDefaultAcids);
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
        AcidRegistry.getInstance().clear();
        String[] fileDestSource = new String[2];
        fileDestSource[0] = Compendium.Config.dataJsonPrefix + Compendium.Config.acidJson;
        fileDestSource[1] = Compendium.Config.configPrefix + Compendium.Config.dataJsonPrefix + Compendium.Config.acidJson;

        InputStream inputStream = FileHelper.getJsonFile(AcidHandler.class, fileDestSource, false);
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
            JsonObject asJsonObject = parser.parse(jReader).getAsJsonObject();
            for (Map.Entry<String, JsonElement> element : asJsonObject.entrySet()) {
                registerAcid(element.getKey(), element.getValue().getAsInt());
            }
        } catch (IllegalStateException e) {
            LogHelper.exception("Bad json format", e, Level.WARN);
        }
    }

    private static void registerAcid(String chemical, int acidStrength) {
        ChemicalBase chemicalBase = Jenkins.get(chemical);
        if (chemicalBase != null) {
            AcidRegistry.getInstance().addAcid(chemicalBase, acidStrength);
        } else  {
            LogHelper.warn("Can't find " + chemical + " to register as acid");
        }
    }
}
