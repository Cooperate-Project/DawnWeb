package de.cooperateproject.cdo.dawn.rest;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.api.TestService;
import de.cooperateproject.cdo.dawn.rest.api.UtilService;
import de.cooperateproject.cdo.dawn.rest.util.CORSFilter;
import de.cooperateproject.cdo.dawn.rest.util.EMFReadyProvider;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;
import de.cooperateproject.cdo.dawn.rest.util.ServiceRegistry;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

public class Activator implements BundleActivator {

	ServiceRegistry serviceRegistry = new ServiceRegistry();

	public void start(BundleContext bundleContext) throws Exception {

		registerProviders(bundleContext);
		
		// FIXME: Configuration admin not found, although equinox is activated
		//configureSwagger(bundleContext);

		serviceRegistry.addService(bundleContext.registerService(BrowseService.class,
				ServiceFactory.getInstance().getBrowseService(), null));
		serviceRegistry.addService(bundleContext.registerService(DiagramService.class,
				ServiceFactory.getInstance().getDiagramService(), null));
		serviceRegistry.addService(
				bundleContext.registerService(TestService.class, ServiceFactory.getInstance().getTestService(), null));
		serviceRegistry.addService(
				bundleContext.registerService(UtilService.class, ServiceFactory.getInstance().getUtilService(), null));
	}

	public void stop(BundleContext bundleContext) throws Exception {
		serviceRegistry.unregisterAll();
	}

	private void registerProviders(BundleContext bundleContext) {

		// Registers an own provider which encapsulates the behavior of an
		// GsonProvider with the possibility to serialize EObjects with emfjson
		EMFReadyProvider<?> provider = new EMFReadyProvider<Object>();
		bundleContext.registerService(EMFReadyProvider.class.getName(), provider, null);

		// Cross Origin Filter to allow every request from e.g. a web browser
		CORSFilter corsFilter = new CORSFilter();
		bundleContext.registerService(CORSFilter.class, corsFilter, null);
	}

	@SuppressWarnings("unused")
	private void configureSwagger(BundleContext bundleContext) throws Exception {
		ServiceReference<?> reference = bundleContext.getServiceReference(ConfigurationAdmin.class);
		ConfigurationAdmin configAdmin = (ConfigurationAdmin) bundleContext.getService(reference);
		Configuration configuration = configAdmin.getConfiguration("com.eclipsesource.jaxrs.swagger.config", null);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("swagger.basePath", "/services");
		properties.put("swagger.host", "localhost:9090");
		properties.put("swagger.info.title", "Dawn Web Editor API");
		properties.put("swagger.info.license.name", "Eclipse Public License, version 1.0");
		properties.put("swagger.info.license.url", "http://www.eclipse.org/legal/epl-v10.html");
		configuration.update(properties);
		bundleContext.ungetService(reference);
	}

}
