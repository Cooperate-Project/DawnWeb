package de.cooperateproject.cdo.dawn.rest.accessible.util;

import de.cooperateproject.cdo.dawn.rest.accessible.api.AccessibleService;
import de.cooperateproject.cdo.dawn.rest.accessible.impl.AccessibleServiceImpl;

/**
 * The Service Factory creates instances of the accessible service API with the
 * project private implementations.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class ServiceFactory {

	private static ServiceFactory instance = new ServiceFactory();

	public static ServiceFactory getInstance() {
		return instance;
	}

	public AccessibleService getAccessibleService() {
		return new AccessibleServiceImpl();
	}

}
