package de.cooperateproject.cdo.dawn.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public interface IResourceTest {

	@GET
	public String sayHello();
	
}
