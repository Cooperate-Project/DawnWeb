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

import org.eclipse.gmf.runtime.notation.Bendpoints;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.gmf.runtime.notation.impl.BoundsImpl;
import org.eclipse.uml2.uml.NamedElement;

import org.osgi.framework.Bundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Martin Fluegge
 */
public class DawnJavaScriptDraw2DRenderer implements IDawnWebRenderer
{
  private static final boolean USE_SVG = true;

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

    // String nextJSP = "/renderer/draw2d/showDiagram.jsp";
    // RequestDispatcher dispatcher = servletContext.getRequestDispatcher(nextJSP);
    // try
    // {
    // dispatcher.forward(request, response);
    // }
    // catch (ServletException ex1)
    // {
    // ex1.printStackTrace();
    // }
    // catch (IOException ex1)
    // {
    // ex1.printStackTrace();
    // }

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
    StringBuffer buffer = new StringBuffer();
    buffer.append(
        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
            + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
            // +
            // "<meta http-equiv=\"refresh\" content=\"3; URL=showDiagram?projectName="
            // + projectName
            // + "\">"
            + "<head>\n" + "<title>Dawn Web-Viewer</title>\n"
            // + "<link href=\"plugin.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
            + "");

    if (USE_SVG)
    {
      // jquery
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/jquery.min.js"));
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/jquery-ui.min.js"));

      // touch
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/jquery.autoresize.js"));
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/jquery-touch_punch.js"));
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/jquery.contextmenu.js"));

      // shifty.js
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/shifty.js"));

      // raphael.js
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/patched_raphael.js"));
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/rgbcolor.js"));
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/patched_canvg.js"));

      // class.js
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/patched_Class.js"));

      // Connection routing
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/pathfinding-browser.min.js"));

      // Draw 2D
      buffer.append(addJSLibrary("draw2d/with_namespace/dist/draw2d.js"));

    }
    else
    {
      buffer.append(addJSLibrary("draw2d/with_namespace/normal/wz_jsgraphics.js"));
      buffer.append(addJSLibrary("draw2d/with_namespace/normal/mootools.js"));
      buffer.append(addJSLibrary("draw2d/with_namespace/normal/moocanvas.js"));
      buffer.append(addJSLibrary("draw2d/with_namespace/single/draw2d.js"));
      // buffer.append(addJSLibrary("draw2d/with_namespace/normal/shape/package.js"));
      // buffer.append(addJSLibrary("draw2d/with_namespace/normal/shape/uml/package.js"));
      // buffer.append(addJSLibrary("draw2d/with_namespace/normal/shape/uml/Actor.js"));
      // buffer.append(addJSLibrary("draw2d/with_namespace/normal/shape/uml/Class.js"));
      buffer.append(addJSLibrary("prototype/prototype.js"));
    }
    buffer.append(addJSLibrary("renderer/draw2d/javaScript/dawnDiagramLib.js"));
    buffer.append(createBasicDawnIncludes());
    buffer.append(createProjectSpecificIncludes(projectPluginId));

    if (USE_SVG)
    {
      buffer.append("</head>\n" + "<body>\n"
          + "<div id=\"paintarea\" style=\"position:absolute;left:0px;top:0px;width:1400px;height:1000px\" >\n"
          + "<!-- The information help text -->\n" + "</div>\n" + "</div>\n" + "<script type=\"text/javascript\">\n"
          + "var workflow  = new draw2d.Canvas(\"paintarea\");\n");

      // + "var label2 = new org.eclipse.emf.cdo.dawn.web.basic.DawnLabel({text:\"This is a simple label\"});\n"
      // + "var box = new org.eclipse.emf.cdo.dawn.web.basic.DawnCompartmentFigure();\n" + "box.add(label2, 10, 10);\n"
      // + "workflow.add(box,120,150);\n");

      renderGlobalVars(resource, request.getSession().getId(), buffer);
      renderDiagram(vidualIdToFigure, diagram, buffer);
      renderListeners(buffer);
      buffer.append("</script>\n" + "</body>\n" + "</html>\n");
    }
    else
    {
      buffer.append("</head>\n" + "<body>\n"
          + "<div id=\"paintarea\" style=\"position:absolute;left:0px;top:0px;width:3000px;height:3000px\" >\n"
          + "<!-- The information help text -->\n" + "</div>\n" + "</div>\n" + "<script type=\"text/javascript\">\n"
          + "var workflow  = new draw2d.Workflow(\"paintarea\");\n");

      renderGlobalVars(resource, request.getSession().getId(), buffer);
      renderDiagram(vidualIdToFigure, diagram, buffer);
      renderListeners(buffer);
      buffer.append("</script>\n" + "</body>\n" + "</html>\n");
    }

    return buffer.toString();
  }

  /**
   * initalize the gloabl vars from the dawn JS include
   */
  private void renderGlobalVars(Resource resource, String httpSessionId, StringBuffer buffer)
  {
    URI uri = resource.getURI();
    // buffer.append("var resourceURI='" + uri + "';\n");
    long lastChanged = DawnResourceRegistry.instance.getLastChanged(uri, httpSessionId);

    // buffer.append("var resourceLastChanged='" + lastChanged + "';\n");

    buffer.append("DawnWebUtil.init('" + uri + "'," + lastChanged + ");\n");
  }

  private void renderListeners(StringBuffer buffer)
  {
    buffer.append(
        "workflow.getCommandStack().addEventListener(new org.eclipse.emf.cdo.dawn.web.basic.DawnCommandListener(DawnWebUtil.moveNode,DawnWebUtil.deleteNode));");
  }

  private void renderDiagram(Map<String, FigureMapping> vidualIdToFigure, Diagram diagram, StringBuffer buffer)
  {
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
        buffer.append("var " + varName + " = new " + figureMapping.getJavaScriptClass() + "(\""
            + node.getElement().eGet(nameAttr) + "\");\n");
        buffer.append("" + varName + ".setId(\"" + uniqueId + "\");\n");

        int i = 0;
        for (Object childObj : node.getChildren())
        {

          if (childObj instanceof View)
          {
            createChildViews(buffer, (View)childObj, varName, vidualIdToFigure, i++);
          }
        }
        if (USE_SVG)
        {
          buffer.append(varName + ".setDimension(" + bounds.getWidth() + ", " + bounds.getHeight() + ");"
              + "\nworkflow.add(" + varName + "," + bounds.getX() * 1 + "," + bounds.getY() * 1 + ");\n");
        }
        else
        {
          buffer.append(varName + ".setDimension(" + bounds.getWidth() + ", " + bounds.getHeight() + ");"
              + "\nworkflow.addFigure(" + varName + "," + bounds.getX() * 1 + "," + bounds.getY() * 1 + ");\n");
        }
      }
    }

    for (Object v : diagram.getEdges())
    {

      Edge edge = (Edge)v;

      String varName = VarNameConverter.convert(DawnWebUtil.getUniqueId(edge));
      String uniqueId = DawnWebUtil.getUniqueId((EObject)v);
      FigureMapping figureMapping = vidualIdToFigure.get(edge.getType());
      buffer.append("var " + varName + " = new " + figureMapping.getJavaScriptClass() + "();\n");
      buffer.append("" + varName + ".setId(\"" + uniqueId + "\");\n");
      String sourceVarName = null;
      String targetVarName = null;

      if (edge.getSource() != null)
      {
        sourceVarName = VarNameConverter.convert(DawnWebUtil.getUniqueId(edge.getSource()));

        buffer.append(varName + ".setSource(" + sourceVarName + ".portTop);\n");
      }
      if (edge.getTarget() != null)
      {
        targetVarName = VarNameConverter.convert(DawnWebUtil.getUniqueId(edge.getTarget()));

        buffer.append(varName + ".setTarget(" + targetVarName + ".portBottom);\n");
      }

      if (USE_SVG)
      {
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
              // buffer.append("alert (" + targetVarName + ".portBottom.getPosition().getX());\n");
              buffer.append(varName + ".insertVertexAt(" + vi // + ", 50, 50);\n");
                  + ", " + targetVarName + ".portBottom.getAbsoluteX()" + " + " + p.getTargetX() + ", " + targetVarName
                  + ".portBottom.getAbsoluteY()" + " + " + p.getTargetY() * 1 + ");\n");
            }
            vi++;
          }

        }

        buffer.append("workflow.add(" + varName + ");\n");
      }
      else
      {
        buffer.append("workflow.addFigure(" + varName + ");\n");
      }
    }
  }

  protected void createChildViews(StringBuffer buffer, View childView, String varName,
      Map<String, FigureMapping> vidualIdToFigure, int i)
  {

    String childVarName = VarNameConverter.convert(DawnWebUtil.getUniqueId(childView)) + "_" + i;
    FigureMapping figureMapping = vidualIdToFigure.get(childView.getType());

    // String
    // childFigureJavaScriptClassName=vidualIdToFigure.get(childView.getType());
    if (figureMapping != null)
    {
      String childFigureJavaScriptClassName = figureMapping.getJavaScriptClass();
      if (childFigureJavaScriptClassName != null && !childFigureJavaScriptClassName.equals("null"))
      {

        EObject viewElement = childView.getElement();
        if (viewElement == null)
        {
          buffer.append("var " + childVarName + " = new " + childFigureJavaScriptClassName + "();\n");
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
                else if (feature instanceof NamedElement)
                {
                  stringValue = ((NamedElement)feature).getName();
                }
                else
                {
                  stringValue = feature.toString();
                }

                viewString = viewString.replace("{" + c++ + "}", stringValue + "");
                tmpAttr = nameAttr;
              }
              else
              {
                System.out.println("not found" + s.getName());
              }
            }
          }

          buffer.append(
              "var " + childVarName + " = new " + childFigureJavaScriptClassName + "(\"" + viewString + "\");\n");

          // just a small quick hack. Fix this asap!
          if (childFigureJavaScriptClassName.toLowerCase().contains("Name".toLowerCase())
              || childFigureJavaScriptClassName.toLowerCase().contains("Label".toLowerCase()))
          {

            buffer.append(childVarName + ".setSemanticElementId('" + DawnWebUtil.getUniqueId(viewElement) + "');\n");
            buffer.append(childVarName + ".setFeatureId(" + tmpAttr.getFeatureID() + ");\n");
          }

        }
      }

      int a = 0;

      for (Object childObj : childView.getChildren())
      {
        if (childObj instanceof View)
        {
          createChildViews(buffer, (View)childObj, childVarName, vidualIdToFigure, a++);
        }
      }
      if (childFigureJavaScriptClassName != null && !childFigureJavaScriptClassName.equals("null"))
      {
        if (USE_SVG)
        {
          buffer.append(varName + ".add(" + childVarName + ");\n");
        }
        else
        {
          buffer.append(varName + ".addChild(" + childVarName + ");\n");
        }
      }
    }
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

  public String createProjectSpecificIncludes(String pluginId)
  {
    StringBuffer ret = new StringBuffer();
    String path = WEB_CONTENT_JAVASCRIPT_FIGURES + pluginId;
    addJSLibsFromPath(ret, path);

    return ret.toString();
  }

  protected String createBasicDawnIncludes()
  {
    StringBuffer ret = new StringBuffer();
    ret.append(addJSLibrary(DAWN_JAVASCRIPT_FIGURES + "package.js"));
    String path;

    if (USE_SVG)
    {
      path = WEB_CONTENT_JAVASCRIPT_FIGURES + BASIC_INCLUDES_SVG_NAME;
    }
    else
    {
      path = WEB_CONTENT_JAVASCRIPT_FIGURES + BASIC_INCLUDES_NAME;
    }

    addJSLibsFromPath(ret, path);

    return ret.toString();
  }

  private void addJSLibsFromPath(StringBuffer ret, String path)
  {
    Bundle bundle = DawnJSDraw2dBundle.getBundleContext().getBundle();
    Enumeration<?> entries = bundle.findEntries(path, "*.js", true);

    while (entries.hasMoreElements())
    {
      URL url = (URL)entries.nextElement();
      String libraryPath = url.getPath();

      if (libraryPath.endsWith(".js") && !libraryPath.contains("package.js"))
      {
        ret.append(addJSLibrary(libraryPath.replace(DawnWebBundle.WEB_CONTENT, RENDERER_DRAW2D)));
      }
    }
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
    // URL res = DawnWebBundle.getBundleContext().getBundle().getResource("/");
    // System.out.println("------" + res.getPath());
    // URL resourceURL = DawnWebBundle.getBundleContext().getBundle()
    // .getResource("/web_content/javascript/figures/" + projectPluginId + "/meta/config.xml");

    Bundle bundle = DawnJSDraw2dBundle.getBundleContext().getBundle();
    Enumeration<?> entries = bundle.findEntries("/web_content/javascript/figures/" + projectPluginId + "/meta/",
        "config.xml", true);

    URL resourceURL = (URL)entries.nextElement();

    System.out.println(resourceURL);

    FigureMappingParser figureMappingParser = new FigureMappingParser();
    return figureMappingParser.parse(resourceURL);
  }

  private Object addJSLibrary(String libraryPath)
  {
    return "<SCRIPT src='" + libraryPath + "'>" + "</SCRIPT>\n";
  }
}
