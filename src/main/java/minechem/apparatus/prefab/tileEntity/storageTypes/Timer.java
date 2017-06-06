package minechem.apparatus.prefab.tileEntity.storageTypes;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.IChangeable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

/**
 * The timer works per second
 */
public class Timer implements INBTWritable {
    private static final int ticksPerSecond = 20;

    private String name;
    private int reset;
    private int counter;
    private boolean selfResetting, visual;

    private IChangeable listener = IChangeable.NONE;

    public Timer() {
        this(1, "tickTimer", true, false);
    }

    public Timer(float reset, String name) {
        this(reset, name, false, true);
    }

    public Timer(float reset, String name, boolean selfResetting, boolean visual)
    {
        this.reset = MathHelper.ceil(reset * ticksPerSecond);
        this.name = name;
        this.selfResetting = selfResetting;
        this.visual = visual;
    }

    public Timer setListener(IChangeable changeable) {
        this.listener = changeable;
        return this;
    }

    public boolean update()
    {
        boolean metTarget = false;
        if (++counter >= reset)
        {
            metTarget = true;
            if (selfResetting) reset();
        }
        listener.onChange(visual);
        return metTarget;
    }

    public void reset() {
        counter = 0;
        listener.onChange(visual);
    }

    public int getCounter() {
        return counter;
    }

    /**
     * @return percentage of the time
     */
    public int getProgress() {
        return (counter * 100) / reset;
    }

    public void writeNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setTag(Compendium.NBTTags.timer + name, asNBTTag());
    }

    public NBTTagCompound asNBTTag()
    {
        NBTTagCompound timer = new NBTTagCompound();
        timer.setInteger(Compendium.NBTTags.count, this.counter);
        timer.setInteger(Compendium.NBTTags.reset, this.reset);
        return timer;
    }

    public void readNBT(NBTTagCompound compound)
    {
        NBTTagCompound timer = compound;
        if (timer.hasKey(Compendium.NBTTags.timer + name, Compendium.NBTTags.tagCompound)) {
            timer = compound.getCompoundTag(Compendium.NBTTags.timer + name);
            reset = timer.getInteger(Compendium.NBTTags.reset);
            counter = timer.getInteger(Compendium.NBTTags.count);
        }

    }

}
