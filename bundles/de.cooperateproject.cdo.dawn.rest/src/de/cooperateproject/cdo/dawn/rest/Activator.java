package de.cooperateproject.cdo.dawn.rest;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cooperateproject.cdo.dawn.rest.impl.BrowseServiceImpl;

public class Activator implements BundleActivator {

	private final static Logger LOG = LoggerFactory.getLogger(Activator.class);
	private static BundleContext context;
	
	// Just for testing purposes
	private ServiceRegistration<? extends de.cooperateproject.cdo.dawn.rest.api.BrowseService> registrationBrowse;
	private ServiceRegistration<DiagramService> registrationDiagram;
	private ServiceRegistration<? extends IResourceTest> registrationTest;

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
		registrationBrowse = bundleContext.registerService(BrowseServiceImpl.class, new BrowseServiceImpl(), null);
		registrationDiagram = bundleContext.registerService(DiagramService.class, new DiagramService(), null);
		registrationTest = bundleContext.registerService(ResourceTestImpl.class, new ResourceTestImpl(), null);
		LOG.info("Dawn REST started");

	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		registrationBrowse.unregister();
		registrationDiagram.unregister();
		registrationTest.unregister();
		Activator.context = null;
	}

}
