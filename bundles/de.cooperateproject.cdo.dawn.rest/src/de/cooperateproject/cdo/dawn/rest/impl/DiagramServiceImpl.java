package de.cooperateproject.cdo.dawn.rest.impl;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;

import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.util.DiagramUtil;
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
	@GET
	@Path("/deleteView/{projectId}/{modelId}/{uuid}")
	@ApiOperation(value = "Deletes a node", response = Boolean.class)
	public boolean deleteView(@PathParam("projectId") String projectId, @PathParam("modelId") String modelId,
			@PathParam("uuid") String uuid) {
		return DiagramUtil.deleteNode(DiagramUtil.getRessourceURI(projectId, modelId), uuid);
	}

	@Override
	@GET
	@Path("/changeFeature/{projectId}/{modelId}/{uuid}/{featureId}/{value}")
	@ApiOperation(value = "Changes the value of a feature", response = Boolean.class)
	public boolean changeFeature(@PathParam("projectId") String projectId, @PathParam("modelId") String modelId,
			@PathParam("uuid") String uuid, @PathParam("featureId") String featureId,
			@PathParam("value") String value) {
		return DiagramUtil.changeFeature(DiagramUtil.getRessourceURI(projectId, modelId), uuid,
				Integer.parseInt(featureId), value);
	}

	@Override
	@GET
	@Path("/addClass/{projectId}/{modelId}/{className}/{x}/{y}")
	@ApiOperation(value = "Adds a new class", response = Boolean.class)
	public boolean addClass(@PathParam("projectId") String projectId, @PathParam("modelId") String modelId,
			@PathParam("className") String className, @PathParam("x") int x, @PathParam("y") int y) {
		return DiagramUtil.addClass(DiagramUtil.getRessourceURI(projectId, modelId), className, x, y);
	}

	@Override
	@GET
	@Path("/getLastChanged/{projectId}/{modelId}")
	@ApiOperation(value = "Gets the timestamp of the last change", response = Long.class)
	public long getLastChanged(@PathParam("projectId") String projectId, @PathParam("modelId") String modelId) {
		return DiagramUtil.getLastChanged(DiagramUtil.getRessourceURI(projectId, modelId));
	}

}
