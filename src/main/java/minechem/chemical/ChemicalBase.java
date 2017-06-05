package minechem.chemical;

import java.util.List;
import minechem.helper.Jenkins;
import minechem.item.chemical.ChemicalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * The base for chemicals This class will hold all functions that will shared by the {@link minechem.chemical.Element} and {@link minechem.chemical.Molecule} This way they can be handled as being the
 * same
 */
public abstract class ChemicalBase
{
    public enum Form {
        solid, liquid, gas, plasma
    }

    public final String fullName;
    public Form form;
    protected int colour;

    public ChemicalBase(String fullName, String form, int colour)
    {
        this.fullName = fullName;
        this.form = Form.valueOf(form);
        this.colour = colour;
    }

    /**
     * Used for logging the {@link minechem.chemical.ChemicalBase} to the {@link net.minecraftforge.fml.common.FMLLog}
     */
    public abstract void log();

    /**
     * Get the short name for the {@link minechem.chemical.ChemicalBase}
     *
     * @return a short String representation
     */
    public abstract String getFormula();

    /**
     * Shorthand for checking if it is an element or not so that instanceof is not needed
     *
     * @return true if it is an element
     */
    public abstract boolean isElement();

    public static ChemicalBase readFromNBT(NBTTagCompound tag)
    {
        if (tag != null && tag.hasKey("fullName"))
        {
            ChemicalBase chemical = Jenkins.get(tag.getString("fullName")).copy();
            if (tag.hasKey("form")) {
                chemical.form = Form.valueOf(tag.getString("form"));
            }
            return chemical;
        }
        return null;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        if (tag == null)
        {
            tag = new NBTTagCompound();
        }
        tag.setString("fullName", this.fullName);
        tag.setBoolean("isElement", isElement());
        tag.setString("form", this.form.name());
        return tag;
    }

    public ItemStack getItemStack()
    {
        return ChemicalItem.getItemStackForChemical(this);
    }

    public ItemStack getItemStack(int size) {
        ItemStack stack = getItemStack();
        stack.setCount(size);
        return stack;
    }

    public int getColour()
    {
        return colour;
    }

    public abstract List<String> getToolTip();

    public abstract String getResearchKey();

    public abstract ChemicalBase copy();

    @Override
    public String toString()
    {
        return getFormula();
    }
}
