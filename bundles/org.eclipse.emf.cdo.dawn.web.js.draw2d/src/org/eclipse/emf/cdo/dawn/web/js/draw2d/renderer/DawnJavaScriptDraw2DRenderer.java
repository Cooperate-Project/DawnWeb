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
import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.Bendpoints;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.gmf.runtime.notation.impl.BoundsImpl;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.util.UMLSwitch;

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

    // Buffer for JavaScript scripts loaded into the <head> area
    ArrayList<String> JSScripts = new ArrayList<String>();

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

    JSScripts.add("renderer/draw2d/javaScript/dawnDiagramLib.js");
    JSScripts.addAll(createBasicDawnIncludes());
    JSScripts.addAll(createProjectSpecificIncludes(projectPluginId));

    // Buffer for all JS code lines that go into the <script> section at the bottom of the page
    ArrayList<String> JSRenderScripts = new ArrayList<String>();
    JSRenderScripts.add("var workflow  = new draw2d.Canvas(\"paintarea\");");
    JSRenderScripts.add(renderGlobalVars(resource, request.getSession().getId()));
    JSRenderScripts.addAll(renderDiagram(vidualIdToFigure, diagram));
    JSRenderScripts.add(renderListeners());

    // Buffer for the diagram JSON
    DiagramExchangeObject syntaxHierarchy = toSyntaxHierarchy(diagram);

    DawnAccessibleRenderer renderer = new DawnAccessibleRenderer();

    return renderer.renderPage(JSScripts, JSRenderScripts, syntaxHierarchy);

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
  private DiagramExchangeObject toSyntaxHierarchy(Diagram diagram)
  {
    // Prepare UML Switch
    Switch umlSwitch = new UMLSwitch();

    // Create fixed root structure
    DiagramExchangeObject result = new DiagramExchangeObject(diagram.getName());

    DiagramExchangeObject classes = new DiagramExchangeObject("classes");
    result.appendChild(classes);

    DiagramExchangeObject associations = new DiagramExchangeObject("associations");
    result.appendChild(associations);

    DiagramExchangeObject generalizations = new DiagramExchangeObject("generalizations");
    result.appendChild(generalizations);

    for (Object v : diagram.getChildren())
    {
      if (v instanceof Node)
      {
        Node node = (Node)v;

        EStructuralFeature nameAttr = getFeatureFromName(node.getElement(), "name");
        DiagramExchangeObject temp = new DiagramExchangeObject(classes, (String)node.getElement().eGet(nameAttr));

        for (Object c : node.getChildren())
        {
          if (c instanceof BasicCompartment)
          {
            BasicCompartment comp = (BasicCompartment)c;

          }
        }
      }
    }

    for (Object v : diagram.getEdges())
    {
      Edge edge = (Edge)v;

      if (umlSwitch.doSwitch(edge.getElement()) instanceof Association)
      {

        // This edge is an association
        EStructuralFeature nameAttr = getFeatureFromName(edge.getElement(), "name");
        DiagramExchangeObject temp = new DiagramExchangeObject(associations, (String)edge.eGet(nameAttr));

        // Add ends to the association
        if (edge.getSource() != null)
        {
          new DiagramExchangeObject(temp, "from", classes.getChildByName((String)edge.getSource().eGet(nameAttr)));
        }
        if (edge.getTarget() != null)
        {
          new DiagramExchangeObject(temp, "to", classes.getChildByName((String)edge.getTarget().eGet(nameAttr)));
        }

        associations.appendChild(temp);
      }
    }

    return result;
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

    System.out.println(resourceURL);

    FigureMappingParser figureMappingParser = new FigureMappingParser();
    return figureMappingParser.parse(resourceURL);
  }
}
