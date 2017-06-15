package de.cooperateproject.cdo.dawn.rest.accessible.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import de.cooperateproject.cdo.dawn.rest.accessible.dto.DiagramExchangeObject;

@Path("/accessible")
public interface AccessibleService {

	@GET
	Boolean validateDiagram(@QueryParam("projectId") String projectId, @QueryParam("modelId") String modelId);

	@GET
	@Path("/hierachy")
	DiagramExchangeObject getSyntaxHierachy(@QueryParam("projectId") String projectId,
			@QueryParam("modelId") String modelId);

}
