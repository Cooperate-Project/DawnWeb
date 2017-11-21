package de.cooperateproject.cdo.dawn.rest.util;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.ServiceRegistration;

/**
 * The service registry can be used to register and unregister (rest) services.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class ServiceRegistry {

	private List<ServiceRegistration<? extends Object>> registrations;

	/**
	 * Creates a new service registry.
	 */
	public ServiceRegistry() {
		registrations = new ArrayList<ServiceRegistration<? extends Object>>();
	}

	/**
	 * Registers a new service at the registry.
	 * 
	 * @param registration
	 */
	public void addService(ServiceRegistration<? extends Object> registration) {
		registrations.add(registration);
	}

	/**
	 * Calls the unregister method for all previously registered services.
	 */
	public void unregisterAll() {
		registrations.forEach((registration) -> registration.unregister());
	}

}
