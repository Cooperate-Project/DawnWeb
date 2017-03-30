package de.cooperateproject.cdo.dawn.rest;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

	private final static Logger LOG = LoggerFactory.getLogger(Activator.class);
	private static BundleContext context;
	
	private ServiceRegistration<BrowseService> registration;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		LOG.info("Dawn REST starting");
		Activator.context = bundleContext;
		registration = bundleContext.registerService(BrowseService.class, new BrowseService(), null);
		LOG.info("Dawn REST started");

	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		registration.unregister();
		Activator.context = null;
	}

}
