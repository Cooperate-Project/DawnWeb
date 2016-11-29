package de.cooperateproject.dawnweb.headless;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * Application that does intentionally nothing.
 */
public class Application implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		return  IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		return;
	}

}
