package minechem.api.reaction;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IReactionRegistry {
    /**
     * Add a new reaction
     *
     * @param type the type of reaction, eg. heat, acid, electrolysis
     * @param input the input ItemStack
     * @param outputs a list of chemicals it outputs
     */
    void addRecation(String type, ItemStack input, String... outputs);

    /**
     * Add a new reaction
     *
     * @param type the type of reaction, eg. heat, acid, electrolysis
     * @param input the input Item
     * @param outputs a list of chemicals it outputs
     */
    void addRecation(String type, Item input, String... outputs);
}
