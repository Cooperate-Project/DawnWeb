package de.cooperateproject.cdo.dawn.rest;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.net4j.util.lifecycle.LifecycleException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.api.TestService;
import de.cooperateproject.cdo.dawn.rest.api.UtilService;
import de.cooperateproject.cdo.dawn.rest.util.CORSFilter;
import de.cooperateproject.cdo.dawn.rest.util.EMFReadyProvider;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;
import de.cooperateproject.cdo.dawn.rest.util.ServiceRegistry;
import de.cooperateproject.cdo.dawn.session.CDOConnectionManager;
import io.swagger.config.ScannerFactory;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jaxrs.config.SwaggerScannerLocator;

/**
 * This activater is called on startup of the rest service project.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class Activator implements BundleActivator {

	private final static Logger LOG = LoggerFactory.getLogger(Activator.class);

	ServiceRegistry serviceRegistry = new ServiceRegistry();

	public void start(BundleContext bundleContext) throws Exception {

		// Test CDO server connection and log failure
		try {
			CDOConnectionManager.INSTANCE.acquireSession();
		} catch (LifecycleException ex) {
			LOG.warn("Unable to connect to cdo server.");
		}

		registerProviders(bundleContext);
		configureSwagger(bundleContext);

		// Registers all REST services to the registry
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
		CDOConnectionManager.INSTANCE.releaseSession();
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

	private void configureSwagger(BundleContext bundleContext) throws Exception {

		// Get swagger configuration, then insert own properties
		ServiceReference<?> reference = bundleContext.getServiceReference(ConfigurationAdmin.class.getName());
		ConfigurationAdmin configAdmin = (ConfigurationAdmin) bundleContext.getService(reference);
		Configuration configuration = configAdmin.getConfiguration("com.eclipsesource.jaxrs.swagger.config", null);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("swagger.basePath", "/services");
		properties.put("swagger.host", "localhost:9090");
		properties.put("swagger.info.title", "Dawn WEB Editor API");
		properties.put("swagger.info.description",
				"Provides access to a cdo repository and utility methods to work with models.");
		properties.put("swagger.info.version", "1.0.0");
		properties.put("swagger.info.license.name", "Eclipse Public License, version 1.0");
		properties.put("swagger.info.license.url", "http://www.eclipse.org/legal/epl-v10.html");
		configuration.update(properties);
		bundleContext.ungetService(reference);

		// Add OSGIJaxRsScanner to the swagger scanner locator to get loaded
		// properly (workaround due to a bug in the osgi jax-rs project)
		SwaggerScannerLocator.getInstance().putScanner(SwaggerContextService.SCANNER_ID_DEFAULT,
				ScannerFactory.getScanner());
	}

}
