package minechem.apparatus.prefab.tileEntity.storageTypes;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/**
 * Implementation of a basic single fluid type tank
 */
public class BasicFluidTank implements IFluidHandler
{
    public FluidTank tank;

    /**
     * Creates a new fluid tank with a specific capacity
     *
     * @param capacity max millibuckets for the tank
     */
    public BasicFluidTank(int capacity)
    {
        tank = new FluidTank(capacity);
    }

    /**
     * Attempt to drain a resource from the tank
     *
     * @param resource the fluid stack being drained
     * @param doDrain  should it actually be drained
     * @return FluidStack object
     */
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain)
    {
        if (resource != null && resource.isFluidEqual(tank.getFluid()))
        {
            return tank.drain(resource.amount, doDrain);
        }
        return null;
    }

    /**
     * Attemt to drain any fluid from the tank
     *
     * @param amount  how much to drain
     * @param doDrain should it actually be drained
     * @return FluidStack object
     */
    @Override
    public FluidStack drain(int amount, boolean doDrain)
    {
        if (amount <= tank.getFluidAmount())
        {
            return tank.drain(amount, doDrain);
        }
        return tank.drain(tank.getFluidAmount(), doDrain);
    }

    /**
     * Fill the tank with a resource from a specific side
     *
     * @param resource the FluidStack to fill with
     * @param doFill   should it be filled
     * @return how much was actually filled
     */
    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        if (tank != null & resource != null)
        {
            if (tank.getFluidAmount() > 0 && resource.isFluidEqual(tank.getFluid()) || tank.getFluidAmount() == 0)
            {
                return tank.fill(resource, doFill);

            }
        }
        return 0;
    }

    /**
     * Get info for the specific tank
     *
     * @return IFluidTankProperties array
     */
    @Override
    public IFluidTankProperties[] getTankProperties()
    {
        return tank.getTankProperties();
    }
}
