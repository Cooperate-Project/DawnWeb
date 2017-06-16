/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import org.eclipse.emf.cdo.dawn.web.registry.DawnResourceRegistry;
import org.eclipse.emf.cdo.dawn.web.renderer.IDawnWebRenderer;
import org.eclipse.emf.cdo.dawn.web.util.DawnWebGMFUtil;
import org.eclipse.emf.cdo.dawn.web.view.util.DawnWebAccessibleUtil;
import org.eclipse.emf.cdo.dawn.web.view.util.DawnWebGraphUtil;
import org.eclipse.emf.cdo.dawn.web.view.util.DiagramExchangeObject;
import org.eclipse.emf.cdo.dawn.web.view.util.Graph;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Martin Fluegge
 */
public class DawnJavaScriptDraw2DRenderer implements IDawnWebRenderer
{
  protected HttpServletRequest request;

  protected HttpServletResponse response;

  protected ServletContext servletContext;

  /**
   * Renders the response for the client request.
   *
   * @param diagramResource
   *          The resource with the diagram.
   * @param projectPluginId
   *          The project plugin ID determines what to use for rendering.
   * @param request
   *          The incoming request.
   * @param response
   *          The outgoing response to include the rendered content.
   * @param servletContext
   *          The servlet context.
   */
  public void render(Resource diagramResource, String projectPluginId, HttpServletRequest request,
      HttpServletResponse response, ServletContext servletContext)
  {
    this.request = request;
    this.response = response;
    this.servletContext = servletContext;

    response.setContentType("text/html");
    try
    {
      response.getWriter().write(render(diagramResource, projectPluginId));
    }
    catch (IOException ex)
    {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    response.setStatus(HttpServletResponse.SC_OK);
  }

  /**
   * Assembles the response content.
   *
   * @param resource
   *          The resource with the diagram
   * @param projectPluginId
   *          The project plugin ID determines what to use for rendering.
   * @return The response as String.
   */
  public String render(Resource resource, String projectPluginId)
  {

    // !! DONE (Diagram)
    Optional<Diagram> diagramOptional = DawnWebGMFUtil.getDiagramFromResource(resource);
    if (!diagramOptional.isPresent())
    {
      return "";
    }
    Diagram diagram = diagramOptional.get();

    // Buffer for JavaScript scripts
    // Those buffers are handed over to Xtend:
    // JSScripts are in the <head>-Area, JSRenderScripts are included after the HTML content.
    ArrayList<String> JSScripts = new ArrayList<String>();
    ArrayList<String> JSRenderScripts = new ArrayList<String>();

    // !! NOT NEEDED
    addAllJavaScript(JSScripts, projectPluginId);

    // !! DONE (URI & timestamp)
    JSRenderScripts.add(renderGlobalVars(resource, request.getSession().getId()));

    // The syntax hierarchy
    // !! DONE
    DiagramExchangeObject syntaxHierarchy = DawnWebAccessibleUtil.toSyntaxHierarchy(diagram, null);

    // The clusters for the clusters view
    // !! DONE
    Collection<DiagramExchangeObject> clusters = renderClusters(diagram);

    // Set some variables for the JS
    Map<String, String> JSVariables = getJavaScriptVariables(diagram);

    return DawnAccessibleRenderer.renderPage(JSScripts, JSRenderScripts, syntaxHierarchy, clusters, JSVariables);
  }

  /**
   * Adds all JavaScript scripts.
   *
   * @param buffer
   *          The buffer to add the lines to.
   * @param projectPluginId
   *          The project plugin ID, needed for more specific includes.
   */
  private void addAllJavaScript(ArrayList<String> buffer, String projectPluginId)
  {
    // External JS
    buffer.add("https://code.jquery.com/jquery-3.1.1.min.js");
    buffer.add("https://code.jquery.com/ui/1.12.1/jquery-ui.min.js");

    // Custom JS files
    buffer.add("renderer/draw2d/javascript/accessibleDawnWebEditorUtility.js");
    buffer.add("renderer/draw2d/javascript/dawnDiagramLib.js");
    buffer.add("renderer/draw2d/javascript/treeviewJs.js");
  }

  /**
   * Retrieves the EStructuralFeature with the given name.
   *
   * @param element
   *          An EObject to retrieve the EStructuralFeature from.
   * @param featureName
   *          The feature name.
   * @return Optional EStructuralFeature, if found.
   */
  private Optional<EStructuralFeature> getFeatureFromName(EObject element, String featureName)
  {
    for (EStructuralFeature f : element.eClass().getEAllStructuralFeatures())
    {
      if (f.getName().equals(featureName))
      {
        return Optional.of(f);
      }
    }
    return Optional.empty();
  }

  /**
   * Extracts IDs of EStructuralFeatures defined by their name and converts them to be added to the Map handed to Xtend.
   * Currently, only the "name" attribute is added.
   *
   * @param diagram
   *          The diagram to extract from.
   * @return A list of key-value pairs to be handed to Xtend.
   */
  private Map<String, String> getFeatureIdsForJavaScript(Diagram diagram)
  {
    Map<String, String> result = new HashMap<String, String>();

    for (Object o : diagram.getChildren())
    {
      if (o instanceof Node)
      {
        Optional<EStructuralFeature> nodeNameFeatureOptional = getFeatureFromName(((Node)o).getElement(), "name");
        if (nodeNameFeatureOptional.isPresent())
        {
          result.put("nameFeatureId", String.valueOf(nodeNameFeatureOptional.get().getFeatureID()));
        }
        break;
      }
    }

    // More feature IDs can be added here

    return result;
  }

  /**
   * Generates/retrieves all variables needed for JavaScript.
   *
   * @param diagram
   *          The current diagram, needed to retrieve feature IDs.
   */
  private Map<String, String> getJavaScriptVariables(Diagram diagram)
  {
    Map<String, String> JSVariables = getFeatureIdsForJavaScript(diagram);

    // Add settings variables for key logging
    putParameter(JSVariables, "code");
    putParameter(JSVariables, "log");

    return JSVariables;
  }

  /**
   * Searches the HTTP request for the GET parameter with the given name and converts it to a key value pair to be
   * handed to Xtend.
   *
   * @param map
   *          The Map containing the key value pairs that is handed over to Xtend.
   * @param parameterName
   *          The name of the GET parameter to be included.
   */
  private void putParameter(Map<String, String> map, String parameterName)
  {
    String value = request.getParameter(parameterName);
    if (value != null && value.matches("[0-9a-zA-z]*"))
    {
      map.put(parameterName, value);
    }
  }

  /**
   * Initialize the global variables from the dawn JS include.
   *
   * @param resource
   * @param httpSessionId
   * @return JavaScript as String
   */
  private String renderGlobalVars(Resource resource, String httpSessionId)
  {

    URI uri = resource.getURI();
    long lastChanged = DawnResourceRegistry.instance.getLastChanged(uri, httpSessionId);

    return String.format("DawnWebUtil.init('%s',%d);\n", uri, lastChanged);
  }

  /**
   * Clusters and converts a given diagram.
   *
   * @param diagram
   *          The diagram to be processed.
   * @return A collection of DiagramExchangeObjects containing the clusters.
   */
  private Collection<DiagramExchangeObject> renderClusters(Diagram diagram)
  {
    Graph graph = DawnWebGraphUtil.convertToGraph(diagram);
    ArrayList<DiagramExchangeObject> clusters = new ArrayList<DiagramExchangeObject>();

    // Cluster the diagram as a graph
    ArrayList<Graph> clustersAsGraphs = DawnWebGraphUtil.clusterGraph(graph);

    // Convert each cluster to a DiagramExchangeObject
    for (Graph g : clustersAsGraphs)
    {
      clusters.add(DawnWebAccessibleUtil.toSyntaxHierarchy(diagram, g));
    }

    // Set unique IDs and names for the clusters
    int clusterCounter = 1;
    for (DiagramExchangeObject c : clusters)
    {
      c.setValue("Cluster " + clusterCounter);
      ++clusterCounter;
    }

    return clusters;
  }
}
