package de.cooperateproject.cdo.dawn.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import de.cooperateproject.cdo.dawn.dto.Model;
import de.cooperateproject.cdo.dawn.dto.Project;

@Path("/browse")
public interface BrowseService {

	@GET
	public List<Project> getProjects();

	@GET
	@Path("/{projectid}")
	public Project getProject(@PathParam("projectid") String projectId);
	
	@GET
	@Path("/{projectid}/models")
	public List<Model> getModels(@PathParam("projectid") String projectId);

	@GET
	@Path("/{projectid}/models/{modelid}")
	public Model getModel(@PathParam("projectid") String projectId, @PathParam("modelid") String modelId);

}
