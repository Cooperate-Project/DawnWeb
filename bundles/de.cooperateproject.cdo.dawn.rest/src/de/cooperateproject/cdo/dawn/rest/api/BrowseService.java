package de.cooperateproject.cdo.dawn.rest.api;

import java.util.List;

import de.cooperateproject.cdo.dawn.dto.Model;
import de.cooperateproject.cdo.dawn.dto.Project;

public interface BrowseService {

	public List<Project> getProjects();

	public Project getProject(String projectId);

	public List<Model> getModels(String projectId);

	public Model getModel(String projectId, String modelId);

}
