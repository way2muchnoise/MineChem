package minechem.apparatus.molecularConstructor;

import minechem.Config;
import minechem.apparatus.prefab.tileEntity.BasicTileTickingEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.*;
import minechem.chemical.ChemicalBase;
import minechem.handler.MessageHandler;
import minechem.handler.message.MolecularCraftMessage;
import minechem.helper.Jenkins;
import minechem.item.chemical.ChemicalItem;
import minechem.registry.BlockRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class MolecularConstructorTileEntity extends BasicTileTickingEntity {
    private ChemicalInventory input;
    private BasicInventory output;
    private ProcessingInventory processingInventory;
    private BasicEnergyStorage energy;
    private ItemStack currentRecipe;

    public MolecularConstructorTileEntity() {
        super(BlockRegistry.molecularConstructor);
        this.input = new ChemicalInventory(9, "input").setListener(this).sendUpdates();
        this.output = new BasicInventory(1, "output").setListener(this).setOutput();
        this.processingInventory = new ProcessingInventory(5).setListener(this);
        this.energy = new BasicEnergyStorage(10000).setListener(this);
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, BasicInventory.asCapability(input, output));
        attachCapability(CapabilityEnergy.ENERGY, this.energy);
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

    @Override
    public void update() {
        super.update();
        doGeneralProcessUpdate(processingInventory, output);
    }

    public void startProcessing() {
        if (world.isRemote) {
            MessageHandler.INSTANCE.sendToServer(new MolecularCraftMessage(this));
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

    @Override
    protected boolean processingTick() {
        return tryAndExtractEnergy(energy, 2 * Config.energyConsumption);
    }

    public ItemStack getCurrentRecipe() {
        return processingInventory.isEmpty() ? currentRecipe : processingInventory.getStackInSlot(0);
    }

    private ItemStack getCurrentRecipeInternal() {
        StringBuilder formula = new StringBuilder();
        for (ItemStack stack : input.getAllStacks()) {
            if (!stack.isEmpty()) {
                ChemicalBase chemical = ChemicalItem.getChemicalBase(stack);
                if (chemical != null) {
                    if (!chemical.isElement()) {
                        formula.append("(");
                    }
                    formula.append(chemical.getFormula());
                    if (!chemical.isElement()) {
                        formula.append(")");
                    }
                    if (stack.getCount() > 1) {
                        formula.append(stack.getCount());
                    }
                }
            }
        }
        return Jenkins.getMoleculeStack(formula.toString());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return INBTWritable.writeNBT(super.writeToNBT(compound), input, output, processingInventory, energy);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        INBTWritable.readNBT(compound, input, output, processingInventory, energy);
        this.currentRecipe = getCurrentRecipeInternal();
    }
}
