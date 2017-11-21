package de.cooperateproject.cdo.dawn.application;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class Headless implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		return;
	}

}	
