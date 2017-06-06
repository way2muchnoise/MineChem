package minechem.apparatus.prefab.tileEntity.storageTypes;

import minechem.apparatus.prefab.tileEntity.IChangeable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * Implementation of a basic single fluid type tank
 */
public class BasicFluidTank extends FluidTank implements INBTWritable
{
    private Fluid allowedFluid;
    private IChangeable listener = IChangeable.NONE;

    /**
     * Creates a new fluid tank with a specific capacity
     *
     * @param capacity max millibuckets for the tank
     */
    public BasicFluidTank(int capacity)
    {
        this(capacity, null);
    }

    /**
     * Creates a new fluid tank with a specific capacity
     *
     * @param capacity max millibuckets for the tank
     * @param allowedFluid the fluid allowed in this tank
     */
    public BasicFluidTank(int capacity, Fluid allowedFluid)
    {
        super(capacity);
        this.allowedFluid = allowedFluid;
    }

    public BasicFluidTank setListener(IChangeable changeable) {
        this.listener = changeable;
        return this;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return this.allowedFluid == null ? super.canFillFluidType(fluid) : this.allowedFluid.equals(fluid.getFluid());
    }

    @Override
    protected void onContentsChanged() {
        super.onContentsChanged();
        listener.onChange(true);
    }

    public void writeNBT(NBTTagCompound tagCompound) {
        writeToNBT(tagCompound);
    }

    public void readNBT(NBTTagCompound nbttagcompound) {
        readFromNBT(nbttagcompound);
    }
}
