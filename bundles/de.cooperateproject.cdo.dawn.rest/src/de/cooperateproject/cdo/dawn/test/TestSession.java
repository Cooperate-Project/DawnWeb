package de.cooperateproject.cdo.dawn.test;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.papyrus.infra.viewpoints.style.StylePackage;
import org.eclipse.uml2.uml.UMLPackage;
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
		
		Net4jUtil.prepareContainer(IPluginContainer.INSTANCE);
	    TCPUtil.prepareContainer(IPluginContainer.INSTANCE);
		
	    NotationPackage.eINSTANCE.eClass();
        StylePackage.eINSTANCE.eClass();
        UMLPackage.eINSTANCE.eClass();
        
		Diagram diagram = service.getDiagram("folder1", "inner");
		Assert.assertNotNull(diagram);
	}
	
}
