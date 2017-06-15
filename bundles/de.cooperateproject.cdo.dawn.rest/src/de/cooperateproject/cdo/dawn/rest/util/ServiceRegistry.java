package de.cooperateproject.cdo.dawn.rest.util;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.ServiceRegistration;

public class ServiceRegistry {

	private List<ServiceRegistration<? extends Object>> registrations;

	public ServiceRegistry() {
		registrations = new ArrayList<ServiceRegistration<? extends Object>>();
	}

	public void addService(ServiceRegistration<? extends Object> registration) {
		registrations.add(registration);
	}

	public void unregisterAll() {
		registrations.forEach((registration) -> registration.unregister());
	}

}
