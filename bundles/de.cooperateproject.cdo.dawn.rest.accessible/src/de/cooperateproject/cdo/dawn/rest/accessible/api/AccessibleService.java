package de.cooperateproject.cdo.dawn.rest.accessible.api;

import java.util.Collection;
import java.util.Map;

import de.cooperateproject.cdo.dawn.rest.accessible.dto.DiagramExchangeObject;

public interface AccessibleService {

	Boolean validateDiagram(String projectId, String modelId);

	DiagramExchangeObject getSyntaxHierarchy(String projectId, String modelId);

	Collection<DiagramExchangeObject> getClusters(String projectId, String modelId);

	Map<String, String> getFeatureIdMap(String projectId, String modelId);

}
