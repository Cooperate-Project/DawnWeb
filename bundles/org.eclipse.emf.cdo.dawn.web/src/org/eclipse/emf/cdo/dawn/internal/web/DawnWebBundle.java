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
package org.eclipse.emf.cdo.dawn.internal.web;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class DawnWebBundle implements BundleActivator
{
  public static final String DAWN = "dawn";

  public static final String WEB_CONTENT = "web_content";

  private static BundleContext bundleContext;

  public void start(BundleContext context) throws Exception
  {
    bundleContext = context;
  }

  public void stop(BundleContext context) throws Exception
  {
  }

  public static BundleContext getBundleContext()
  {
    return bundleContext;
  }
}
