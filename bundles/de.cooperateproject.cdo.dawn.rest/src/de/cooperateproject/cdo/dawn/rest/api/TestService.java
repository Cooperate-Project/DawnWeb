package de.cooperateproject.cdo.dawn.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public interface TestService {

	@GET
	public String getTestResponse();
	
}
