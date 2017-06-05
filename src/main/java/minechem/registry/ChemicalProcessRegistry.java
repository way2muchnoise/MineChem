package minechem.registry;

import minechem.breakdown.ProbabilityItemStack;
import minechem.chemical.Chemical;
import minechem.chemical.process.ChemicalProcess;
import minechem.chemical.process.ChemicalProcessType;
import minechem.collections.ItemStackMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

public class ChemicalProcessRegistry {
    private static ChemicalProcessRegistry instance;
    private ItemStackMap<Map<ChemicalProcessType, Set<ChemicalProcess>>> itemStackProcessMap;
    private Map<Item, Map<ChemicalProcessType, Set<ChemicalProcess>>> itemProcessMap;
    private Map<ChemicalProcessType, String> processTypes;
    private Map<String, ChemicalProcessType> processNames;

    public static ChemicalProcessRegistry getInstance() {
        if (instance == null) {
            instance = new ChemicalProcessRegistry();
        }
        return instance;
    }

    private ChemicalProcessRegistry() {
        itemStackProcessMap = new ItemStackMap<>(true);
        itemProcessMap = new HashMap<>();
        processNames = new TreeMap<>();
        processTypes = new HashMap<>();
    }

    /**
     * Add a {@link minechem.chemical.process.ChemicalProcessType} with given name to the registry
     *
     * @param name name for the process
     * @return the generated ChemicalProcessType, null if the given name already exists, is null or is empty
     */
    public ChemicalProcessType addProcess(String name) {
        if (name != null && !name.isEmpty()) {
            ChemicalProcessType result = new ChemicalProcessType(name);
            if (processTypes.get(result) == null && processNames.get(name) == null) {
                processTypes.put(result, name.toLowerCase());
                processNames.put(name.toLowerCase(), result);
                return result;
            }
        }
        return null;
    }

    /**
     * Get the {@link minechem.chemical.process.ChemicalProcessType} for the given process name
     *
     * @param name the process name
     * @return the requested ChemicalProcessType or null if nothing is registered under that name
     */
    public ChemicalProcessType getProcess(String name) {
        return processNames.get(name.toLowerCase());
    }

    /**
     * Get the output of given {@link net.minecraft.item.ItemStack} using given {@link minechem.chemical.process.ChemicalProcessType}
     *
     * @param itemStack   the input ItemStack
     * @param processType the ChemicalProcessType
     * @return the output in an array
     */
    public Chemical[] getOutput(ItemStack itemStack, ChemicalProcessType processType) {
        List<ProbabilityItemStack> items = RecipeBreakdownsRegistry.getInstance().getBreakdown(itemStack);
        List<Chemical> outputs = new LinkedList<>();
        for (ProbabilityItemStack stack : items) {
            outputs.addAll(getInternalOutput(stack, processType));
        }
        return outputs.toArray(new Chemical[outputs.size()]);
    }

    private List<Chemical> getInternalOutput(ProbabilityItemStack probabilityItemStack, ChemicalProcessType processType) {
        List<Chemical> output = new LinkedList<>();
        Set<ChemicalProcess> processes = new HashSet<>();
        ItemStack itemStack =  probabilityItemStack.getStack();

        if (itemStackProcessMap.contains(itemStack)) {
            processes.addAll(getProcesses(itemStackProcessMap.get(itemStack), processType));
        }

        // Only do item check when there is no specific stack implementation
        if (processes.isEmpty() && itemProcessMap.containsKey(itemStack.getItem())) {
            processes.addAll(getProcesses(itemProcessMap.get(itemStack.getItem()), processType));
        }

        for (ChemicalProcess process : processes) {
            Collections.addAll(output, process.getOutput(processType, itemStack));
        }

        return Chemical.applyProbability(output, probabilityItemStack.getChance());
    }

    /**
     * Add a {@link minechem.chemical.process.ChemicalProcess} to an {@link net.minecraft.item.ItemStack}
     *
     * @param itemStack the itemStack
     * @param process   the process
     */
    public void addItemStackProcess(ItemStack itemStack, ChemicalProcess process) {
        Map<ChemicalProcessType, Set<ChemicalProcess>> chemicalTypes = itemStackProcessMap.get(itemStack);
        itemStackProcessMap.put(itemStack, addChemicalType(chemicalTypes, process));
        RecipeBreakdownsRegistry.getInstance().addStackWithProcess(itemStack);
    }

    /**
     * Add a {@link minechem.chemical.process.ChemicalProcess} to an {@link net.minecraft.item.Item}
     *
     * @param item    the item
     * @param process the process
     */
    public void addItemProcess(Item item, ChemicalProcess process) {
        Map<ChemicalProcessType, Set<ChemicalProcess>> chemicalTypes = itemProcessMap.get(item);
        itemProcessMap.put(item, addChemicalType(chemicalTypes, process));
        RecipeBreakdownsRegistry.getInstance().addItemWithProcess(item);
    }

    public void clearProcessingFor(Item item, ChemicalProcessType... types) {
        if (types.length == 0) {
            itemProcessMap.remove(item);
        } else {
            Map<ChemicalProcessType, Set<ChemicalProcess>> chemicalProcesses = itemProcessMap.get(item);
            for (ChemicalProcessType process : types) {
                chemicalProcesses.remove(process);
            }
        }
    }

    public void clearProcessingFor(ItemStack itemStack, ChemicalProcessType... types) {
        if (types.length == 0) {
            itemStackProcessMap.remove(itemStack);
        } else {
            Map<ChemicalProcessType, Set<ChemicalProcess>> chemicalProcesses = itemStackProcessMap.get(itemStack);
            for (ChemicalProcessType process : types) {
                chemicalProcesses.remove(process);
            }
        }
    }

    private Map<ChemicalProcessType, Set<ChemicalProcess>> addChemicalType(Map<ChemicalProcessType, Set<ChemicalProcess>> chemicalTypes, ChemicalProcess process) {
        if (chemicalTypes == null) {
            chemicalTypes = new HashMap<>();
        }
        Set<ChemicalProcess> processes = chemicalTypes.get(process.getType());
        if (processes == null) {
            processes = new HashSet<>();
        }
        processes.add(process);
        chemicalTypes.put(process.getType(), processes);
        return chemicalTypes;
    }

    private Set<ChemicalProcess> getProcesses(Map<ChemicalProcessType, Set<ChemicalProcess>> chemicalTypes, ChemicalProcessType processType) {
        if (chemicalTypes == null || chemicalTypes.isEmpty()) {
            return Collections.emptySet();
        }
        return chemicalTypes.get(processType);
    }

    public void clear() {
        itemProcessMap.clear();
        itemStackProcessMap.clear();
    }
}
