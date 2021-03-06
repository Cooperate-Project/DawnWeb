package de.cooperateproject.cdo.dawn.rest.impl;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import de.cooperateproject.cdo.dawn.rest.api.UtilService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * This implementation of the util service can be used as a rest service.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
@Path("/util")
@Api(value = "/util")
public class UtilServiceImpl implements UtilService {

	@Override
	@GET
	@Path("/timestamp")
	@ApiOperation(value = "Get the current Server timestamp")
	public long getCurrentServerTimestamp() {
		return new Date().getTime();
	}

}
