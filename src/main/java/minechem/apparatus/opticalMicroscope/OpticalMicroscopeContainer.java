package minechem.apparatus.opticalMicroscope;

import minechem.apparatus.prefab.gui.container.BasicContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class OpticalMicroscopeContainer extends BasicContainer
{
    /**
     * Container object for the opticalMicroscope
     *
     * @param inventoryPlayer   the player's inventory
     * @param opticalMicroscope the microscope TileEntity
     */
    public OpticalMicroscopeContainer(InventoryPlayer inventoryPlayer, OpticalMicroscopeTileEntity opticalMicroscope)
    {
        bindPlayerInventory(inventoryPlayer);
        addSlotToContainer(new Slot(opticalMicroscope.getInventory(), 0, 32, 32) {
            @Override
            public int getSlotStackLimit() {
                return 1;
            }
        });
    }
}
