package gregicadditions.oc;

import gregicadditions.oc.driver.EnergyContainerDriver;
import gregicadditions.oc.driver.ControllableMachineDriver;
import li.cil.oc.api.Driver;

public class OpenComputerIntegration {
    public static void initDrivers() {
        Driver.add(new EnergyContainerDriver());
        Driver.add(new ControllableMachineDriver());
    }
}
