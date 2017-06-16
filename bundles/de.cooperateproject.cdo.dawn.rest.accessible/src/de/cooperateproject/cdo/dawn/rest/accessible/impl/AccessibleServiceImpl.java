package de.cooperateproject.cdo.dawn.rest.accessible.impl;

import org.eclipse.gmf.runtime.notation.Diagram;

import de.cooperateproject.cdo.dawn.rest.accessible.api.AccessibleService;
import de.cooperateproject.cdo.dawn.rest.accessible.dto.DiagramExchangeObject;
import de.cooperateproject.cdo.dawn.rest.accessible.util.DawnWebAccessibleUtil;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;

public class AccessibleServiceImpl implements AccessibleService {

	// FIXME: Proper dependencies

	@Override
	public Boolean validateDiagram(String projectId, String modelId) {

		DiagramService diagramService = ServiceFactory.getInstance().getDiagramService();
		Diagram diagram = diagramService.getDiagram(projectId, modelId);

		return (diagram != null);
	}

	@Override
	public DiagramExchangeObject getSyntaxHierarchy(String projectId, String modelId) {

		DiagramService diagramService = ServiceFactory.getInstance().getDiagramService();
		Diagram diagram = diagramService.getDiagram(projectId, modelId);

		return DawnWebAccessibleUtil.toSyntaxHierarchy(diagram, null);
	}

}
