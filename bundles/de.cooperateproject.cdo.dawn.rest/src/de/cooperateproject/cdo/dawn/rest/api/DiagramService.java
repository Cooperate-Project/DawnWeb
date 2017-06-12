package de.cooperateproject.cdo.dawn.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.eclipse.gmf.runtime.notation.Diagram;

@Path("/diagram")
public interface DiagramService {

	@GET
	Diagram getDiagram(@QueryParam("projectId") String projectId, @QueryParam("modelId") String modelId);

}
