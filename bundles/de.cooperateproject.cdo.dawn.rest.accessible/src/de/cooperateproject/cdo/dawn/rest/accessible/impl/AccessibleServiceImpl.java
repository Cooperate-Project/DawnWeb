package de.cooperateproject.cdo.dawn.rest.accessible.impl;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.gmf.runtime.notation.Diagram;

import de.cooperateproject.cdo.dawn.rest.accessible.api.AccessibleService;
import de.cooperateproject.cdo.dawn.rest.accessible.dto.IDiagramExchangeObject;
import de.cooperateproject.cdo.dawn.rest.accessible.util.DawnWebAccessibleUtil;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces(MediaType.APPLICATION_JSON)
@Path("/accessible")
@Api(value = "/accessible")
public class AccessibleServiceImpl implements AccessibleService {

	private Diagram getDiagram(String projectId, String modelId) {
		DiagramService diagramService = ServiceFactory.getInstance().getDiagramService();
		return diagramService.getDiagram(projectId, modelId);
	}

	@Override
	@GET
	@Path("/hierarchy/{projectId}/{modelId}")
	@ApiOperation(value = "Calculates the diagram syntax hierarchy", response = IDiagramExchangeObject.class)
	public IDiagramExchangeObject getSyntaxHierarchy(@PathParam("projectId") String projectId,
			@PathParam("modelId") String modelId) {
		return DawnWebAccessibleUtil.toSyntaxHierarchy(getDiagram(projectId, modelId), null);
	}

	@Override
	@GET
	@Path("/cluster/{projectId}/{modelId}")
	@ApiOperation(value = "Renders clusters of a given diagram", response = IDiagramExchangeObject.class, responseContainer = "List")
	public Collection<IDiagramExchangeObject> getClusters(@PathParam("projectId") String projectId,
			@PathParam("modelId") String modelId) {
		return DawnWebAccessibleUtil.renderClusters(getDiagram(projectId, modelId));
	}

	@Override
	@GET
	@Path("/feature/{projectId}/{modelId}")
	@ApiOperation(value = "Calculates the feature ids for a given diagram", response = String.class, responseContainer = "Map")
	public Map<String, String> getFeatureIdMap(@PathParam("projectId") String projectId,
			@PathParam("modelId") String modelId) {
		return DawnWebAccessibleUtil.getFeatureIdsForJavaScript(getDiagram(projectId, modelId));
	}

	@Override
	@GET
	@Path("/validate/{projectId}/{modelId}")
	@ApiOperation(value = "Returns true if the diagrams does actually exist", response = Boolean.class)
	public Boolean validateDiagram(@PathParam("projectId") String projectId, @PathParam("modelId") String modelId) {
		return (getDiagram(projectId, modelId) != null);
	}

}
