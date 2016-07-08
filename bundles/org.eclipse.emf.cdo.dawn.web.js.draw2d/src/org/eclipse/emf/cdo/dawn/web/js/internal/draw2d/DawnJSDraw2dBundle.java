package org.eclipse.emf.cdo.dawn.web.js.internal.draw2d;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class DawnJSDraw2dBundle implements BundleActivator
{
  public static final String BASIC_INCLUDES_NAME = "org.eclipse.emf.cdo.dawn.web.js.draw2d";

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
