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
package org.eclipse.emf.cdo.dawn.web.renderer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Fluegge
 */
public class DawnWebRendererFactory
{
  public static DawnWebRendererFactory instance = new DawnWebRendererFactory();

  public static String EXTENSION_POINT_ID = "org.eclipse.emf.cdo.dawn.web.renderer";

  public Map<String, IDawnWebRenderer> registeredRenderers = new HashMap<String, IDawnWebRenderer>();

  public IDawnWebRenderer getRenderer(String type)
  {

    IDawnWebRenderer renderer = registeredRenderers.get(type);

    if (renderer == null)
    {
      renderer = findRenderer(type);
      registeredRenderers.put(type, renderer);
    }
    return renderer;

  }

  private IDawnWebRenderer findRenderer(String type)
  {
    IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID);
    try
    {
      for (IConfigurationElement e : config)
      {

        if (e.getAttribute("type").equals(type))
        {
          return (IDawnWebRenderer)e.createExecutableExtension("renderer");
        }
      }
    }
    catch (CoreException ex)
    {
      throw new RuntimeException(ex);
    }

    return null;
  }
}
