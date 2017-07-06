package de.cooperateproject.cdo.dawn.rest.accessible.impl;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.gmf.runtime.notation.Diagram;

import de.cooperateproject.cdo.dawn.rest.accessible.api.AccessibleService;
import de.cooperateproject.cdo.dawn.rest.accessible.dto.DiagramExchangeObject;
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
	@ApiOperation(value = "Check Diagram existence", response = Boolean.class)
	public Boolean validateDiagram(@QueryParam("projectId") String projectId, @QueryParam("modelId") String modelId) {
		return (getDiagram(projectId, modelId) != null);
	}

	@Override
	@GET
	@Path("/hierarchy")
	@ApiOperation(value = "Calculates the diagram syntax hierarchy", response = IDiagramExchangeObject.class)
	public IDiagramExchangeObject getSyntaxHierarchy(@QueryParam("projectId") String projectId,
			@QueryParam("modelId") String modelId) {
		return DawnWebAccessibleUtil.toSyntaxHierarchy(getDiagram(projectId, modelId), null);
	}

	@Override
	@GET
	@Path("/cluster")
	@ApiOperation(value = "Renders clusters of a given diagram", response = IDiagramExchangeObject.class, responseContainer = "List")
	public Collection<IDiagramExchangeObject> getClusters(@QueryParam("projectId") String projectId,
			@QueryParam("modelId") String modelId) {
		return DawnWebAccessibleUtil.renderClusters(getDiagram(projectId, modelId));
	}

	@Override
	@GET
	@Path("feature")
	@ApiOperation(value = "Calculates the feature ids for a given diagram", response = String.class, responseContainer = "Map")
	public Map<String, String> getFeatureIdMap(@QueryParam("projectId") String projectId,
			@QueryParam("modelId") String modelId) {
		return DawnWebAccessibleUtil.getFeatureIdsForJavaScript(getDiagram(projectId, modelId));
	}

}
