package de.cooperateproject.cdo.dawn.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/util")
public interface UtilService {

	@GET
	@Path("/timestamp")
	long getCurrentServerTimestamp();
	
}
