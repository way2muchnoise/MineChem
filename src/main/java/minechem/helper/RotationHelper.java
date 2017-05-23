package minechem.helper;

import net.minecraft.client.renderer.EnumFaceDirection;

public class RotationHelper
{
    public static final EnumFaceDirection[] ROTATION_DIRECTIONS =
    {
        EnumFaceDirection.SOUTH, EnumFaceDirection.WEST, EnumFaceDirection.NORTH, EnumFaceDirection.EAST
    };

    public static EnumFaceDirection getDirectionFromMetadata(int meta)
    {
        return ROTATION_DIRECTIONS[meta];
    }

}
