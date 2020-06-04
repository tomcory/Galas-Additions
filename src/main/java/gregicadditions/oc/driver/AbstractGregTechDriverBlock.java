package gregicadditions.oc.driver;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import li.cil.oc.api.driver.DriverBlock;
import li.cil.oc.api.network.ManagedEnvironment;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Optional;

abstract public class AbstractGregTechDriverBlock<T> implements DriverBlock {
    private final Capability<T> capability;

    public AbstractGregTechDriverBlock(Capability<T> capability) {
        this.capability = capability;
    }

    /**
     * Optionally returns the GregTech Meta Tile Entity (which differ from the minecraft tile entities)
     */
    protected Optional<MetaTileEntity> getMetaTileEntity(World world, BlockPos blockPos, EnumFacing facing) {
        final TileEntity tile = world.getTileEntity(blockPos);

        // Make sure the tile is a meta tile entity holder
        if (tile == null || !MetaTileEntityHolder.class.isAssignableFrom(tile.getClass())) {
            return Optional.empty();
        }

        // Make sure the greg tech tile entity implements the desired interface
        return Optional.of(((MetaTileEntityHolder) tile).getMetaTileEntity());
    }

    /**
     * Optionally returns the GregTech Capability
     */
    protected Optional<T> getMetaTileCapability(MetaTileEntity entity, EnumFacing facing) {
        return Optional.ofNullable(entity.getCoverCapability(this.capability, facing));
    }

    @Override
    public boolean worksWith(World world, BlockPos blockPos, EnumFacing facing) {
        Optional<MetaTileEntity> entity = this.getMetaTileEntity(world, blockPos, facing);
        return entity.isPresent() && this.getMetaTileCapability(entity.get(), facing).isPresent();
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, BlockPos blockPos, EnumFacing facing)
            throws RuntimeException {
        // Ensure we can still retrieve the MetaTileEntity
        final Optional<MetaTileEntity> metaTileEntity = this.getMetaTileEntity(world, blockPos, facing);
        if (!metaTileEntity.isPresent()) {
            throw new RuntimeException("Setting up driver environment failed due to missing GTCE MetaTileEntity");
        }

        // Ensure the Capability is still preset
        final Optional<T> capability = this.getMetaTileCapability(metaTileEntity.get(), facing);
        if (!capability.isPresent()) {
            throw new RuntimeException("Setting up driver environment failed due to missing capability");
        }

        return createEnvironment(capability.get(), metaTileEntity.get());
    }

    abstract public ManagedEnvironment createEnvironment(T tile, MetaTileEntity entity);
}
