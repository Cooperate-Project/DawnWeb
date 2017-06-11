package de.cooperateproject.cdo.dawn.test;

import org.junit.Assert;
import org.junit.Test;

import de.cooperateproject.cdo.dawn.rest.api.TestService;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;

public class TestSession {

	@Test
	public void testService() {
		TestService service = ServiceFactory.getInstance().getTestService();
		
		Assert.assertNotNull(service.getTestResponse());
	}
	
}
