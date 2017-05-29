package de.cooperateproject.cdo.dawn.rest;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/diagram")
@Produces(MediaType.APPLICATION_JSON)
public class DiagramService {

	@GET
	public DiagramExchangeObject getSampleObject() {
		
		DiagramExchangeObject sample = new DiagramExchangeObject();
		sample.setValue("Hello World!");
		sample.setX(312);
		sample.setY(42);
		
		DiagramExchangeObject child = new DiagramExchangeObject();
		child.setValue("Hello again!");
		child.setX(400);
		child.setY(12);
		
		sample.appendChild(child);
		
		return sample;
	}
	
}
