package de.cooperateproject.cdo.dawn.test;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.junit.Assert;
import org.junit.Test;

import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.api.TestService;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;

public class TestSession {

	@Test
	public void testService() {
		TestService service = ServiceFactory.getInstance().getTestService();
		
		Assert.assertNotNull(service.getTestResponse());
	}
	
	@Test
	public void testDiagram() {
		DiagramService service = ServiceFactory.getInstance().getDiagramService();
		
		Diagram diagram = service.getDiagram("folder1", "inner");
		Assert.assertNotNull(diagram);
	}
	
}
