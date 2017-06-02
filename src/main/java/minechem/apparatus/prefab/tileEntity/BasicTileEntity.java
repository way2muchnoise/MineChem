package minechem.apparatus.prefab.tileEntity;

import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicEnergyStorage;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicFluidTank;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import minechem.apparatus.prefab.tileEntity.storageTypes.ProcessingInventory;
import minechem.chemical.Chemical;
import minechem.chemical.process.ChemicalProcessType;
import minechem.handler.MessageHandler;
import minechem.handler.message.ClientTEUpdate;
import minechem.registry.ChemicalProcessRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class BasicTileEntity extends TileEntity implements IChangeable
{
    protected String name;
    private Map<Capability, Object> caps;
    private List<BasicInventory> toDrop;

    public BasicTileEntity(BasicBlockContainer block) {
        this.name = block.getLocalizedName();
        caps = new HashMap<>();
        toDrop = new LinkedList<>();
    }

    @Override
    public void onChange(boolean sendUpdate) {
        markDirty();
    }

    public String getName() {
        return name;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        readFromNBT(tag);
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

    public void registerDropableInventories(BasicInventory... inventories) {
        toDrop.addAll(Arrays.asList(inventories));
    }

    public void addStacksDroppedOnBlockBreak(ArrayList<ItemStack> itemStacks) {
        for (BasicInventory inventory : toDrop) {
            itemStacks.addAll(inventory.getAllStacks());
            inventory.clear();
        }
    }
}
