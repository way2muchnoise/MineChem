package minechem.apparatus.prefab.block;

import java.util.ArrayList;
import minechem.Compendium;
import minechem.Minechem;
import minechem.apparatus.prefab.tileEntity.BasicTileEntity;
import minechem.helper.AchievementHelper;
import minechem.helper.ItemHelper;
import minechem.helper.ResearchHelper;
import minechem.registry.CreativeTabRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

/**
 * Extendable class for simple container blocks
 */
public abstract class BasicBlockContainer extends BlockContainer
{
    private AxisAlignedBB boundingBox;

    /**
     * Unnamed blocks are given a default name
     */
    public BasicBlockContainer()
    {
        this(Compendium.Naming.name + " Basic Block");
    }

    /**
     * Create a basic block with a given name
     *
     * @param blockName unlocalized name of the block
     */
    public BasicBlockContainer(String blockName)
    {
        this(blockName, Material.GRASS, SoundType.GROUND);
    }

    /**
     * Create a basic block with a given name, material, and sound
     *
     * @param blockName unlocalized name of the block
     * @param material  Material type
     * @param sound     Block sound type
     */
    public BasicBlockContainer(String blockName, Material material, SoundType sound)
    {
        super(material);
        setRegistryName(Compendium.Naming.id, blockName);
        setSoundType(sound);
        setUnlocalizedName(blockName);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
    }

    public void setBlockBounds(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.boundingBox = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.boundingBox;
    }

    public void addStacksDroppedOnBlockBreak(BasicTileEntity basicTileEntity, ArrayList<ItemStack> itemStacks) {
        basicTileEntity.addStacksDroppedOnBlockBreak(itemStacks);
    }

    /**
     * Called when the block is broken
     *
     * @param world    the world object
     * @param pos      the x,y,z position
     * @param state     the form of the block being broken
     */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity != null && tileEntity instanceof BasicTileEntity)
        {
            ArrayList<ItemStack> droppedStacks = new ArrayList<>();
            addStacksDroppedOnBlockBreak((BasicTileEntity) tileEntity, droppedStacks);
            for (ItemStack itemstack : droppedStacks) {
                ItemHelper.throwItemStack(world, itemstack, pos);
            }
        }
        super.breakBlock(world, pos, state);
    }

    /**
     * Abstract method, to be overridden by child classes
     *
     * @param world the world object
     * @param meta  block metadata
     * @return the newly created TileEntity
     */
    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    /**
     * Open the GUI on block activation
     *
     * @param world  the game world object
     * @param pos      the x,y,z position
     * @param player the entityplayer object
     * @param facing   which side was hit
     * @param hitX   on the side that was hit, the x coordinate
     * @param hitY   on the side that was hit, the y coordinate
     * @param hitZ   on the side that was hit, the z coordinate
     * @return boolean does the block get activated
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity != null && !player.isSneaking())
        {
            if (tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)
                && player.getHeldItem(hand).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, facing)) {
                IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
                IFluidHandlerItem fluidHandlerItem = player.getHeldItem(hand).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, facing);
                if (!world.isRemote && fluidHandler != null && fluidHandlerItem != null) {
                    FluidStack willBeDrained = fluidHandlerItem.drain(Fluid.BUCKET_VOLUME, false);
                    if (willBeDrained != null) {
                        willBeDrained.amount = fluidHandler.fill(willBeDrained, false);
                        FluidStack drained = fluidHandlerItem.drain(willBeDrained.amount, true);
                        fluidHandler.fill(drained, true);
                    }
                }
                return true;
            }
            acquireResearch(player, world);
            openGui(player, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    public void openGui(EntityPlayer player, World world, int posX, int posY, int posZ) {
        int guiId = getGuiId();
        if (guiId != -1) {
            player.openGui(Minechem.INSTANCE, guiId, world, posX, posY, posZ);
        }
    }

    public int getGuiId() {
        return -1;
    }

    /**
     * Acquire the research for the block
     *
     * @param player
     * @param world
     */
    public void acquireResearch(EntityPlayer player, World world)
    {
        String researchKey = getResearchKey();
        ResearchHelper.addResearch(player, researchKey, world.isRemote);
        AchievementHelper.giveAchievement(player, researchKey, world.isRemote);
    }

    /**
     * Get the research key bound to the block
     *
     * @return
     */
    public String getResearchKey()
    {
        return "apparatus." + getRawUnlocalizedName();
    }

    /**
     * Get the unlocalized name without the "tile." prefix
     *
     * @return
     */
    private String getRawUnlocalizedName()
    {
        return getUnlocalizedName().substring(5);
    }

    /**
     * Set block metadata for model rotation
     *
     * @param world        the world object
     * @param pos      the x,y,z position
     * @param placer the entity that placed the block
     * @param stack    ItemStack object used to place the block
     */
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        int facing = MathHelper.fastFloor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        //world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }
}
