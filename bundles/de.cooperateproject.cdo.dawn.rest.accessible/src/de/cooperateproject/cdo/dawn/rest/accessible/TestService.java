package de.cooperateproject.cdo.dawn.rest.accessible;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.cooperateproject.cdo.dawn.dto.Project;
import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;

@Path("/accessible")
@Produces(MediaType.APPLICATION_JSON)
public class TestService {

	@GET
	public List<Project> getTestList() {
		
		// TODO: Remove testing code
		Project test = new Project();
		test.setName("JustATest");
		
		BrowseService service = ServiceFactory.getInstance().getBrowseService();
		
		List<Project> returnValue = service.getProjects();
		returnValue.add(test);
		
		return returnValue;
	}
	
}
