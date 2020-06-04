package gregicadditions.oc.driver;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MetaTileEntity;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;

public class EnergyContainerDriver extends AbstractGregTechDriverBlock<IEnergyContainer> {
    public EnergyContainerDriver() {
        super(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER);
    }

    public static class EnergyContainerDriverEnvironment extends AbstractManagedDriverEnvironment
    {
        private final IEnergyContainer energyContainer;

        public EnergyContainerDriverEnvironment(IEnergyContainer energyContainer, String preferredName) {
            super(preferredName);
            this.energyContainer = energyContainer;
        }

        @Callback(doc = "function():long;Returns the total energy storage capacity", direct = true)
        public Object[] getEnergyCapacity(Context context, Arguments arguments) {
            return new Object[]{this.energyContainer.getEnergyCapacity()};
        }

        @Callback(doc = "function():long;Returns the total energy currently stored", direct = true)
        public Object[] getEnergyStored(Context context, Arguments arguments) {
            return new Object[]{this.energyContainer.getEnergyStored()};
        }

        @Callback(doc = "function():long;Returns the input amperage", direct = true)
        public Object[] getInputAmperage(Context context, Arguments arguments) {
            return new Object[]{this.energyContainer.getInputAmperage()};
        }

        @Callback(doc = "function():long;Returns the output amperage", direct = true)
        public Object[] getOutputAmperage(Context context, Arguments arguments) {
            return new Object[]{this.energyContainer.getOutputAmperage()};
        }

        @Callback(doc = "function():long;Returns the input voltage", direct = true)
        public Object[] getInputVoltage(Context context, Arguments arguments) {
            return new Object[]{this.energyContainer.getInputVoltage()};
        }

        @Callback(doc = "function():long;Returns the output voltage", direct = true)
        public Object[] getOutputVoltage(Context context, Arguments arguments) {
            return new Object[]{this.energyContainer.getOutputVoltage()};
        }
    }

    @Override
    public ManagedEnvironment createEnvironment(IEnergyContainer tile, MetaTileEntity entity) {
        return new EnergyContainerDriverEnvironment(tile, entity.getMetaName());
    }
}
