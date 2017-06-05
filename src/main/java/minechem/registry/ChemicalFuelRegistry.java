package minechem.registry;

import minechem.chemical.ChemicalBase;

import java.util.HashMap;
import java.util.Map;

public class ChemicalFuelRegistry {
    private static ChemicalFuelRegistry instance;
    private Map<String, Integer> fuelValues;

    public static ChemicalFuelRegistry getInstance() {
        if (instance == null) {
            instance = new ChemicalFuelRegistry();
        }
        return instance;
    }

    private ChemicalFuelRegistry() {
        fuelValues = new HashMap<>();
    }

    public int getFuelValue(ChemicalBase chemicalBase) {
        Integer value = fuelValues.get(chemicalBase.fullName);
        return value == null ? 0 : value;
    }

    public void addFuel(ChemicalBase chemicalBase, int value) {
        fuelValues.put(chemicalBase.fullName, Math.max(0, value));
    }

    public void removeFuel(ChemicalBase chemicalBase) {
        fuelValues.remove(chemicalBase.fullName);
    }

    public void clear() {
        fuelValues.clear();
    }
}
