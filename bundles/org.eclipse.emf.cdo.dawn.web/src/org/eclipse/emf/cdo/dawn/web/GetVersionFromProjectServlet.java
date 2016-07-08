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

import org.eclipse.emf.cdo.dawn.web.registry.DawnResourceRegistry;
import org.eclipse.emf.cdo.eresource.CDOResource;

import org.eclipse.emf.common.util.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class GetVersionFromProjectServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession httpSession = request.getSession();
    String resourceURILiteral = request.getParameter("resourceURI");

    URI uri = URI.createURI(resourceURILiteral);
    DawnResourceRegistry.instance.getResource(uri, httpSession.getId());

    CDOResource resource = DawnResourceRegistry.instance.getResource(uri, httpSession.getId(), false);// view.getResource(uri.path());

    if (resource == null)
    {
      throw new RuntimeException("Resource could not be found: " + uri);
    }

    response.setContentType("text/html");

    response.getWriter().write(DawnResourceRegistry.instance.getLastChanged(uri, httpSession.getId()) + "");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}
