package de.cooperateproject.cdo.dawn.rest.impl;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;

import de.cooperateproject.cdo.dawn.dto.Model;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.util.DiagramUtil;
import de.cooperateproject.cdo.dawn.session.CDOConnectionManager;
import de.cooperateproject.cdo.dawn.session.DawnServerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/diagram")
@Api(value = "/diagram")
@Produces(MediaType.APPLICATION_JSON)
public class DiagramServiceImpl implements DiagramService {

	@Override
	@GET
	@Path("/{projectId}/{modelId}")
	@ApiOperation(value = "Get diagram", response = Diagram.class)
	public Diagram getDiagram(@PathParam("projectId") String projectId, @PathParam("modelId") String modelId) {

		Optional<CDOResource> resource = DiagramUtil.getResource(projectId, modelId);

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
	@Path("/uri/{projectId}/{modelId}")
	@ApiOperation(value = "Get diagram path", response = String.class)
	public String getAbsolutePath(@PathParam("projectId") String projectId, @PathParam("modelId") String modelId) {
		return "\"" + DiagramUtil.getRessourceURI(projectId, modelId).toString() + "\"";
	}

	@Override
	public boolean deleteView(String projectId, String modelId, String uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeFeature(String projectId, String modelId, String uuid, String featureId, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addClass(String projectId, String modelId, String className, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getLastChanged(String projectId, String modelId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
