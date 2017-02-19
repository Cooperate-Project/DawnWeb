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

import org.eclipse.emf.cdo.dawn.internal.web.DawnWebBundle;
import org.eclipse.emf.cdo.dawn.web.js.internal.draw2d.DawnJSDraw2dBundle;
import org.eclipse.emf.cdo.dawn.web.registry.DawnResourceRegistry;
import org.eclipse.emf.cdo.dawn.web.renderer.IDawnWebRenderer;
import org.eclipse.emf.cdo.dawn.web.util.DawnWebUtil;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.TypedElement;

import org.osgi.framework.Bundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Martin Fluegge
 */
public class DawnJavaScriptDraw2DRenderer implements IDawnWebRenderer
{
  public static final String BASIC_INCLUDES_SVG_NAME = "org.eclipse.emf.cdo.dawn.web.svg.basic";

  private static final String RENDERER_DRAW2D = "renderer/draw2d";

  private static final String JAVASCRIPT_FIGURES = "javascript/figures/";

  public static final String WEB_CONTENT_JAVASCRIPT_FIGURES = "/web_content/" + JAVASCRIPT_FIGURES;

  public static final String DAWN_JAVASCRIPT_FIGURES = "/" + RENDERER_DRAW2D + "/" + JAVASCRIPT_FIGURES;

  // START Parameters for clustering
  private static final int ASSOCIATION_WEIGHT = 1;

  private static final int GENERALIZATION_WEIGHT = 5;

  private static final int CLUSTER_SIZE_THRESHOLD = 7;
  // END Parameters for clustering

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
    Diagram diagram = getDiagramFromResource(resource);

    // Buffer for JavaScript scripts
    // Those buffers are handed over to Xtend:
    // JSScripts are in the <head>-Area, JSRenderScripts are included after the HTML content.
    ArrayList<String> JSScripts = new ArrayList<String>();
    ArrayList<String> JSRenderScripts = new ArrayList<String>();

    addAllJavaScript(JSScripts, projectPluginId);
    JSRenderScripts.add(renderGlobalVars(resource, request.getSession().getId()));

    // The syntax hierarchy
    DiagramExchangeObject syntaxHierarchy = toSyntaxHierarchy(diagram, null);

    // The clusters for the clusters view
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

    buffer.addAll(createBasicDawnIncludes());
    buffer.addAll(createProjectSpecificIncludes(projectPluginId));
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
   * Converts the diagram to an exchange format to be handed over to Xtend.
   *
   * @param diagram
   *          The diagram to be converted to a syntax hierarchy.
   * @param graph
   *          A graph containing a partial diagram to restrict the syntax hierarchy to a certain part. The graph can be
   *          <code>null</code> to include all available elements.
   * @return The syntax hierarchy as a DiagramExchangeObject.
   */
  private DiagramExchangeObject toSyntaxHierarchy(Diagram diagram, Graph graph)
  {
    // If graph is not null, get the IDs of the nodes to be included
    ArrayList<String> nodeIds = graph == null ? null : graph.getNodeIds();

    // Switches are necessary to read out EStructuralFeatures
    NamedSwitch nameSwitch = new NamedSwitch();
    TypedSwitch typeSwitch = new TypedSwitch();

    // Create fixed root structure
    DiagramExchangeObject result = new DiagramExchangeObject(getCdoId(diagram), diagram.getName());

    DiagramExchangeObject classes = new DiagramExchangeObject(null, "Classes");
    result.appendChild(classes);

    DiagramExchangeObject associations = new DiagramExchangeObject(null, "Associations");
    result.appendChild(associations);

    DiagramExchangeObject generalizations = new DiagramExchangeObject(null, "Generalizations");
    result.appendChild(generalizations);
    int generalizationsCounter = 0; // Used for naming as generalizations aren't named

    // Tracking of the outer bounds for center calculation (for clusters view)
    int maxX = 0;
    int minX = 0;
    int maxY = 0;
    int minY = 0;

    for (Object v : diagram.getChildren())
    {
      if (v instanceof Node)
      {
        Node node = (Node)v;
        String nodeId = getCdoId(node);

        if (nodeIds != null && !nodeIds.contains(nodeId))
        {
          // There is a graph set and this node is not part of the graph
          continue;
        }

        DiagramExchangeObject temp = new DiagramExchangeObject(nodeId, classes, nameSwitch.doSwitch(node.getElement()));
        temp.setMutable(true);
        temp.setRemovable(true);

        // Get the node position and add to tracking variables
        int localX = 0;
        int localY = 0;
        LayoutConstraint l = node.getLayoutConstraint();
        if (l instanceof Location)
        {
          localX = ((Location)l).getX();
          localY = ((Location)l).getY();
        }
        minX = Math.min(minX, localX);
        maxX = Math.max(maxX, localX);
        minY = Math.min(minY, localY);
        maxY = Math.max(maxY, localY);

        int compartmentCounter = 0;

        // Get more information from the compartments
        for (Object c : node.getChildren())
        {
          if (c instanceof BasicCompartment)
          {
            BasicCompartment compartment = (BasicCompartment)c;

            String compartmentName;
            switch (compartmentCounter)
            {
            case 0:
              compartmentName = "Properties";
              break;
            case 1:
              compartmentName = "Methods";
              break;
            case 2:
              compartmentName = "Enumerations and Primitives";
              break;
            default:
              compartmentName = "Other";
            }

            DiagramExchangeObject tempCompartment = new DiagramExchangeObject(getCdoId(compartment), temp,
                compartmentName);

            for (Object elem : compartment.getChildren())
            {
              if (elem instanceof Shape)
              {
                Shape s = (Shape)elem;

                DiagramExchangeObject entry = new DiagramExchangeObject(getCdoId(s), tempCompartment,
                    nameSwitch.doSwitch(s.getElement()));

                if (s.getElement() instanceof TypedElement)
                {
                  new DiagramExchangeObject(null, entry, typeSwitch.doSwitch(s.getElement()));
                }

              }
            }
            ++compartmentCounter;
          }
        }

        // Create compartments for the references to the links
        new DiagramExchangeObject(null, temp, "Outgoing Associations");
        new DiagramExchangeObject(null, temp, "Incoming Associations");
        new DiagramExchangeObject(null, temp, "Generalizations");
        new DiagramExchangeObject(null, temp, "Specializations");
      }
    }

    for (Object v : diagram.getEdges())
    {
      Edge edge = (Edge)v;
      String edgeId = getCdoId(edge);

      // Check if edge should be added
      if (nodeIds != null)
      {
        String sourceId = edge.getSource() != null ? getCdoId(edge.getSource()) : null;
        String targetId = edge.getTarget() != null ? getCdoId(edge.getTarget()) : null;

        if (!nodeIds.contains(sourceId) || !nodeIds.contains(targetId))
        {
          continue;
        }
      }

      // Associations
      if (edge.getElement() instanceof Association)
      {
        // This edge is an association
        String name = nameSwitch.doSwitch(edge.getElement());
        DiagramExchangeObject temp = new DiagramExchangeObject(edgeId, associations, name);
        temp.setMutable(true);
        temp.setRemovable(true);

        // Add ends to the association
        if (edge.getSource() != null)
        {
          DiagramExchangeObject sourceObj = classes.getChildById(getCdoId(edge.getSource()));

          // Append information to the association object
          new DiagramExchangeObject(edgeId + "Source", temp, "Source", sourceObj);

          // Append association to the class
          new DiagramExchangeObject(edgeId + "SourceReference", sourceObj.getChildByName("Outgoing Associations"), name,
              temp);
        }

        if (edge.getTarget() != null)
        {
          DiagramExchangeObject targetObj = classes.getChildById(getCdoId(edge.getTarget()));

          // Append information to the association object
          new DiagramExchangeObject(edgeId + "Target", temp, "Target", targetObj);

          // Append association to the class
          new DiagramExchangeObject(edgeId + "TargetReference", targetObj.getChildByName("Incoming Associations"), name,
              temp);
        }
      }

      // Generalizations
      if (edge.getElement() instanceof Generalization)
      {
        ++generalizationsCounter;
        String name = "InheritanceRelation " + generalizationsCounter;

        // This edge is an generalization
        DiagramExchangeObject temp = new DiagramExchangeObject(edgeId, generalizations, "Generalization");

        // Add ends to the generalization
        if (edge.getSource() != null)
        {
          DiagramExchangeObject sourceObj = classes.getChildById(getCdoId(edge.getSource()));

          // Append information to the association object
          new DiagramExchangeObject(edgeId + "Source", temp, "Source", sourceObj);

          // Append association to the class
          new DiagramExchangeObject(edgeId + "SourceReference", sourceObj.getChildByName("Generalizations"), name,
              temp);
        }

        if (edge.getTarget() != null)
        {
          DiagramExchangeObject targetObj = classes.getChildById(getCdoId(edge.getTarget()));

          // Append information to the association object
          new DiagramExchangeObject(edgeId + "Target", temp, "Target", targetObj);

          // Append association to the class
          new DiagramExchangeObject(edgeId + "TargetReference", targetObj.getChildByName("Specializations"), name,
              temp);
        }
      }
    }

    // Calculate position of the hierarchy (relevant for clusters)
    result.setX(minX + (maxX - minX) / 2);
    result.setY(minY + (maxY - minY) / 2);

    return result;
  }

  /**
   * Returns the CDO ID of an EObject.
   *
   * @param object
   *          The EObject to get the ID from.
   * @return The CDO ID of the EObject.
   */
  private String getCdoId(EObject object)
  {
    return DawnWebUtil.getUniqueId(object);
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
    Graph graph = convertToGraph(diagram);
    ArrayList<DiagramExchangeObject> clusters = new ArrayList<DiagramExchangeObject>();

    // Cluster the diagram as a graph
    ArrayList<Graph> clustersAsGraphs = clusterGraph(graph);

    // Convert each cluster to a DiagramExchangeObject
    for (Graph g : clustersAsGraphs)
    {
      clusters.add(toSyntaxHierarchy(diagram, g));
    }

    // Set unique IDs and names for the clusters
    int clusterCounter = 1;
    for (DiagramExchangeObject c : clusters)
    {
      c.setId(UUID.randomUUID().toString());
      c.setValue("Cluster " + clusterCounter);
      ++clusterCounter;
    }

    return clusters;
  }

  /**
   * Clusters a graph by dividing the given graph recursively into two parts until the cluster size threshold is
   * reached.
   *
   * @param graph
   *          The graph to cluster.
   * @return A list of Graphs (clusters).
   */
  private ArrayList<Graph> clusterGraph(Graph graph)
  {

    ArrayList<Graph> result = new ArrayList<Graph>();

    // Store a backup of all links in their original state
    ArrayList<Link> allLinks = new ArrayList<Link>();
    for (Link l : graph.getLinks())
    {
      allLinks.add(new Link(l));
    }

    // Split graph if larger than threshold
    if (graph.getSize() > CLUSTER_SIZE_THRESHOLD)
    {
      // Recursively contract the heaviest edge (highest closeness) until halved
      while (graph.getSize() > 2)
      {
        Link heaviestLink = graph.getHeaviestLink();
        if (heaviestLink != null)
        {
          graph.contractLink(heaviestLink);
        }
        else
        {
          // There is no edge left, but still more than two parts
          graph.mergeClosestNodes();
        }
      }

      // Cut into two separate graphs
      Graph partOne = new Graph();
      Graph partTwo = new Graph();

      assert graph.getNodes().size() == 2;

      addNodesToGraph(partOne, graph.getNodes().get(0));
      addNodesToGraph(partTwo, graph.getNodes().get(1));
      addInternalLink(partOne, allLinks);
      addInternalLink(partTwo, allLinks);

      // Recursion
      result.addAll(clusterGraph(partOne));
      result.addAll(clusterGraph(partTwo));
    }
    else
    {
      // Return the input graph if smaller than threshold, end of recursion
      result.add(graph);
    }

    return result;
  }

  /**
   * Adds all links from the original diagram to the given graph if applicable (both source and target are in the
   * graph).
   *
   * @param graph
   *          The graph to add the links to.
   * @param links
   *          A list of links from the original diagram.
   */
  private void addInternalLink(Graph graph, ArrayList<Link> links)
  {
    ArrayList<GraphNode> nodes = graph.getNodes();

    for (Link l : links)
    {
      if (nodes.contains(l.getSource()) && nodes.contains(l.getTarget()))
      {
        graph.addLink(l);
      }
    }
  }

  /**
   * Adds all given nodes to a graph.
   *
   * @param graph
   *          The graph to add the node(s) to.
   * @param node
   *          The node to be added to the graph. This can be a multi-node containing multiple separate nodes. All nodes
   *          will then be added.
   */
  private void addNodesToGraph(Graph graph, GraphNode node)
  {
    // Avoid duplicate nodes
    if (graph.getNodes().contains(node))
    {
      return;
    }

    if (node instanceof MultiNode)
    {
      MultiNode nodeCluster = (MultiNode)node;
      // Add nodes from the node cluster
      for (GraphNode n : nodeCluster.getNodes())
      {
        graph.addNode(n);
      }
    }
    else
    {
      graph.addNode(node);
    }
  }

  /**
   * Converts a diagram to a graph.
   *
   * @param diagram
   *          The diagram to convert into a graph.
   * @return The resulting graph.
   */
  private Graph convertToGraph(Diagram diagram)
  {
    ArrayList<Link> links = new ArrayList<Link>();
    ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();

    Graph resultGraph = new Graph(nodes, links);

    // Add the nodes and links
    for (Object o : diagram.getChildren())
    {
      if (o instanceof Node)
      {
        // Add a node
        Node node = (Node)o;

        // Get the node position
        int x = -1;
        int y = -1;
        LayoutConstraint l = node.getLayoutConstraint();
        if (l instanceof Location)
        {
          x = ((Location)l).getX();
          y = ((Location)l).getY();
        }
        nodes.add(new GraphNode(getCdoId(node), x, y));
      }
    }

    for (Object e : diagram.getEdges())
    {
      // Add an edge
      Edge edge = (Edge)e;

      if (edge.getElement() instanceof Association)
      {
        addEdgeInGraph(resultGraph, edge, ASSOCIATION_WEIGHT);
      }

      if (edge.getElement() instanceof Generalization)
      {
        addEdgeInGraph(resultGraph, edge, GENERALIZATION_WEIGHT);
      }
    }

    return resultGraph;
  }

  /**
   * Adds an edge from a diagram to a graph with a specified weight.
   *
   * @param graph
   *          The graph to add the edge to.
   * @param edge
   *          The edge to be added.
   * @param weight
   *          The weight of the edge.
   */
  private void addEdgeInGraph(Graph graph, Edge edge, int weight)
  {
    GraphNode sourceNode = graph.getNodeById(getCdoId(edge.getSource()));
    GraphNode targetNode = graph.getNodeById(getCdoId(edge.getTarget()));

    Link link = new Link(sourceNode, targetNode, weight);

    graph.addLink(link);

    sourceNode.addLink(link);
    targetNode.addLink(link);
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
   * Adds includes.
   *
   * @param pluginId
   *          The plugin ID determining what to include.
   * @return A list of lines to add to the response.
   */
  public ArrayList<String> createProjectSpecificIncludes(String pluginId)
  {
    return addJSLibsFromPath(WEB_CONTENT_JAVASCRIPT_FIGURES + pluginId);
  }

  /**
   * Adds includes.
   *
   * @return A list of lines to add to the response.
   */
  protected ArrayList<String> createBasicDawnIncludes()
  {
    ArrayList<String> resultBuffer = new ArrayList<String>();
    resultBuffer.add(DAWN_JAVASCRIPT_FIGURES + "package.js");

    String path = WEB_CONTENT_JAVASCRIPT_FIGURES + BASIC_INCLUDES_SVG_NAME;

    resultBuffer.addAll(addJSLibsFromPath(path));

    return resultBuffer;
  }

  /**
   * Adds all files from a given path.
   *
   * @param path
   *          The path to retrieve the files.
   * @return A list of file
   */
  private ArrayList<String> addJSLibsFromPath(String path)
  {
    ArrayList<String> result = new ArrayList<String>();

    Bundle bundle = DawnJSDraw2dBundle.getBundleContext().getBundle();
    Enumeration<?> entries = bundle.findEntries(path, "*.js", true);

    while (entries.hasMoreElements())
    {
      URL url = (URL)entries.nextElement();
      String libraryPath = url.getPath();

      if (libraryPath.endsWith(".js") && !libraryPath.contains("package.js"))
      {
        result.add(libraryPath.replace(DawnWebBundle.WEB_CONTENT, RENDERER_DRAW2D));
      }
    }

    return result;
  }

  /**
   * Retrieves the diagram from a given resource.
   *
   * @param res
   *          The resource to retrieve from.
   * @return The retrieved diagram or <code>null</code> if no diagram has been found.
   */
  protected Diagram getDiagramFromResource(Resource res)
  {
    for (Object o : res.getContents())
    {
      if (o instanceof Diagram)
      {
        return (Diagram)o;
      }
    }
    return null;
  }
}
