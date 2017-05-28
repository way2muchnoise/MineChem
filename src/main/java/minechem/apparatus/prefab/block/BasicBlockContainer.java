package minechem.apparatus.prefab.block;

import java.util.ArrayList;
import minechem.Compendium;
import minechem.Minechem;
import minechem.helper.ItemHelper;
import minechem.helper.ResearchHelper;
import minechem.registry.CreativeTabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Extendable class for simple container blocks
 */
public abstract class BasicBlockContainer extends BlockContainer
{
    private AxisAlignedBB boudningBox;

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
        this.boudningBox = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.boudningBox;
    }

    /**
     * Define what stacks get dropped when the block is broken, defaults to nothing
     *
     * @param tileEntity
     * @param itemStacks
     */
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {
    }

    /**
     * Called when the block is broken
     *
     * @param world    the world object
     * @param pos      the x,y,z position
     * @param state     the state of the block being broken
     */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        Block block = state.getBlock();
        if (block instanceof BasicBlockContainer)
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity != null)
            {
                ArrayList<ItemStack> droppedStacks = new ArrayList<ItemStack>();

                if (dropInventory())
                {
                    if (tileEntity instanceof IInventory)
                    {
                        IInventory inventory = (IInventory) tileEntity;
                        for (int i = 0; i < inventory.getSizeInventory(); i++)
                        {
                            ItemStack stack = inventory.getStackInSlot(i);
                            if (stack != null)
                            {
                                droppedStacks.add(stack);
                            }
                        }
                    }
                }

                addStacksDroppedOnBlockBreak(tileEntity, droppedStacks);
                for (ItemStack itemstack : droppedStacks)
                {
                    ItemHelper.throwItemStack(world, itemstack, pos);
                }
                super.breakBlock(world, pos, state);
            }
        }
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
     * Override to allow inventory dropping to be toggled
     *
     * @return boolean true
     */
    public boolean dropInventory()
    {
        return true;
    }

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
            acquireResearch(player, world);
            if (!world.isRemote)
            {
                player.openGui(Minechem.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }

        return false;
    }

    /**
     * Acquire the research for the block
     *
     * @param player
     * @param world
     */
    public void acquireResearch(EntityPlayer player, World world)
    {
        ResearchHelper.addResearch(player, getResearchKey(), world.isRemote);
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
