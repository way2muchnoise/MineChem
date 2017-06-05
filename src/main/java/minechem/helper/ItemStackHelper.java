package minechem.helper;

import minechem.collections.ItemStackMap;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ItemStackHelper {
     public static boolean compareIgnoreSize(ItemStack one, ItemStack other) {
         return ItemStack.areItemsEqual(one, other) && ItemStack.areItemStackTagsEqual(one, other);
     }

     public static int getItemCount(Collection<ItemStack> stacks) {
         int sum = 0;
         for (ItemStack stack : stacks) {
             sum += stack.getCount();
         }
         return sum;
     }

     public static List<ItemStack> flatten(List<ItemStack> itemStacks) {
         ItemStackMap<Integer> count = new ItemStackMap<>(true);
         for (ItemStack stack : itemStacks) {
             Integer currentCount = count.get(stack);
             if (currentCount == null) currentCount = 0;
             currentCount += stack.getCount();
             count.put(stack, currentCount);
         }

         List<ItemStack> flattened = new ArrayList<>(count.size());
         for (Map.Entry<ItemStack, Integer> entry : count.entrySet()) {
             ItemStack stack = entry.getKey().copy();
             stack.setCount(entry.getValue());
             flattened.add(stack);
         }
         return flattened;
     }
}
