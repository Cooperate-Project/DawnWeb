package de.cooperateproject.cdo.dawn.rest.accessible.util;

import de.cooperateproject.cdo.dawn.rest.accessible.api.AccessibleService;
import de.cooperateproject.cdo.dawn.rest.accessible.impl.AccessibleServiceImpl;

public class ServiceFactory {

	private static ServiceFactory instance = new ServiceFactory();

	public static ServiceFactory getInstance() {
		return instance;
	}

	public AccessibleService getAccessibleService() {
		return new AccessibleServiceImpl();
	}

}
