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
import org.eclipse.emf.cdo.dawn.web.util.FigureMapping;
import org.eclipse.emf.cdo.dawn.web.util.FigureMappingParser;
import org.eclipse.emf.cdo.dawn.web.util.VarNameConverter;
import org.eclipse.emf.cdo.dawn.web.util.ViewAttribute;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.Bendpoints;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.gmf.runtime.notation.impl.BoundsImpl;
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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Martin Fluegge
 */
public class DawnJavaScriptDraw2DRenderer implements IDawnWebRenderer
{

  public static final String BASIC_INCLUDES_NAME = "org.eclipse.emf.cdo.dawn.web.basic";

  public static final String BASIC_INCLUDES_SVG_NAME = "org.eclipse.emf.cdo.dawn.web.svg.basic";

  private static final String RENDERER_DRAW2D = "renderer/draw2d/";

  private static final String JAVASCRIPT_FIGURES = "javascript/figures/";

  public static final String WEB_CONTENT_JAVASCRIPT_FIGURES = "/web_content/" + JAVASCRIPT_FIGURES;

  public static final String DAWN_JAVASCRIPT_FIGURES = "/" + RENDERER_DRAW2D + JAVASCRIPT_FIGURES;

  private static final int ASSOCIATION_WEIGHT = 1;

  private static final int GENERALIZATION_WEIGHT = 3;

  private static final int CLUSTER_SIZE_THRESHOLD = 7;

  protected HttpServletRequest request;

  protected HttpServletResponse response;

  protected ServletContext servletContext;

  public void render(Resource diagramResource, String projectPluginId, HttpServletRequest request,
      HttpServletResponse response, ServletContext servletContext)
  {
    this.request = request;
    this.response = response;
    this.servletContext = servletContext;

    response.setContentType("text/html");
    Map<String, FigureMapping> vidualIdToFigure = getFigureMapping(projectPluginId);
    try
    {
      response.getWriter().write(render(diagramResource, projectPluginId, vidualIdToFigure));
    }
    catch (IOException ex)
    {
      throw new RuntimeException(ex);
    }
  }

  public String render(Resource resource, String projectPluginId, Map<String, FigureMapping> vidualIdToFigure)
  {
    Diagram diagram = getDiagramFromResource(resource);

    // Buffer for JavaScript scripts
    ArrayList<String> JSScripts = new ArrayList<String>();
    ArrayList<String> JSRenderScripts = new ArrayList<String>();

    JSScripts.add("https://code.jquery.com/jquery-3.1.1.min.js");
    JSScripts.add("https://code.jquery.com/ui/1.12.1/jquery-ui.min.js");

    // touch
    JSScripts.add("draw2d/with_namespace/dist/jquery.autoresize.js");
    JSScripts.add("draw2d/with_namespace/dist/jquery-touch_punch.js");
    JSScripts.add("draw2d/with_namespace/dist/jquery.contextmenu.js");

    // shifty.js
    JSScripts.add("draw2d/with_namespace/dist/shifty.js");

    // raphael.js
    JSScripts.add("draw2d/with_namespace/dist/patched_raphael.js");
    JSScripts.add("draw2d/with_namespace/dist/rgbcolor.js");
    JSScripts.add("draw2d/with_namespace/dist/patched_canvg.js");

    // class.js
    JSScripts.add("draw2d/with_namespace/dist/patched_Class.js");

    // Connection routing
    JSScripts.add("draw2d/with_namespace/dist/pathfinding-browser.min.js");

    // Draw 2D
    JSScripts.add("draw2d/with_namespace/dist/draw2d.js");

    // Custom JS files
    JSScripts.add("renderer/draw2d/javaScript/dawnDiagramLib.js");
    JSScripts.add("renderer/draw2d/javaScript/treeviewJs.js");
    JSScripts.add("renderer/draw2d/javaScript/customJs.js");

    JSScripts.addAll(createBasicDawnIncludes());
    JSScripts.addAll(createProjectSpecificIncludes(projectPluginId));

    // JSRenderScripts.add("var workflow = new draw2d.Canvas(\"paintarea\");");
    JSRenderScripts.add(renderGlobalVars(resource, request.getSession().getId()));
    // JSRenderScripts.addAll(renderDiagram(vidualIdToFigure, diagram));
    // JSRenderScripts.add(renderListeners());

    // Buffer for the diagram
    DiagramExchangeObject syntaxHierarchy = toSyntaxHierarchy(diagram, null);

    ArrayList<DiagramExchangeObject> clusters = renderClusters(diagram);

    DawnAccessibleRenderer renderer = new DawnAccessibleRenderer();

    // Set some variables for the JS
    ArrayList<String[]> JSVariables = new ArrayList<String[]>();
    JSVariables = getFeatureIds(diagram);

    return renderer.renderPage(JSScripts, JSRenderScripts, syntaxHierarchy, clusters, JSVariables);

  }

  /**
   * Initialize the global variables from the dawn JS include
   */
  private String renderGlobalVars(Resource resource, String httpSessionId)
  {

    URI uri = resource.getURI();
    long lastChanged = DawnResourceRegistry.instance.getLastChanged(uri, httpSessionId);

    return "DawnWebUtil.init('" + uri + "'," + lastChanged + ");\n";
  }

  /**
   * Adds the JS event listeners.
   */
  private String renderListeners()
  {
    return "workflow.getCommandStack().addEventListener(new org.eclipse.emf.cdo.dawn.web.basic.DawnCommandListener(DawnWebUtil.moveNode,DawnWebUtil.deleteNode));";
  }

  /**
   * Converts the diagram to an exchange format.
   */
  private DiagramExchangeObject toSyntaxHierarchy(Diagram diagram, Graph graph)
  {
    ArrayList<String> nodeIds = graph == null ? null : graph.getNodeIds();

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
    // return CDOUtil.getCDOObject(object).cdoID().toString();
    return DawnWebUtil.getUniqueId(object);
  }

  private ArrayList<DiagramExchangeObject> renderClusters(Diagram diagram)
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

  private ArrayList<Graph> clusterGraph(Graph graph)
  {

    ArrayList<Graph> result = new ArrayList<Graph>();

    // Store a backup of all links in their original state
    ArrayList<Link> allLinks = new ArrayList<Link>();
    for (Link l : graph.getLinks())
    {
      allLinks.add(new Link(l));
    }

    if (graph.getSize() > CLUSTER_SIZE_THRESHOLD)
    {

      // Recursively contract the heaviest edge until halved
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
      // Return the input graph
      result.add(graph);
    }

    return result;

  }

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

    // Add links
  }

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
        addLinkInGraph(resultGraph, edge, ASSOCIATION_WEIGHT);
      }

      if (edge.getElement() instanceof Generalization)
      {
        addLinkInGraph(resultGraph, edge, GENERALIZATION_WEIGHT);
      }

    }

    return resultGraph;

  }

  private void addLinkInGraph(Graph graph, Edge edge, int weight)
  {

    GraphNode sourceNode = graph.getNodeById(getCdoId(edge.getSource()));
    GraphNode targetNode = graph.getNodeById(getCdoId(edge.getTarget()));

    Link link = new Link(sourceNode, targetNode, weight);

    graph.addLink(link);

    sourceNode.addLink(link);
    targetNode.addLink(link);
  }

  /**
   * Renders the graphical diagram elements. Each item in the ArrayList represents one line of JS code.
   */
  private ArrayList<String> renderDiagram(Map<String, FigureMapping> vidualIdToFigure, Diagram diagram)
  {
    ArrayList<String> result = new ArrayList<String>();

    for (Object v : diagram.getChildren())
    {
      if (v instanceof Node)
      {
        Node node = (Node)v;
        BoundsImpl bounds = (BoundsImpl)node.getLayoutConstraint();
        String uniqueId = DawnWebUtil.getUniqueId((EObject)v);
        String varName = VarNameConverter.convert(uniqueId);

        EStructuralFeature nameAttr = getFeatureFromName(node.getElement(), "name");
        FigureMapping figureMapping = vidualIdToFigure.get(node.getType());
        result.add("var " + varName + " = new " + figureMapping.getJavaScriptClass() + "(\""
            + node.getElement().eGet(nameAttr) + "\");");
        result.add(varName + ".setId(\"" + uniqueId + "\");");

        int i = 0;
        for (Object childObj : node.getChildren())
        {
          if (childObj instanceof View)
          {
            result.addAll(createChildViews((View)childObj, varName, vidualIdToFigure, i++));
          }
        }
        result.add(varName + ".setDimension(" + bounds.getWidth() + ", " + bounds.getHeight() + ");");
        result.add("workflow.add(" + varName + "," + bounds.getX() * 1 + "," + bounds.getY() * 1 + ");");
      }
    }

    for (Object v : diagram.getEdges())
    {

      Edge edge = (Edge)v;

      String varName = VarNameConverter.convert(DawnWebUtil.getUniqueId(edge));
      String uniqueId = DawnWebUtil.getUniqueId((EObject)v);
      FigureMapping figureMapping = vidualIdToFigure.get(edge.getType());
      result.add("var " + varName + " = new " + figureMapping.getJavaScriptClass() + "();");
      result.add(varName + ".setId(\"" + uniqueId + "\");");
      String sourceVarName = null;
      String targetVarName = null;

      if (edge.getSource() != null)
      {
        sourceVarName = VarNameConverter.convert(DawnWebUtil.getUniqueId(edge.getSource()));

        result.add(varName + ".setSource(" + sourceVarName + ".portTop);");
      }
      if (edge.getTarget() != null)
      {
        targetVarName = VarNameConverter.convert(DawnWebUtil.getUniqueId(edge.getTarget()));

        result.add(varName + ".setTarget(" + targetVarName + ".portBottom);");
      }

      Bendpoints bendpoints = edge.getBendpoints();
      int vi = 0;
      if (bendpoints instanceof RelativeBendpoints && sourceVarName != null)
      {

        @SuppressWarnings("unchecked")
        List<RelativeBendpoint> points = ((RelativeBendpoints)bendpoints).getPoints();
        for (Iterator iterator = points.iterator(); iterator.hasNext();)
        {
          RelativeBendpoint p = (RelativeBendpoint)iterator.next();

          if (vi > 0 && iterator.hasNext())
          {
            result.add(varName + ".insertVertexAt(" + vi + ", " + targetVarName + ".portBottom.getAbsoluteX()" + " + "
                + p.getTargetX() + ", " + targetVarName + ".portBottom.getAbsoluteY()" + " + " + p.getTargetY() * 1
                + ");");
          }
          vi++;
        }

      }

      result.add("workflow.add(" + varName + ");");

    }

    return result;
  }

  protected ArrayList<String> createChildViews(View childView, String varName,
      Map<String, FigureMapping> vidualIdToFigure, int i)
  {
    ArrayList<String> result = new ArrayList<String>();

    String childVarName = VarNameConverter.convert(DawnWebUtil.getUniqueId(childView)) + "_" + i;
    FigureMapping figureMapping = vidualIdToFigure.get(childView.getType());

    if (figureMapping != null)
    {
      String childFigureJavaScriptClassName = figureMapping.getJavaScriptClass();
      if (childFigureJavaScriptClassName != null && !childFigureJavaScriptClassName.equals("null"))
      {

        EObject viewElement = childView.getElement();
        if (viewElement == null)
        {
          result.add("var " + childVarName + " = new " + childFigureJavaScriptClassName + "();");
        }
        else
        {
          String viewPattern = figureMapping.getViewPattern();
          String viewString = "";

          EStructuralFeature tmpAttr = null;
          if (viewPattern == null || viewPattern.equals(""))
          {
            for (ViewAttribute s : figureMapping.getViewAttributes())
            {
              EStructuralFeature nameAttr = getFeatureFromName(viewElement, s.getName());
              viewString += viewElement.eGet(nameAttr) + "";
              tmpAttr = nameAttr;
            }
          }
          else
          {
            viewString = viewPattern;
            int c = 0;
            for (ViewAttribute s : figureMapping.getViewAttributes())
            {
              EStructuralFeature nameAttr = getFeatureFromName(viewElement, s.getName());
              if (nameAttr != null)
              {
                CharSequence stringValue;
                Object feature = viewElement.eGet(nameAttr);

                if (feature instanceof ENamedElement)
                {
                  stringValue = ((ENamedElement)feature).getName();
                }
                else if (feature instanceof EObject && getFeatureFromName((EObject)feature, "name") != null)
                {
                  EObject o = (EObject)feature;
                  EStructuralFeature f = getFeatureFromName(o, "name");
                  Object potentialName = o.eGet(f);
                  stringValue = potentialName != null ? potentialName.toString() : feature.toString();
                }
                else
                {
                  stringValue = feature == null ? "" : feature.toString();
                }

                viewString = viewString.replace("{" + c++ + "}", stringValue + "");
                tmpAttr = nameAttr;
              }
              else
              {
                System.out.println("not found " + s.getName());
              }
            }
          }

          result.add("var " + childVarName + " = new " + childFigureJavaScriptClassName + "(\"" + viewString + "\");");

          // just a small quick hack. Fix this asap!
          if (childFigureJavaScriptClassName.toLowerCase().contains("Name".toLowerCase())
              || childFigureJavaScriptClassName.toLowerCase().contains("Label".toLowerCase()))
          {

            result.add(childVarName + ".setSemanticElementId('" + DawnWebUtil.getUniqueId(viewElement) + "');");
            result.add(childVarName + ".setFeatureId(" + tmpAttr.getFeatureID() + ");");
          }

        }
      }

      int a = 0;

      for (Object childObj : childView.getChildren())
      {
        if (childObj instanceof View)
        {
          result.addAll(createChildViews((View)childObj, childVarName, vidualIdToFigure, a++));
        }
      }
      if (childFigureJavaScriptClassName != null && !childFigureJavaScriptClassName.equals("null"))
      {
        result.add(varName + ".add(" + childVarName + ");");

      }
    }

    return result;
  }

  private ArrayList<String[]> getFeatureIds(Diagram diagram)
  {
    ArrayList<String[]> result = new ArrayList<String[]>();

    for (Object o : diagram.getChildren())
    {
      if (o instanceof Node)
      {
        String[] keyValuePair = new String[2];
        EStructuralFeature nodeNameFeature = getFeatureFromName(((Node)o).getElement(), "name");
        keyValuePair[0] = "nameFeatureId";
        keyValuePair[1] = String.valueOf(nodeNameFeature.getFeatureID());
        result.add(keyValuePair);
        break;
      }
    }

    // More feature IDs can be added here

    return result;
  }

  private EStructuralFeature getFeatureFromName(EObject element, String attrName)
  {
    EStructuralFeature nameAttr = null;
    for (EStructuralFeature attr : element.eClass().getEAllStructuralFeatures())
    {
      if (attr.getName().equals(attrName))
      {
        nameAttr = attr;
        break;
      }
    }
    return nameAttr;
  }

  public ArrayList<String> createProjectSpecificIncludes(String pluginId)
  {
    return addJSLibsFromPath(WEB_CONTENT_JAVASCRIPT_FIGURES + pluginId);
  }

  protected ArrayList<String> createBasicDawnIncludes()
  {
    ArrayList<String> resultBuffer = new ArrayList<String>();
    resultBuffer.add(DAWN_JAVASCRIPT_FIGURES + "package.js");

    String path = WEB_CONTENT_JAVASCRIPT_FIGURES + BASIC_INCLUDES_SVG_NAME;

    resultBuffer.addAll(addJSLibsFromPath(path));

    return resultBuffer;
  }

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

  private Map<String, FigureMapping> getFigureMapping(String projectPluginId)
  {
    Bundle bundle = DawnJSDraw2dBundle.getBundleContext().getBundle();

    Enumeration<?> entries = bundle.findEntries("/web_content/javascript/figures/" + projectPluginId + "/meta/",
        "config.xml", true);

    URL resourceURL = (URL)entries.nextElement();

    FigureMappingParser figureMappingParser = new FigureMappingParser();
    return null;// figureMappingParser.parse(resourceURL);
  }
}
