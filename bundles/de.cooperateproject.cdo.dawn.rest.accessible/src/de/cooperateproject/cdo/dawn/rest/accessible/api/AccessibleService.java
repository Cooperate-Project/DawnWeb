package de.cooperateproject.cdo.dawn.rest.accessible.api;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import de.cooperateproject.cdo.dawn.rest.accessible.dto.DiagramExchangeObject;

@Path("/accessible")
public interface AccessibleService {

	@GET
	Boolean validateDiagram(@QueryParam("projectId") String projectId, @QueryParam("modelId") String modelId);

	@GET
	@Path("/hierarchy")
	DiagramExchangeObject getSyntaxHierarchy(@QueryParam("projectId") String projectId,
			@QueryParam("modelId") String modelId);

	@GET
	@Path("/cluster")
	Collection<DiagramExchangeObject> getClusters(@QueryParam("projectId") String projectId,
			@QueryParam("modelId") String modelId);

	@GET
	@Path("feature")
	Map<String, String> getFeatureIdMap(@QueryParam("projectId") String projectId,
			@QueryParam("modelId") String modelId);

}
