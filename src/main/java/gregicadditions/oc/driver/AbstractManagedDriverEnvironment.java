package gregicadditions.oc.driver;

import li.cil.oc.api.Network;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;

/**
 * Base class for network driver environments
 */
abstract public class AbstractManagedDriverEnvironment
        extends AbstractManagedEnvironment
        implements ManagedEnvironment, NamedBlock
{
    private final String preferredName;

    protected AbstractManagedDriverEnvironment(String preferredName) {
        this(preferredName, Visibility.Network);
    }

    public AbstractManagedDriverEnvironment(String preferredName, Visibility reachability) {
        this.preferredName = preferredName.replace(".", "_");
        this.setNode(Network.newNode(this, reachability)
                .withComponent(preferredName, reachability)
                .create()
        );
    }

    @Override
    public String preferredName() {
        return this.preferredName;
    }

    @Override
    public int priority() {
        return 0;
    }
}
