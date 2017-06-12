package minechem.helper;

import minechem.chemical.Chemical;
import minechem.chemical.ChemicalBase;
import minechem.chemical.Element;
import minechem.chemical.Molecule;
import minechem.registry.ElementRegistry;
import minechem.registry.MoleculeRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Loyal servant to find your elements and molecules
 */
public class Jenkins {
    /**
     * Get {@link minechem.chemical.Element} by atomic number
     *
     * @param atomicNumber the atomic number
     * @return can be null if atomicNumber does not exists
     */
    public static Element get(int atomicNumber) {
        return ElementRegistry.getInstance().getElement(atomicNumber);
    }

    /**
     * Get an element or molecule by abbreviation or full name
     *
     * @param s eg. 'H', 'H2O', 'hydrogen', 'water'
     * @return the element or molecule that matches given abbreviation or full name
     */
    public static ChemicalBase get(String s) {
        ChemicalBase chemicalBase = getElement(s);
        if (chemicalBase == null) {
            chemicalBase = getMolecule(s);
        }
        return chemicalBase;
    }

    public static Element getElement(String s) {
        Element element = ElementRegistry.getInstance().getElement(s);
        if (element == null) {
            element = ElementRegistry.getInstance().getElementByName(s);
        }
        return element;
    }

    public static Molecule getMolecule(String s) {
        Molecule molecule = MoleculeRegistry.getInstance().getMoleculeByFormula(s);
        if (molecule == null) {
            molecule = MoleculeRegistry.getInstance().getMoleculeByName(s);
        }
        return molecule;
    }

    /**
     * Get a stack of an element or molecule by abbreviation or full name
     *
     * @param s eg. eg. 'H', 'H2O', 'hydrogen', 'water' since indicated with a prepended 'n*'
     * @return a stack of the wanted item
     */
    public static ItemStack getStack(String s) {
        int size = 1;
        if (s.contains("*")) {
            String[] splitted = s.split("\\*", 2);
            s = splitted[1];
            try {
                size = Integer.parseInt(splitted[0]);
            } catch (NumberFormatException ignored) {}
        }
        ChemicalBase chemicalBase = get(s);
        return chemicalBase == null ? ItemStack.EMPTY : chemicalBase.getItemStack(size);
    }

    public static ItemStack getMoleculeStack(String s) {
        int size = 1;
        if (s.contains("*")) {
            String[] splitted = s.split("\\*", 2);
            s = splitted[1];
            try {
                size = Integer.parseInt(splitted[0]);
            } catch (NumberFormatException ignored) {}
        }
        Molecule molecule = getMolecule(s);
        return molecule == null ? ItemStack.EMPTY : molecule.getItemStack(size);
    }

    public static ItemStack getElementStack(String s) {
        int size = 1;
        if (s.contains("*")) {
            String[] splitted = s.split("\\*", 2);
            s = splitted[1];
            try {
                size = Integer.parseInt(splitted[0]);
            } catch (NumberFormatException ignored) {}
        }
        Element element = getElement(s);
        return element == null ? ItemStack.EMPTY : element.getItemStack(size);
    }

    /**
     * Get a stack of an element or molecule by abbreviation or full name
     *
     * @param s eg. eg. 'H', 'H2O', 'hydrogen', 'water' since indicated with a prepended 'n*'
     * @return a stack of the wanted item
     */
    public static Chemical getChemical(String s) {
        int size = 1;
        if (s.contains("*")) {
            String[] splitted = s.split("\\*", 2);
            s = splitted[1];
            try {
                size = Integer.parseInt(splitted[0]);
            } catch (NumberFormatException ignored) {}
        }
        ChemicalBase chemicalBase = get(s);
        return chemicalBase == null ? null : new Chemical(chemicalBase, size);
    }

    /**
     * Gets all {@link minechem.chemical.Element}s and {@link minechem.chemical.Molecule}s that are registered
     *
     * @return a list of all {@link minechem.chemical.ChemicalBase}s registered
     */
    public static List<? extends ChemicalBase> getAll() {
        List<ChemicalBase> all = new ArrayList<>();
        all.addAll(ElementRegistry.getInstance().getElements());
        all.addAll(MoleculeRegistry.getInstance().getMolecules());
        return all;
    }
}
