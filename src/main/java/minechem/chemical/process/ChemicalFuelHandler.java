package minechem.chemical.process;

import minechem.chemical.ChemicalBase;
import minechem.registry.ChemicalFuelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class ChemicalFuelHandler implements IFuelHandler {
    @Override
    public int getBurnTime(ItemStack fuel) {
        ChemicalBase chemical = ChemicalBase.readFromNBT(fuel.getTagCompound());
        return chemical == null ? 0 : ChemicalFuelRegistry.getInstance().getFuelValue(chemical);
    }
}
