package de.cooperateproject.cdo.dawn.rest;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.eclipsesource.jaxrs.provider.gson.GsonProvider;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.api.TestService;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;

public class Activator implements BundleActivator {

	private List<ServiceRegistration<? extends Object>> registrations = new ArrayList<ServiceRegistration<? extends Object>>();

	public void start(BundleContext bundleContext) throws Exception {
		modifyJaxRsGsonProvider(bundleContext);

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

	@SuppressWarnings("rawtypes")
	private void modifyJaxRsGsonProvider(BundleContext bundleContext) {
		ServiceReference<GsonProvider> gsonProviderReference = bundleContext.getServiceReference(GsonProvider.class);
		GsonProvider<?> provider = (GsonProvider) bundleContext.getService(gsonProviderReference);

		// TODO: EObject to JSON
		// TODO: XML to JSON
		
		// TODO: Better way?
		Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				return false;
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		}).create();
		
		
		provider.setGson(gson);
	}

}
