package de.cooperateproject.cdo.dawn.rest.util;

import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.rest.api.TestService;
import de.cooperateproject.cdo.dawn.rest.impl.BrowseServiceImpl;
import de.cooperateproject.cdo.dawn.rest.impl.TestServiceImpl;

public class ServiceFactory {

	private static ServiceFactory instance = new ServiceFactory();
	
	public static ServiceFactory getInstance() {
		return instance;
	}

	public BrowseService getBrowseService() {
		return new BrowseServiceImpl();
	}
	
	public TestService getTestService() {
		return new TestServiceImpl();
	}

}
