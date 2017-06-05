package minechem.breakdown;

import minechem.collections.ItemStackMap;
import minechem.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProbabilityItemStack {
    private ItemStack stack;
    private float producesCount;

    public ProbabilityItemStack(ItemStack stack, float producesCount) {
        this.stack = stack.copy();
        this.producesCount = producesCount;
    }

    public ProbabilityItemStack(ProbabilityItemStack a, ProbabilityItemStack b) {
        if (ItemStackHelper.compareIgnoreSize(a.stack, b.stack)) {
            this.stack = a.stack.copy();
            this.stack.grow(b.stack.getCount());
            this.producesCount = a.producesCount + b.producesCount;
        } else {
            throw new IllegalArgumentException("Can't merge ProbabilityItemStacks");
        }
    }

    public ItemStack getStack() {
        return stack;
    }

    public float getProducesCount() {
        return producesCount;
    }

    public float getChance() {
        return 1.0F * stack.getCount() / producesCount;
    }

    public ProbabilityItemStack renew(float producesCount) {
        return new ProbabilityItemStack(stack.copy(), this.producesCount * producesCount);
    }

    public static List<ProbabilityItemStack> newList(Collection<ItemStack> stacks, final float producesCount) {
        return stacks.stream().map(s -> new ProbabilityItemStack(s, producesCount)).collect(Collectors.toList());
    }

    public static List<ProbabilityItemStack> renewList(Collection<ProbabilityItemStack> stacks, final float producesCount) {
        return stacks.stream().map(s -> s.renew(producesCount)).collect(Collectors.toList());
    }

    public static List<ProbabilityItemStack> flatten(Collection<ProbabilityItemStack> stacks) {
        ItemStackMap<ProbabilityItemStack> flat = new ItemStackMap<>(true);
        for (ProbabilityItemStack stack : stacks) {
            ProbabilityItemStack current = flat.get(stack.getStack());
            if (current == null) {
                flat.put(stack.getStack(), stack);
            } else {
                ProbabilityItemStack merged = new ProbabilityItemStack(current, stack);
                flat.put(merged.getStack(), merged);
            }
        }
        return new ArrayList<>(flat.values());
    }
}
