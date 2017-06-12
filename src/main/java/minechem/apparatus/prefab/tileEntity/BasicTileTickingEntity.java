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
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class BasicTileTickingEntity extends BasicTileEntity implements ITickable {
    private boolean update, sendUpdate;

    public BasicTileTickingEntity(BasicBlockContainer block) {
        super(block);
    }

    @Override
    public void onChange(boolean sendUpdate) {
        // schedule an update
        this.update = true;
        this.sendUpdate |= sendUpdate;
    }

    @Override
    public void update() {
        if (update) {
            update = false;
            markDirty();
        }
        if (sendUpdate) {
            sendUpdate = false;
            MessageHandler.INSTANCE.sendToAllAround(new ClientTEUpdate(this), asTarget(5));
        }
    }

    protected void doChemicalProcessUpdate(BasicInventory inventoryIn, BasicInventory inventoryOut, ProcessingInventory processingInventory, ChemicalProcessType processType) {
        if (!world.isRemote) {
            if (processingInventory.isEmpty()) {
                ItemStack inStack = inventoryIn.getStackInSlot(0);
                if (!inStack.isEmpty() && processingTick()) {
                    ItemStack taken = inventoryIn.decrStackSize(0, 1);
                    processingInventory.addItem(taken);
                    processingInventory.update();
                }
            } else {
                if (processingInventory.isDone() || (processingTick() && processingInventory.update())) {
                    ItemStack toProcess = processingInventory.decrStackSize(0, 1);
                    Chemical[] outputs = ChemicalProcessRegistry.getInstance().getOutput(toProcess, processType);
                    IItemHandlerModifiable output = inventoryOut.asCapability();
                    List<ItemStack> stacks = Arrays.stream(outputs).map(Chemical::asItemStack).collect(Collectors.toList());
                    if (insert(output, new LinkedList<>(stacks), true)) {
                        insert(output, new LinkedList<>(stacks), false);
                        processingInventory.reset();
                    } else {
                        // Try again next time
                        processingInventory.addItem(toProcess);
                    }
                }
            }
        }
    }

    /**
     * Use certain resources during the {@link #doChemicalProcessUpdate(BasicInventory, BasicInventory, ProcessingInventory, ChemicalProcessType)}
     * or other processes
     */
    protected boolean processingTick() {
        return true;
    }

    protected boolean tryAndExtractEnergy(BasicEnergyStorage energyStorage, int toExtract) {
        int extracted = energyStorage.extractEnergy(toExtract, true);
        if (extracted == toExtract) {
            energyStorage.extractEnergy(toExtract, false);
            return true;
        }
        return false;
    }

    protected boolean tryAndExtractFluid(BasicFluidTank fluidTank, int toExtract) {
        FluidStack extracted = fluidTank.drain(toExtract, false);
        if (extracted != null && extracted.amount == toExtract) {
            fluidTank.drain(toExtract, true);
            return true;
        }
        return false;
    }

    /**
     * Insert or simulate insertion of {@link ItemStack}s into an {@link IItemHandler}
     *
     * @param dest     target {@link IItemHandler}
     * @param stacks   a {@link Deque} of {@link ItemStack}s
     * @param simulate simulate or actually insert the {@link ItemStack}s
     * @return true when all can be inserted, false otherwise
     */
    protected static boolean insert(IItemHandler dest, Deque<ItemStack> stacks, boolean simulate) {
        ItemStack current = stacks.poll();
        List<Integer> availableSlots = IntStream.range(0, dest.getSlots()).boxed().collect(Collectors.toList());
        while (current != null && !availableSlots.isEmpty()) {
            ItemStack remainder = null;
            for (Integer slot : availableSlots) {
                remainder = dest.insertItem(slot, current, simulate);
                if (remainder.isEmpty() || current.getCount() != remainder.getCount()) {
                    availableSlots.remove(slot);
                    break;
                }
            }
            if (remainder == null || remainder.isEmpty()) {
                current = stacks.poll();
            } else if (current.getCount() == remainder.getCount()) {
                break; // Can't be inserted
            } else {
                current = remainder;
            }
        }
        return current == null && stacks.isEmpty();
    }

    public NetworkRegistry.TargetPoint asTarget(int range) {
        return new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range);
    }
}
