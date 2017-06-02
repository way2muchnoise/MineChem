package minechem.apparatus.prefab.tileEntity.storageTypes;

import minechem.apparatus.prefab.tileEntity.IChangeable;
import net.minecraftforge.fluids.FluidTank;

/**
 * Implementation of a basic single fluid type tank
 */
public class BasicFluidTank extends FluidTank
{
    private IChangeable listener = IChangeable.NONE;

    /**
     * Creates a new fluid tank with a specific capacity
     *
     * @param capacity max millibuckets for the tank
     */
    public BasicFluidTank(int capacity)
    {
        super(capacity);
    }

    public BasicFluidTank setListener(IChangeable changeable) {
        this.listener = changeable;
        return this;
    }

    @Override
    protected void onContentsChanged() {
        super.onContentsChanged();
        listener.onChange(true);
    }
}
