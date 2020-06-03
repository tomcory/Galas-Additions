package gregicadditions.oc.driver;

import gregicadditions.GregicAdditions;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import li.cil.oc.api.driver.DriverBlock;
import li.cil.oc.api.network.ManagedEnvironment;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

abstract public class AbstractGregTechDriverBlock<T extends MetaTileEntity> implements DriverBlock {
    private final Class<T> entityFilter;

    public AbstractGregTechDriverBlock(Class<T> entityFilter)
    {
        this.entityFilter = entityFilter;
    }

    /**
     * Optionally returns the GregTech Meta Tile Entity (which differ from the minecraft tile entities)
     */
    @SuppressWarnings("unchecked")
    protected Optional<T> getMetaTileEntity(World world, BlockPos blockPos)
    {
        final TileEntity tile = world.getTileEntity(blockPos);

        // Make sure the tile is a meta tile entity holder
        if (tile == null || ! MetaTileEntityHolder.class.isAssignableFrom(tile.getClass())) {
            return Optional.empty();
        }

        // Make sure the greg tech tile entity implements the desired interface
        final MetaTileEntity entity = ((MetaTileEntityHolder) tile).getMetaTileEntity();
        if (entity == null || ! this.entityFilter.isAssignableFrom(entity.getClass())) {
            return Optional.empty();
        }

        // Unchecked cast
        return Optional.of((T) entity);
    }

    @Override
    public boolean worksWith(World world, BlockPos blockPos, EnumFacing enumFacing) {
        return getMetaTileEntity(world, blockPos).isPresent();
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, BlockPos blockPos, EnumFacing enumFacing)
            throws RuntimeException
    {
        final Optional<T> gtMetaTileEntity = getMetaTileEntity(world, blockPos);
        if (! gtMetaTileEntity.isPresent()) {
            throw new RuntimeException("Tried to get a GregTech MetaTileEntity from a non-holder.");
        }

        return createEnvironment(gtMetaTileEntity.get());
    }

    abstract public ManagedEnvironment createEnvironment(T tile);
}
