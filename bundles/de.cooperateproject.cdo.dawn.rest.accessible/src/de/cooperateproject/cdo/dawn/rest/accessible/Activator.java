package de.cooperateproject.cdo.dawn.rest.accessible;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
	
	private final static Logger LOG = LoggerFactory.getLogger(Activator.class);
	private static BundleContext context;
	
	private ServiceRegistration<TestService> registrationTest;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		LOG.info("Accessible Rest starting");
		Activator.context = context;
		registrationTest = context.registerService(TestService.class, new TestService(), null);
		LOG.info("Accessible Rest started");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		registrationTest.unregister();
	}

}
