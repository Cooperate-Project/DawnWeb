package de.cooperateproject.cdo.dawn.rest.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.cooperateproject.cdo.dawn.rest.api.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * This implementation of the test service can be used as a rest service.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
@Produces(MediaType.TEXT_PLAIN)
@Path("/test")
@Api(value = "/test")
public class TestServiceImpl implements TestService {

	@Override
	@GET
	@ApiOperation(value = "Get a test answer", response = String.class)
	public String getTestResponse() {
		return "Received!";
	}

}
