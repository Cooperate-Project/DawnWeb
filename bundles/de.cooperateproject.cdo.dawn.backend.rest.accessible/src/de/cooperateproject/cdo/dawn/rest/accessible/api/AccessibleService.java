package de.cooperateproject.cdo.dawn.rest.accessible.api;

import java.util.Collection;
import java.util.Map;

import de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject;

/**
 * A accessible service is used to get preprocessed model information for
 * accessible diagram editors.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public interface AccessibleService {

	/**
	 * Returns the accessible syntax hierarchy of a diagram.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @return a DTO containing the hierarchy information
	 */
	IDiagramExchangeObject getSyntaxHierarchy(String projectId, String modelId);

	/**
	 * Returns the clusters (for accessible cluster view) of a diagram.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of existing model
	 * @return a collection of DTOs, containing cluster information
	 */
	Collection<IDiagramExchangeObject> getClusters(String projectId, String modelId);

	/**
	 * Returns all feature id mappings for a diagram
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @return a mpa of feature names and their ids
	 */
	Map<String, String> getFeatureIdMap(String projectId, String modelId);

	/**
	 * Tests if a model exists. Only maintained for legacy code.
	 * 
	 * @param projectId
	 *            the projectId (project name). Project may not exist
	 * @param modelId
	 *            the modelId (model name). Model may not exist
	 * @return true, if project and model exist
	 */
	@Deprecated
	Boolean validateDiagram(String projectId, String modelId);

}
