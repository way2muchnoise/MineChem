package minechem.chemical.process;

import minechem.chemical.Chemical;
import minechem.chemical.ChemicalBase;
import minechem.item.chemical.ChemicalItem;
import minechem.registry.ChemicalProcessRegistry;
import minechem.registry.ItemRegistry;
import net.minecraft.item.ItemStack;

public class DefaultProcesses {
    // Crucible
    public static final ChemicalProcess boil = new ChemicalProcess(ChemicalProcessType.heat) {
        @Override
        public Chemical[] getOutput(ChemicalProcessType type, ItemStack stack) {
            if (this.type == type) {
                ChemicalBase chemical = ChemicalItem.getChemicalBase(stack);
                if (chemical != null && chemical.isElement() && chemical.form == ChemicalBase.Form.liquid) {
                    chemical.form = ChemicalBase.Form.gas;
                    return new Chemical[] { new Chemical(chemical, 1) };
                }
            }
            return empty;
        }
    };

    // TODO add machine
    public static final ChemicalProcess ionize = new ChemicalProcess(ChemicalProcessType.ionization) {
        @Override
        public Chemical[] getOutput(ChemicalProcessType type, ItemStack stack) {
            if (this.type == type) {
                ChemicalBase chemical = ChemicalItem.getChemicalBase(stack);
                if (chemical != null && chemical.isElement() && chemical.form == ChemicalBase.Form.gas) {
                    chemical.form = ChemicalBase.Form.plasma;
                    return new Chemical[] { new Chemical(chemical, 1) };
                }
            }
            return empty;
        }
    };

    // Oven
    public static final ChemicalProcess dry = new ChemicalProcess(ChemicalProcessType.dry) {
        @Override
        public Chemical[] getOutput(ChemicalProcessType type, ItemStack stack) {
            if (this.type == type) {
                ChemicalBase chemical = ChemicalItem.getChemicalBase(stack);
                if (chemical != null && chemical.isElement() && chemical.form == ChemicalBase.Form.liquid) {
                    chemical.form = ChemicalBase.Form.solid;
                    return new Chemical[] { new Chemical(chemical, 1) };
                }
            }
            return empty;
        }
    };

    // TODO add machine
    public static final ChemicalProcess dissolve = new ChemicalProcess(ChemicalProcessType.dissolve) {
        @Override
        public Chemical[] getOutput(ChemicalProcessType type, ItemStack stack) {
            if (this.type == type) {
                ChemicalBase chemical = ChemicalItem.getChemicalBase(stack);
                if (chemical != null && chemical.isElement() && chemical.form == ChemicalBase.Form.solid) {
                    chemical.form = ChemicalBase.Form.liquid;
                    return new Chemical[] { new Chemical(chemical, 1) };
                }
            }
            return empty;
        }
    };

    public static final ChemicalProcess electrolysis = new ChemicalProcess(ChemicalProcessType.electrolysis) {
        @Override
        public Chemical[] getOutput(ChemicalProcessType type, ItemStack stack) {
            if (this.type == type) {
                ChemicalBase chemical = ChemicalItem.getChemicalBase(stack);
                if (chemical != null) {
                    return new Chemical[] { new Chemical(chemical, 1) };
                }
            }
            return empty;
        }
    };

    public static final ChemicalProcess centrifuge = new ChemicalProcess(ChemicalProcessType.centrifuge) {
        @Override
        public Chemical[] getOutput(ChemicalProcessType type, ItemStack stack) {
            if (this.type == type) {
                ChemicalBase chemical = ChemicalItem.getChemicalBase(stack);
                if (chemical != null) {
                    return new Chemical[] { new Chemical(chemical, 1) };
                }
            }
            return empty;
        }
    };

    public static void register() {
        // Form related TODO: make form variable
        // ChemicalProcessRegistry.getInstance().addItemProcess(ItemRegistry.chemicalItem, boil);
        // ChemicalProcessRegistry.getInstance().addItemProcess(ItemRegistry.chemicalItem, ionize);
        // ChemicalProcessRegistry.getInstance().addItemProcess(ItemRegistry.chemicalItem, dry);
        // ChemicalProcessRegistry.getInstance().addItemProcess(ItemRegistry.chemicalItem, dissolve);

        // Default machine output
        ChemicalProcessRegistry.getInstance().addItemProcess(ItemRegistry.chemicalItem, electrolysis);
        ChemicalProcessRegistry.getInstance().addItemProcess(ItemRegistry.chemicalItem, centrifuge);
    }
}
