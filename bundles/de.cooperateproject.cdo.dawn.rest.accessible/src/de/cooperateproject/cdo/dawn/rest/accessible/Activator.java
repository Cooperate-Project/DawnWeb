package de.cooperateproject.cdo.dawn.rest.accessible;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.cooperateproject.cdo.dawn.rest.accessible.api.AccessibleService;
import de.cooperateproject.cdo.dawn.rest.accessible.util.ServiceFactory;
import de.cooperateproject.cdo.dawn.rest.util.ServiceRegistry;

/**
 * This activator is called on startup of the accessible rest project.
 * @author Sebastian Hahner (sebinside)
 *
 */
public class Activator implements BundleActivator {

	ServiceRegistry serviceRegistry = new ServiceRegistry();

	public void start(BundleContext bundleContext) throws Exception {
		// Register the accessible rest service
		serviceRegistry.addService(bundleContext.registerService(AccessibleService.class,
				ServiceFactory.getInstance().getAccessibleService(), null));
	}

	public void stop(BundleContext bundleContext) throws Exception {
		serviceRegistry.unregisterAll();
	}

}
