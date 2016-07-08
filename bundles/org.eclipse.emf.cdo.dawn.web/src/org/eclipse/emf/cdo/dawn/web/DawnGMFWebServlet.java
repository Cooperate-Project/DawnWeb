package org.eclipse.emf.cdo.dawn.web;

import org.eclipse.emf.cdo.dawn.examples.acore.AcorePackage;
import org.eclipse.emf.cdo.dawn.web.registry.DawnResourceRegistry;
import org.eclipse.emf.cdo.dawn.web.renderer.DawnWebRendererFactory;
import org.eclipse.emf.cdo.dawn.web.renderer.IDawnWebRenderer;
import org.eclipse.emf.cdo.eresource.CDOResource;

import org.eclipse.emf.common.util.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class DawnGMFWebServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    AcorePackage.eINSTANCE.getClass();

    HttpSession httpSession = request.getSession();

    String projectPluginId = request.getParameter("pluginid");
    String resourcePath = request.getParameter("path");
    String type = request.getParameter("type");

    // CDOConnectionUtil.instance.init("repo1", "tcp", "localhost");
    // CDOSession session = CDOConnectionUtil.instance.openSession();
    // CDOView view = session.openView();
    //
    // CDOResource diagramResource = view.getResource(resourcePath);

    String repoName = "repo1";
    CDOResource diagramResource = DawnResourceRegistry.instance.getResource(
        URI.createURI("cdo://" + repoName + "/" + resourcePath), httpSession.getId());

    IDawnWebRenderer renderer = DawnWebRendererFactory.instance.getRenderer(type);// new DawnJavaScriptRenderer();

    if (renderer == null)
    {
      response.setContentType("text/html");
      response.sendError(1503, "A renderer for type " + type + " cannot be found");
    }
    else
    {
      renderer.render(diagramResource, projectPluginId, request, response, getServletContext());
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }

}
