package gregicadditions.oc.driver;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.common.metatileentities.electric.MetaTileEntityBatteryBuffer;
import li.cil.oc.api.Network;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;

public class BatterBufferDriver extends AbstractGregTechDriverBlock<MetaTileEntityBatteryBuffer> {
    public BatterBufferDriver() {
        super(MetaTileEntityBatteryBuffer.class);
    }

    public static class BatteryBufferEnvironment extends AbstractManagedEnvironment
            implements ManagedEnvironment, NamedBlock {
        private final MetaTileEntityBatteryBuffer entity;
        private final IEnergyContainer energyContainer;
        private final String preferredName;

        public BatteryBufferEnvironment(MetaTileEntityBatteryBuffer entity, String preferredName) {
            this.entity = entity;
            this.preferredName = preferredName;
            this.energyContainer = entity.getCapability(
                    GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER,
                    entity.getFrontFacing().getOpposite()
            );

            this.setNode(Network.newNode(this, Visibility.Network)
                    .withComponent(preferredName, Visibility.Network)
                    .create()
            );
        }

        @Callback(doc = "function():boolean;Returns whether the machine is allowed to work", direct = true)
        public Object[] isWorkEnabled(Context context, Arguments arguments) {
            return new Object[]{this.entity.isWorkingEnabled()};
        }

        @Callback(doc = "function(isWorkEnabled:boolean):void;Enables or disables the machine", direct = false)
        public Object[] setWorkEnabled(Context context, Arguments arguments) {
            this.entity.setWorkingEnabled(arguments.isBoolean(0) && arguments.checkBoolean(0));
            return null;
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

        @Override
        public String preferredName() {
            return this.preferredName;
        }

        @Override
        public int priority() {
            return 1;
        }
    }

    @Override
    public ManagedEnvironment createEnvironment(MetaTileEntityBatteryBuffer tile) {
        return new BatteryBufferEnvironment(tile, "gtce_battery_buffer");
    }
}
