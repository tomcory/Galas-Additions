package gregicadditions.oc;

import gregicadditions.oc.driver.BatterBufferDriver;
import li.cil.oc.api.Driver;

public class OpenComputerIntegration {
    public static void initDrivers() {
        Driver.add(new BatterBufferDriver());
    }
}
