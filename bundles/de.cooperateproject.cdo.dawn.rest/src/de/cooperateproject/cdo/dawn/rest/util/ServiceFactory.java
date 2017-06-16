package de.cooperateproject.cdo.dawn.rest.util;

import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.api.TestService;
import de.cooperateproject.cdo.dawn.rest.api.UtilService;
import de.cooperateproject.cdo.dawn.rest.impl.BrowseServiceImpl;
import de.cooperateproject.cdo.dawn.rest.impl.DiagramServiceImpl;
import de.cooperateproject.cdo.dawn.rest.impl.TestServiceImpl;
import de.cooperateproject.cdo.dawn.rest.impl.UtilServiceImpl;

public class ServiceFactory {

	private static ServiceFactory instance = new ServiceFactory();

	public static ServiceFactory getInstance() {
		return instance;
	}

	public BrowseService getBrowseService() {
		return new BrowseServiceImpl();
	}

	public DiagramService getDiagramService() {
		return new DiagramServiceImpl();
	}

	public TestService getTestService() {
		return new TestServiceImpl();
	}

	public UtilService getUtilService() {
		return new UtilServiceImpl();
	}

}
