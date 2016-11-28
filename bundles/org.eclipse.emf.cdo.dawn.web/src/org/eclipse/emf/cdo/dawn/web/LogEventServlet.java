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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class LogEventServlet extends HttpServlet
{
  private static final long serialVersionUID = 2L;

  private static final String LOG_FILE_URL = "/Users/Shengjia/Downloads/log.csv";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String message = request.getParameter("message");

    // Check if file exists
    Path path = Paths.get(LOG_FILE_URL);

    File f = new File(LOG_FILE_URL);
    if (!f.exists() || f.isDirectory())
    {
      Files.createDirectories(path.getParent());
      Files.createFile(path);
    }

    // Write into file
    try
    {
      List<String> lines = Arrays.asList(message);
      Files.write(path, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
      response.setStatus(HttpServletResponse.SC_OK);
    }
    catch (Exception e)
    {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}
