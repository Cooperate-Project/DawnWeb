package de.cooperateproject.cdo.dawn.rest;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.api.TestService;
import de.cooperateproject.cdo.dawn.rest.util.EMFReadyProvider;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;
import de.cooperateproject.cdo.dawn.rest.util.ServiceRegistry;

public class Activator implements BundleActivator {

	ServiceRegistry serviceRegistry = new ServiceRegistry();

	public void start(BundleContext bundleContext) throws Exception {

		registerEMFReadyProvider(bundleContext);

		serviceRegistry.addService(bundleContext.registerService(BrowseService.class,
				ServiceFactory.getInstance().getBrowseService(), null));
		serviceRegistry.addService(bundleContext.registerService(DiagramService.class,
				ServiceFactory.getInstance().getDiagramService(), null));
		serviceRegistry.addService(
				bundleContext.registerService(TestService.class, ServiceFactory.getInstance().getTestService(), null));
	}

	public void stop(BundleContext bundleContext) throws Exception {
		serviceRegistry.unregisterAll();
	}

	private void registerEMFReadyProvider(BundleContext bundleContext) {

		// Registers an own provider which encapsulates the behavior of an
		// GsonProvider with the possibility to serialize EObjects with emfjson
		EMFReadyProvider<?> provider = new EMFReadyProvider<Object>();
		bundleContext.registerService(EMFReadyProvider.class.getName(), provider, null);
	}

}
