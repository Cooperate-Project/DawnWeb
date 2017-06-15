package de.cooperateproject.cdo.dawn.rest;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.api.TestService;
import de.cooperateproject.cdo.dawn.rest.util.EMFReadyProvider;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;

public class Activator implements BundleActivator {

	private List<ServiceRegistration<? extends Object>> registrations = new ArrayList<ServiceRegistration<? extends Object>>();

	public void start(BundleContext bundleContext) throws Exception {

		registerEMFReadyProvider(bundleContext);

		registrations.add(bundleContext.registerService(BrowseService.class,
				ServiceFactory.getInstance().getBrowseService(), null));
		registrations.add(bundleContext.registerService(DiagramService.class,
				ServiceFactory.getInstance().getDiagramService(), null));
		registrations.add(
				bundleContext.registerService(TestService.class, ServiceFactory.getInstance().getTestService(), null));
	}

	public void stop(BundleContext bundleContext) throws Exception {
		registrations.forEach((registration) -> registration.unregister());
	}

	private void registerEMFReadyProvider(BundleContext bundleContext) {

		// Registers an own provider which encapsulates the behavior of an
		// GsonProvider with the possibility to serialize EObjects with emfjson
		EMFReadyProvider<?> provider = new EMFReadyProvider<Object>();
		bundleContext.registerService(EMFReadyProvider.class.getName(), provider, null);
	}

}
