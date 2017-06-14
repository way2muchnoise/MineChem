package minechem.apparatus.acidBath;

import minechem.apparatus.prefab.tileEntity.BasicTileTickingEntity;
import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import minechem.apparatus.prefab.tileEntity.storageTypes.INBTWritable;
import minechem.apparatus.prefab.tileEntity.storageTypes.ProcessingInventory;
import minechem.chemical.ChemicalBase;
import minechem.chemical.process.ChemicalProcessType;
import minechem.item.chemical.ChemicalItem;
import minechem.registry.AcidRegistry;
import minechem.registry.BlockRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;

public class AcidBathTileEntity extends BasicTileTickingEntity {
    private BasicInventory inventoryIn, inventoryOut, inventoryAcid;
    private ProcessingInventory processingInventory;
    private int currentAcid;

    public AcidBathTileEntity() {
        super(BlockRegistry.acidBathBlock);
        this.inventoryIn = new BasicInventory(1, "insert").setListener(this);
        this.inventoryOut = new BasicInventory(6, "extract").setListener(this).setOutput();
        this.inventoryAcid = new BasicInventory(1, "acid").setListener(this);
        this.processingInventory = new ProcessingInventory(5).setListener(this);
        this.currentAcid = 0;
        attachCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, BasicInventory.asCapability(inventoryIn, inventoryOut, inventoryAcid));
        registerDropableInventories(inventoryIn, inventoryOut); // You don't get the processing inventory back
    }

    public BasicInventory getInventoryIn() {
        return inventoryIn;
    }

    public BasicInventory getInventoryOut() {
        return inventoryOut;
    }

    public BasicInventory getInventoryAcid() {
        return inventoryAcid;
    }

    public int getProgression() {
        return processingInventory.getProgress();
    }

    @Override
    public void update() {
        doChemicalProcessUpdate(inventoryIn, inventoryOut, processingInventory, ChemicalProcessType.acid);
        super.update();
    }

    @Override
    protected boolean processingTick() {
        if (currentAcid > 0) {
            currentAcid--;
            return true;
        } else if (!inventoryAcid.isEmpty()) {
            ItemStack stack = inventoryAcid.decrStackSize(0, 1);
            ChemicalBase chemicalBase = ChemicalItem.getChemicalBase(stack);
            if (chemicalBase != null) {
                int acidValue = AcidRegistry.getInstance().getAcidValue(chemicalBase);
                if (acidValue > 0) {
                    currentAcid = acidValue - 1;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        INBTWritable.readNBT(nbttagcompound, inventoryIn, inventoryOut, processingInventory, inventoryAcid);
        this.currentAcid = nbttagcompound.getInteger("currentAcid");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound = super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("currentAcid", currentAcid);
        return INBTWritable.writeNBT(nbttagcompound, inventoryIn, inventoryOut, processingInventory, inventoryAcid);
    }
}
