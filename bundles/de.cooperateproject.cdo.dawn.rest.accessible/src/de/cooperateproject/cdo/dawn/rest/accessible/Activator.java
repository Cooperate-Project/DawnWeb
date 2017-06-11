package de.cooperateproject.cdo.dawn.rest.accessible;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private ServiceRegistration<TestService> registrationTest;

	public void start(BundleContext context) throws Exception {
		registrationTest = context.registerService(TestService.class, new TestService(), null);
	}

	public void stop(BundleContext context) throws Exception {
		registrationTest.unregister();
	}

}
