package minechem.apparatus.prefab.tileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseTileEntity extends TileEntity implements IChangeable
{
    protected String name;
    private Map<Capability, Object> caps;

    public BaseTileEntity(String name) {
        this.name = name;
        caps = new HashMap<>();
    }

    @Override
    public void onChange() {
        markDirty();
    }

    public String getName() {
        return name;
    }

    public <T, V extends T> void attachCapability(Capability<T> cap, V data) {
        caps.put(cap, data);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return caps.containsKey(capability) || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (caps.containsKey(capability)) {
            return (T) caps.get(capability);
        }
        return super.getCapability(capability, facing);
    }
}
