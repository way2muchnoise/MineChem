package minechem.chemical;

import minechem.helper.ColourHelper;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * This class will hold one Chemical structure
 *
 * @author way2muchnoise
 */
public class ChemicalStructure {
    private LinkedList<ChemicalBaseSet> internalStructure;
    private int colour;

    public ChemicalStructure() {
        this.internalStructure = new LinkedList<>();
    }

    public ChemicalStructure(LinkedList<ChemicalBaseSet> chemicalBaseSets) {
        this.internalStructure = new LinkedList<>(chemicalBaseSets);
    }

    /**
     * Add a {@link minechem.chemical.ChemicalBase} with given count to the structure
     *
     * @param chemicalBase the chemical
     * @param count        the amount must be 1 or more otherwise it will throw a IllegalArgumentException
     */
    public void add(ChemicalBase chemicalBase, int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count can't be less than zero");
        }
        this.internalStructure.add(new ChemicalBaseSet(chemicalBase, count));
    }

    /**
     * Add a {@link minechem.chemical.ChemicalBase} to the structure, the count will be one
     *
     * @param chemicalBase the chemical
     */
    public void add(ChemicalBase chemicalBase) {
        add(chemicalBase, 1);
    }

    public void addAll(ChemicalBaseSet... chemicalBaseSets) {
        internalStructure.addAll(Arrays.asList(chemicalBaseSets));
    }

    public LinkedList<ChemicalBaseSet> getInternalStructure() {
        return internalStructure;
    }

    /**
     * Generates the formula for the {@link minechem.chemical.ChemicalStructure }
     *
     * @return
     */
    public String getFormula() {
        StringBuilder formula = new StringBuilder();
        for (ChemicalBaseSet chemicalBaseSet : internalStructure) {
            boolean molecule = chemicalBaseSet.chemical instanceof Molecule;
            if (molecule) {
                formula.append("(");
            }
            formula.append(chemicalBaseSet.chemical.getFormula());
            if (molecule) {
                formula.append(")");
            }
            if (chemicalBaseSet.count > 1) {
                formula.append(chemicalBaseSet.count);
            }
        }
        return formula.toString();
    }

    private void calcColour() {
        int[] colours = new int[internalStructure.size()];
        for (int i = 0; i < internalStructure.size(); i++) {
            colours[i] = internalStructure.get(i).chemical.getColour();
        }
        colour = ColourHelper.blend(colours);
    }

    public int getColour() {
        if (colour == 0) {
            calcColour();
        }
        return this.colour;
    }

    public ChemicalStructure copy() {
        return new ChemicalStructure(internalStructure);
    }

    /**
     * Node class for the {@link minechem.chemical.ChemicalStructure}
     */
    public static class ChemicalBaseSet {
        private ChemicalBase chemical;
        private int count;

        private ChemicalBaseSet(ChemicalBase chemical) {
            this(chemical, 1);
        }

        private ChemicalBaseSet(ChemicalBase chemical, int count) {
            this.chemical = chemical;
            this.count = count;
        }

        /**
         * Get the {@link minechem.chemical.ChemicalBase}
         *
         * @return
         */
        public ChemicalBase getChemical() {
            return chemical;
        }

        /**
         * Get the amount of the give {@link minechem.chemical.ChemicalBase}
         *
         * @return
         */
        public int getCount() {
            return count;
        }
    }
}
