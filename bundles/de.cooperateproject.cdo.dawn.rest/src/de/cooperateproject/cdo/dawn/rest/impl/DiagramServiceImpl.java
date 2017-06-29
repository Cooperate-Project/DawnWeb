package de.cooperateproject.cdo.dawn.rest.impl;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;

import de.cooperateproject.cdo.dawn.dto.Model;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.session.CDOConnectionManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces(MediaType.APPLICATION_JSON)
@Path("/diagram")
@Api(value = "/diagram")
public class DiagramServiceImpl implements DiagramService {

	private Optional<CDOResource> getResource(@QueryParam("projectId") String projectId,
			@QueryParam("modelId") String modelId) {

		Optional<CDOResource> returnValue = Optional.empty();

		// Get session
		CDOConnectionManager connectionManager = CDOConnectionManager.INSTANCE;
		CDOSession session = connectionManager.acquireSession();

		// Get content
		CDOView view = session.openView();
		String path = String.format("%s/%s.%s", projectId, modelId, Model.MODEL_FILE_EXTENSION);

		if (view.hasResource(path)) {
			returnValue = Optional.of(view.getResource(path));
		}

		return returnValue;
	}

	@Override
	@GET
	@ApiOperation(value = "Get diagram", response = Diagram.class)
	public Diagram getDiagram(@QueryParam("projectId") String projectId, @QueryParam("modelId") String modelId) {

		Optional<CDOResource> resource = getResource(projectId, modelId);

		if (resource.isPresent()) {

			// Get diagram
			// FIXME: Does only support first diagram
			for (EObject eObject : resource.get().getContents()) {
				if (eObject instanceof Diagram) {

					Diagram diagram = (Diagram) eObject;

					return diagram;
				}
			}
		}
		return null;
	}

	@Override
	@GET
	@Path("/uri")
	@ApiOperation(value = "Get diagram path", response = String.class)
	public String getPath(@QueryParam("projectId") String projectId, @QueryParam("modelId") String modelId) {
		Optional<CDOResource> resource = getResource(projectId, modelId);

		if (resource.isPresent()) {
			return resource.get().getURI().path();
		} else {
			return null;
		}
	}

}
