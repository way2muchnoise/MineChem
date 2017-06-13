package minechem.registry;

import minechem.api.breakdown.IReverseRecipe;
import minechem.breakdown.ProbabilityItemStack;
import minechem.breakdown.ReverseRecipeRegistry;
import minechem.breakdown.ReverseRecipeSorter;
import minechem.collections.ItemStackMap;
import minechem.collections.ItemStackSet;
import minechem.helper.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

public class RecipeBreakdownsRegistry {
    private static final RecipeBreakdownsRegistry instance = new RecipeBreakdownsRegistry();
    private ItemStackSet stacksWithProcess;
    private Set<Item> itemWithProcess;
    private ItemStackMap<List<ProbabilityItemStack>> breakdowns;

    public static RecipeBreakdownsRegistry getInstance() {
        return instance;
    }

    private RecipeBreakdownsRegistry() {
        stacksWithProcess = new ItemStackSet(true);
        itemWithProcess = new HashSet<>();
        breakdowns = new ItemStackMap<>(true);
    }

    public void addStackWithProcess(ItemStack stack) {
        stacksWithProcess.add(stack);
    }

    public void addItemWithProcess(Item item) {
        itemWithProcess.add(item);
    }

    public List<ProbabilityItemStack> getBreakdown(ItemStack itemStack) {
        if (stacksWithProcess.contains(itemStack) || itemWithProcess.contains(itemStack.getItem())) {
            return Collections.singletonList(new ProbabilityItemStack(itemStack, itemStack.getCount()));
        } else {
            List<ProbabilityItemStack> breakdown = breakdowns.get(itemStack);
            if (breakdown == null) {
                calculateTotalBreakdown(itemStack);
                breakdown = breakdowns.get(itemStack);
            }
            return breakdown;
        }
    }

    public void clear() {
        breakdowns.clear();
    }

    public void fullClear() {
        stacksWithProcess.clear();
        itemWithProcess.clear();
        breakdowns.clear();
    }

    private void calculateTotalBreakdown(ItemStack itemStack) {
        Queue<ProbabilityItemStack> breakdownQueue = new LinkedList<>(calculateSingleBreakdown(itemStack));
        List<ProbabilityItemStack> actualBreakdown = new LinkedList<>();
        while (!breakdownQueue.isEmpty()) {
            ProbabilityItemStack currentProbabilityStack = breakdownQueue.poll();
            ItemStack currentStack = currentProbabilityStack.getStack();
            float produces = currentProbabilityStack.getProducesCount() / currentStack.getCount();
            if (stacksWithProcess.contains(currentStack) || itemWithProcess.contains(currentStack.getItem())) {
                actualBreakdown.add(currentProbabilityStack);
            } else {
                List<ProbabilityItemStack> breakdown = breakdowns.get(currentStack);
                if (breakdown == null) {
                    calculateTotalBreakdown(currentStack);
                    breakdown = breakdowns.get(currentStack);
                }
                actualBreakdown.addAll(ProbabilityItemStack.renewList(breakdown, produces));
            }
        }
        breakdowns.put(itemStack, ProbabilityItemStack.flatten(actualBreakdown));
    }

    private List<ProbabilityItemStack> calculateSingleBreakdown(ItemStack itemStack) {
        List<IReverseRecipe> reverseRecipes = ReverseRecipeRegistry.getInstance().getReverseRecipes(itemStack);
        IReverseRecipe selectedRecipe = reverseRecipes.stream()
            .sorted(new ReverseRecipeSorter(itemStack))
            .findFirst().orElse(null);
        List<ProbabilityItemStack> breakdown;
        if (selectedRecipe == null) {
            breakdown = Collections.emptyList();
        } else {
            breakdown = ProbabilityItemStack.newList(ItemStackHelper.flatten(selectedRecipe.getInputs(itemStack)), selectedRecipe.getOutputAmount(itemStack));
        }
        return breakdown;
    }
}
