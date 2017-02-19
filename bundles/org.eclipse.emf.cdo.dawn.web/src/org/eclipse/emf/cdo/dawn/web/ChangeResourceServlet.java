/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 *    Shengjia Feng - Changed editing features for the accessible Dawn Web Editor
 */
package org.eclipse.emf.cdo.dawn.web;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.dawn.internal.web.OM;
import org.eclipse.emf.cdo.dawn.web.registry.DawnResourceRegistry;
import org.eclipse.emf.cdo.dawn.web.util.DawnWebGMFUtil;
import org.eclipse.emf.cdo.dawn.web.util.DawnWebUtil;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.gmf.runtime.notation.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;

public class ChangeResourceServlet extends HttpServlet
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_OBJECT, ChangeResourceServlet.class);

  private static final long serialVersionUID = 1L;

  private HttpSession httpSession;

  private URI resourceURI;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    httpSession = request.getSession();
    resourceURI = URI.createURI(request.getParameter("resourceURI"));

    String method = request.getParameter("method");
    String uuid = request.getParameter("uuid");

    if (TRACER.isEnabled())
    {
      TRACER.format("Resource URI: {0}", resourceURI); //$NON-NLS-1$
      TRACER.format("UUID: {0}", uuid); //$NON-NLS-1$
      TRACER.format("method: {0}", method); //$NON-NLS-1$
    }

    boolean success = false;

    if (method.equals("deleteView"))
    {
      success = deleteNode(uuid);
    }
    else if (method.equals("changeFeature"))
    {
      success = changeFeature(uuid, Integer.parseInt(request.getParameter("featureId")), request.getParameter("value"));
    }
    else if (method.equals("addClass"))
    {
      success = addClass(request.getParameter("className"), Integer.valueOf(request.getParameter("x")),
          Integer.valueOf(request.getParameter("y")));
    }

    response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }

  /**
   * Adds a new class with the given class name to the diagram at the specified position.
   *
   * @param newClassName
   *          The name of the class to be created.
   * @param posX
   *          The position of the class to be created on the x-axis (horizontal).
   * @param posY
   *          The position of the class to be created on the y-axis (vertical).
   * @return <code>true</code> if operation succeeded, <code>false</code> otherwise
   */
  private boolean addClass(String newClassName, int posX, int posY)
  {
    CDOResource resource = DawnResourceRegistry.instance.getResource(resourceURI, httpSession.getId());
    CDOView cdoView = resource.cdoView();

    if (!checkTransaction(cdoView))
    {
      return false;
    }

    DawnWebGMFUtil.addClassToResource(resource, newClassName, posX, posY);

    try
    {
      resource.save(Collections.EMPTY_MAP);
      ((CDOTransaction)cdoView).commit();
    }
    catch (Exception ex)
    {
      return false;
    }

    return true;
  }

  /**
   * Changes a feature to a specified value of the element with the given UUID.
   *
   * @param uuid
   *          The UUID of the element to be altered.
   * @param featureId
   *          The ID of the feature to be altered according to EStructuralFeatures.
   * @param value
   *          The target value of the feature.
   * @return <code>true</code> if operation succeeded, <code>false</code> otherwise
   */
  private boolean changeFeature(String uuid, int featureId, String value)
  {
    CDOResource resource = DawnResourceRegistry.instance.getResource(resourceURI, httpSession.getId());
    CDOView cdoView = resource.cdoView();

    if (!checkTransaction(cdoView))
    {
      return false;
    }

    InternalEObject element = (InternalEObject)CDOUtil.getEObject(DawnWebUtil.getObjectFromId(uuid, cdoView));
    ((View)element).getElement().eSet(getFeatureFromId(element, featureId), value);

    try
    {
      ((CDOTransaction)cdoView).commit();
    }
    catch (CommitException ex)
    {
      return false;
    }

    return true;
  }

  /**
   * Deletes a node in the diagram (= class).
   *
   * @param uuid
   *          The UUID of the element to be deleted.
   * @return <code>true</code> if operation succeeded, <code>false</code> otherwise
   */
  private boolean deleteNode(String uuId)
  {
    CDOResource resource = DawnResourceRegistry.instance.getResource(resourceURI, httpSession.getId());
    CDOID cdoId = CDOIDUtil.read(uuId);
    CDOView cdoView = resource.cdoView();

    if (!checkTransaction(cdoView))
    {
      return false;
    }

    View view = (View)CDOUtil.getEObject(cdoView.getObject(cdoId));
    DawnWebGMFUtil.deleteViewInResource(resource, view);

    try
    {
      ((CDOTransaction)cdoView).commit();
    }
    catch (CommitException ex)
    {
      return false;
    }

    return true;
  }

  /**
   * Gets the corresponding EStructuralFeature from a EObject with a specified ID.
   *
   * @param element
   *          The EObject to get the EStructuralFeature from.
   * @param id
   *          The ID of the EStructuralFeature to get.
   * @return The EStructuralFeature with the specified ID.
   */
  private EStructuralFeature getFeatureFromId(EObject element, int id)
  {
    for (EStructuralFeature attr : element.eClass().getEAllStructuralFeatures())
    {
      if (attr.getFeatureID() == id)
      {
        return attr;
      }
    }
    return null;
  }

  /**
   * Checks whether the given CDO View is a transaction.
   *
   * @param cdoView
   *          The CDO View to check for.
   */
  private boolean checkTransaction(CDOView cdoView)
  {
    return cdoView instanceof CDOTransaction;
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}
