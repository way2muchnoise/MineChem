package minechem.registry;

import minechem.chemical.ChemicalBase;

import java.util.HashMap;
import java.util.Map;

public class AcidRegistry {
    private static AcidRegistry instance;
    private Map<String, Integer> acidValues;

    public static AcidRegistry getInstance() {
        if (instance == null) {
            instance = new AcidRegistry();
        }
        return instance;
    }

    private AcidRegistry() {
        acidValues = new HashMap<>();
    }

    public void clear() {
        acidValues.clear();
    }

    public void addAcid(ChemicalBase chemicalBase, int acidStrength) {
        acidValues.put(chemicalBase.fullName, acidStrength);
    }

    public int getAcidValue(ChemicalBase chemicalBase) {
        Integer value = acidValues.get(chemicalBase.fullName);
        return value == null ? 0 : value;
    }

    public void removeAcid(ChemicalBase chemicalBase) {
        acidValues.remove(chemicalBase.fullName);
    }
}
