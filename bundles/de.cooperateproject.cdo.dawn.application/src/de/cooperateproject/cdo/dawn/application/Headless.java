package de.cooperateproject.cdo.dawn.application;

import java.util.concurrent.CompletableFuture;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Headless implements IApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(Headless.class);
	private static final String APP_NAME = "Cooperate CDO Dawn Backend";
	private CompletableFuture<Object> stopFuture;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		LOGGER.info("{} starting...", APP_NAME);

		context.applicationRunning();

		stopFuture = new CompletableFuture<Object>();
		Object result = stopFuture.get();
		stopFuture = null;

		LOGGER.info("{} terminating...", APP_NAME);
		return result;
	}

	@Override
	public void stop() {
		if (stopFuture != null) {
			stopFuture.complete(EXIT_OK);
		}
	}

}
