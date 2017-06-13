package minechem.apparatus.itemPrinter;

import minechem.Config;
import minechem.apparatus.prefab.tileEntity.BasicTileTickingEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.*;
import minechem.chemical.Chemical;
import minechem.handler.MessageHandler;
import minechem.handler.message.ItemPrinterMessage;
import minechem.printing.PrintingRecipe;
import minechem.registry.BlockRegistry;
import minechem.registry.PrintingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class ItemPrinterTileEntity extends BasicTileTickingEntity {
    private ChemicalInventory input;
    private BasicInventory output;
    private ProcessingInventory processingInventory;
    private BasicEnergyStorage energy;
    private BasicFluidTank tank;
    private PrintingRecipe currentRecipe;

    public ItemPrinterTileEntity() {
        super(BlockRegistry.itemPrinter);
        this.input = new ChemicalInventory(9, "input").setListener(this).sendUpdates();
        this.output = new BasicInventory(1, "output").setListener(this).setOutput();
        this.processingInventory = new ProcessingInventory(5).setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        this.tank = new BasicFluidTank(3000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, BasicInventory.asCapability(input, output));
        attachCapability(CapabilityEnergy.ENERGY, this.energy);
        attachCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this.tank);
        this.currentRecipe = getCurrentRecipeInternal();
    }

    public ChemicalInventory getInput() {
        return input;
    }

    public BasicInventory getOutput() {
        return output;
    }

    public BasicEnergyStorage getEnergy() {
        return energy;
    }

    public int getProgression() {
        return processingInventory.getProgress();
    }

    public BasicFluidTank getTank() {
        return tank;
    }

    @Override
    public void update() {
        super.update();
        doGeneralProcessUpdate(processingInventory, output);
    }

    public void startProcessing() {
        if (world.isRemote) {
            MessageHandler.INSTANCE.sendToServer(new ItemPrinterMessage(this));
        } else {
            if (processingInventory.isEmpty()) {
                PrintingRecipe recipe = getCurrentRecipeInternal();
                if (recipe != null) {
                    processingInventory.addItem(recipe.getResult());
                    for (int i = 0; i < recipe.getRecipe().length; i++) {
                        for (int j = 0; j < recipe.getRecipe()[i].length; j++) {
                            if (!recipe.getRecipe()[i][j].isEmpty()) {
                                int slot  = i + j * 3;
                                ItemStack current = input.getStackInSlot(slot);
                                current.shrink(recipe.getRecipe()[i][j].getCount());
                                input.setInventorySlotContents(slot, current);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean processingTick() {
        if (tryAndExtractEnergy(energy, 3 * Config.energyConsumption)) {
            if (tryAndExtractFluid(tank, Config.fluidConsumption)) {
                return true;
            }
            energy.receiveEnergy(3 * Config.energyConsumption, false);
        }
        return false;
    }

    public ItemStack getCurrentRecipe() {
        if (processingInventory.isEmpty()) {
            return currentRecipe == null ? ItemStack.EMPTY : currentRecipe.getResult();
        } else {
            return processingInventory.getStackInSlot(0);
        }
    }

    private PrintingRecipe getCurrentRecipeInternal() {
        Chemical[][] recipe = new Chemical[PrintingRecipe.SIZE][PrintingRecipe.SIZE];
        for (int i = 0; i < PrintingRecipe.SIZE * PrintingRecipe.SIZE; i++) {
            recipe[i%3][i/3] = new Chemical(getInput().getStackInSlot(i));
        }
        return PrintingRegistry.getInstance().matches(recipe);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return INBTWritable.writeNBT(super.writeToNBT(compound), input, output, processingInventory, energy, tank);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        INBTWritable.readNBT(compound, input, output, processingInventory, energy, tank);
        this.currentRecipe = getCurrentRecipeInternal();
    }
}
