package de.cooperateproject.cdo.dawn.rest.api;

import java.util.List;

import de.cooperateproject.cdo.dawn.dto.Model;
import de.cooperateproject.cdo.dawn.dto.Project;

/**
 * The BrowseService is used to retrieve projects and models located on the
 * connected server.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public interface BrowseService {

	/**
	 * Lists all projects on the connected server.
	 * 
	 * @return a list of project DTOs
	 */
	public List<Project> getProjects();

	/**
	 * Returns a specific project of the connected server.
	 * 
	 * @param projectId
	 *            the projectId (project name) of the desired project
	 * @return the project DTO or null
	 */
	public Project getProject(String projectId);

	/**
	 * Lists all models of a project.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @return a list of model DTOs or null
	 */
	public List<Model> getModels(String projectId);

	/**
	 * Returns a specific model of a project.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @return a model DTO or null
	 */
	public Model getModel(String projectId, String modelId);

}
