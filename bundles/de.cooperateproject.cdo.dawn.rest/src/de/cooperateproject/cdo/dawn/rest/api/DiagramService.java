package de.cooperateproject.cdo.dawn.rest.api;

import org.eclipse.gmf.runtime.notation.Diagram;

/**
 * A diagram service is used to get diagram and edit diagrams.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public interface DiagramService {

	/**
	 * Returns a diagram from the provided project and model information.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @return the first available diagram or null
	 */
	Diagram getDiagram(String projectId, String modelId);

	/**
	 * Returns the absolute path to a model located at the connected server. the
	 * model must exist for the path creation.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @return a string, representing the file path of the model
	 */
	String getAbsolutePath(String projectId, String modelId);

	/**
	 * Delets a view / node with the given uuid.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @param uuid
	 *            the uuid provided from the cdo utility
	 * @return true, if the deletion was successful
	 */
	boolean deleteView(String projectId, String modelId, String uuid);

	/**
	 * Changes a property / feature of a diagram element.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @param uuid
	 *            the uuid provided from the cdo utility
	 * @param featureId
	 *            the id of the feature, that should be changed
	 * @param value
	 *            the new value of the feature
	 * @return true, if the change was successful
	 */
	boolean changeFeature(String projectId, String modelId, String uuid, String featureId, String value);

	/**
	 * Adds a new class to the diagram of the model
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @param className
	 *            the new name of the created class
	 * @param x
	 *            the x-position of the new class
	 * @param y
	 *            the y-position of the new class
	 * @return true, if the creation was successful
	 */
	boolean addClass(String projectId, String modelId, String className, int x, int y);

	/**
	 * Returns the time stamp of the last change of a diagram
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @return a time stamp, formatted as long
	 */
	long getLastChanged(String projectId, String modelId);

}
