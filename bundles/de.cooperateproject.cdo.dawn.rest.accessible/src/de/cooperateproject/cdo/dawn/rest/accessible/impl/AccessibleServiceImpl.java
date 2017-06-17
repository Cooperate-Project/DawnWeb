package de.cooperateproject.cdo.dawn.rest.accessible.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.gmf.runtime.notation.Diagram;

import de.cooperateproject.cdo.dawn.rest.accessible.api.AccessibleService;
import de.cooperateproject.cdo.dawn.rest.accessible.dto.DiagramExchangeObject;
import de.cooperateproject.cdo.dawn.rest.accessible.util.DawnWebAccessibleUtil;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;

public class AccessibleServiceImpl implements AccessibleService {

	private Diagram getDiagram(String projectId, String modelId) {
		DiagramService diagramService = ServiceFactory.getInstance().getDiagramService();
		return diagramService.getDiagram(projectId, modelId);
	}

	@Override
	public Boolean validateDiagram(String projectId, String modelId) {
		return (getDiagram(projectId, modelId) != null);
	}

	@Override
	public DiagramExchangeObject getSyntaxHierarchy(String projectId, String modelId) {
		return DawnWebAccessibleUtil.toSyntaxHierarchy(getDiagram(projectId, modelId), null);
	}

	@Override
	public Collection<DiagramExchangeObject> getClusters(String projectId, String modelId) {
		return DawnWebAccessibleUtil.renderClusters(getDiagram(projectId, modelId));
	}

	@Override
	public Map<String, String> getFeatureIdMap(String projectId, String modelId) {
		return DawnWebAccessibleUtil.getFeatureIdsForJavaScript(getDiagram(projectId, modelId));
	}

}
