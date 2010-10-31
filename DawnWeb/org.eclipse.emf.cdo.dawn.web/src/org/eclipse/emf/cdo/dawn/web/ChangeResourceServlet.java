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
package org.eclipse.emf.cdo.dawn.web;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.dawn.util.DawnResourceHelper;
import org.eclipse.emf.cdo.dawn.web.registry.DawnResourceRegistry;
import org.eclipse.emf.cdo.dawn.web.util.DawnWebUtil;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class ChangeResourceServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  private HttpSession httpSession;

  private URI resourceURI;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    httpSession = request.getSession();
    String method = request.getParameter("method");
    resourceURI = URI.createURI(request.getParameter("resourceURI"));
    String uuid = request.getParameter("uuid");

    System.err.println("URI " + resourceURI);
    System.err.println("UUID:" + uuid);
    System.err.println("method:" + method);

    if (method.equals("moveNode"))
    {
      moveNode(uuid, Integer.parseInt(request.getParameter("x")), Integer.parseInt(request.getParameter("y")));
    }
    else if (method.equals("deleteView"))
    {
      deleteNode(uuid);
    }
    else if (method.equals("changeFeature"))
    {
      changeFeature(uuid, Integer.parseInt(request.getParameter("featureId")), request.getParameter("value"));
    }
    else
    {
      throw new UnsupportedOperationException("UnsupportedMethod: " + method);
    }
  }

  private void changeFeature(String uuid, int featureId, String value)
  {

    CDOResource resource = DawnResourceRegistry.instance.getResource(resourceURI, httpSession.getId());
    CDOView cdoView = resource.cdoView();

    if (cdoView instanceof CDOTransaction)
    {
      InternalEObject element = (InternalEObject)CDOUtil.getEObject(DawnWebUtil.getObjectFromId(uuid, cdoView));
      element.eSet(featureId, value);

      try
      {
        ((CDOTransaction)cdoView).commit();
      }
      catch (CommitException ex)
      {
        throw new RuntimeException(ex);
      }
    }
    else
    {
      throw new RuntimeException("Cannot modifiy resource on read-only CDOView");
    }
  }

  private void deleteNode(String uuId)
  {
    CDOResource resource = DawnResourceRegistry.instance.getResource(resourceURI, httpSession.getId());
    CDOID cdoId = CDOIDUtil.read(uuId);
    CDOView cdoView = resource.cdoView();
    if (cdoView instanceof CDOTransaction)
    {
      View view = (View)CDOUtil.getEObject(cdoView.getObject(cdoId));
      DawnResourceHelper.deleteViewInResource(resource, view);

      try
      {
        ((CDOTransaction)cdoView).commit();
      }
      catch (CommitException ex)
      {
        throw new RuntimeException(ex);
      }
    }
    else
    {
      throw new RuntimeException("Cannot modifiy resource on read-only CDOView");
    }
  }

  private void moveNode(String uuId, int x, int y)
  {
    CDOResource resource = DawnResourceRegistry.instance.getResource(resourceURI, httpSession.getId());
    CDOID cdoId = CDOIDUtil.read(uuId);
    CDOView cdoView = resource.cdoView();

    if (cdoView instanceof CDOTransaction)
    {
      Node node = (Node)CDOUtil.getEObject(cdoView.getObject(cdoId));
      Bounds bounds = (Bounds)node.getLayoutConstraint();
      bounds.setX(x);
      bounds.setY(y);

      try
      {
        ((CDOTransaction)cdoView).commit();
      }
      catch (CommitException ex)
      {
        throw new RuntimeException(ex);
      }
    }
    else
    {
      throw new RuntimeException("Cannot modifiy resource on read-only CDOView");
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}
