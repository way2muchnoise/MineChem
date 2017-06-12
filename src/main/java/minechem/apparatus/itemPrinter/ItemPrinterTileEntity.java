package minechem.apparatus.itemPrinter;

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

public class ItemPrinterTileEntity extends BasicTileTickingEntity {
    private BasicInventory input, output;
    private ProcessingInventory processingInventory;
    private BasicEnergyStorage energy;
    private BasicFluidTank tank;
    private ItemStack currentRecipe;

    public ItemPrinterTileEntity() {
        super(BlockRegistry.itemPrinter);
        this.input = new ChemicalInventory(9, "input").setListener(this).sendUpdates();
        this.output = new BasicInventory(1, "output").setListener(this);
        this.processingInventory = new ProcessingInventory(5).setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        this.tank = new BasicFluidTank(3000).setListener(this);
        this.currentRecipe = getCurrentRecipeInternal();
    }

    public BasicInventory getInput() {
        return input;
    }

    public BasicInventory getOutput() {
        return output;
    }

    public BasicEnergyStorage getEnergy() {
        return energy;
    }

    public ProcessingInventory getProcessingInventory() {
        return processingInventory;
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
        if (!processingInventory.isEmpty()) {
            if (processingInventory.isDone() || (processingTick() && processingInventory.update())) {
                ItemStack result = processingInventory.decrStackSize(0, 1);
                if (output.asCapability().insertItem(0, result, true).isEmpty()) {
                    output.asCapability().insertItem(0, result, false);
                    processingInventory.reset();
                } else {
                    processingInventory.addItem(result);
                }
            }
        }
    }

    public void startProcessing() {
        if (world.isRemote) {
            MessageHandler.INSTANCE.sendToServer(new ItemPrinterMessage(this));
        } else {
            if (processingInventory.isEmpty()) {
                ItemStack result = getCurrentRecipeInternal();
                if (!result.isEmpty()) {
                    processingInventory.addItem(result);
                    input.clear();
                }
            }
        }
    }

    public ItemStack getCurrentRecipe() {
        return processingInventory.isEmpty() ? currentRecipe : processingInventory.getStackInSlot(0);
    }

    private ItemStack getCurrentRecipeInternal() {
        Chemical[][] recipe = new Chemical[PrintingRecipe.SIZE][PrintingRecipe.SIZE];
        for (int i = 0; i < PrintingRecipe.SIZE * PrintingRecipe.SIZE; i++) {
            recipe[i%3][i/3] = new Chemical(getInput().getStackInSlot(i));
        }
        PrintingRecipe printingRecipe = PrintingRegistry.getInstance().matches(recipe);
        return printingRecipe == null ? ItemStack.EMPTY : printingRecipe.getResult();
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
