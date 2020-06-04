package gregicadditions.oc.driver;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.metatileentity.MetaTileEntity;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;

public class ControllableMachineDriver extends AbstractGregTechDriverBlock<IControllable> {
    public ControllableMachineDriver() {
        super(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE);
    }

    public static class ControllableMachineDriverEnvironment extends AbstractManagedDriverEnvironment
            implements ManagedEnvironment, NamedBlock {
        private final IControllable controllable;

        public ControllableMachineDriverEnvironment(IControllable controllable, String preferredName) {
            super(preferredName);
            this.controllable = controllable;
        }

        @Callback(doc = "function():boolean;Returns whether the machine is allowed to work", direct = true)
        public Object[] isWorkingEnabled(Context context, Arguments arguments) {
            return new Object[]{this.controllable.isWorkingEnabled()};
        }

        @Callback(doc = "function(isActivationAllowed:boolean):void;Enables or disables the machine", direct = false)
        public Object[] setWorkingEnabled(Context context, Arguments arguments) {
            this.controllable.setWorkingEnabled(arguments.isBoolean(0) && arguments.checkBoolean(0));
            return null;
        }
    }

    @Override
    public ManagedEnvironment createEnvironment(IControllable tile, MetaTileEntity entity) {
        return new ControllableMachineDriverEnvironment(tile, entity.getMetaName());
    }
}
