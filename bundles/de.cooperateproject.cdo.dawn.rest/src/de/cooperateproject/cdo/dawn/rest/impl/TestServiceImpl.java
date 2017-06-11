package de.cooperateproject.cdo.dawn.rest.impl;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.cooperateproject.cdo.dawn.rest.api.TestService;

@Produces(MediaType.TEXT_PLAIN)
public class TestServiceImpl implements TestService {

	@Override
	public String getTestResponse() {
		return "Received!";
	}

}
