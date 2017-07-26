package de.cooperateproject.cdo.dawn.rest.accessible.api;

import java.util.Collection;
import java.util.Map;

import de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject;

public interface AccessibleService {

	IDiagramExchangeObject getSyntaxHierarchy(String projectId, String modelId);

	Collection<IDiagramExchangeObject> getClusters(String projectId, String modelId);

	Map<String, String> getFeatureIdMap(String projectId, String modelId);

}
