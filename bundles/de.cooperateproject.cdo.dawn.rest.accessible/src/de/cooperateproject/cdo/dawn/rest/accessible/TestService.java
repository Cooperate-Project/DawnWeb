package de.cooperateproject.cdo.dawn.rest.accessible;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.cooperateproject.cdo.dawn.dto.Project;
import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.rest.impl.BrowseServiceImpl;

@Path("/accessible")
@Produces(MediaType.APPLICATION_JSON)
public class TestService {

	@GET
	public List<Project> getTestList() {
		
		Project test = new Project();
		test.setName("JustATest");
		
		// Get Project from Interface
		// FIXME: Using this like a singleton, defined in the main project?
		BrowseService service = new BrowseServiceImpl();
		
		List<Project> returnValue = service.getProjects();
		returnValue.add(test);
		
		return returnValue;
	}
	
}
