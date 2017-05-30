package de.cooperateproject.cdo.dawn.rest;

import javax.ws.rs.Path;

@Path("/test")
public class ResourceTestImpl implements IResourceTest {

	@Override
	public String sayHello() {
		return "Hello World!";
	}

}
