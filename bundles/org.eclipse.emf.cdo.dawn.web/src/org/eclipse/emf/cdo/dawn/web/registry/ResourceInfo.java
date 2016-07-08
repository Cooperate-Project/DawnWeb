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
package org.eclipse.emf.cdo.dawn.web.registry;

import org.eclipse.emf.cdo.eresource.CDOResource;

/**
 * @author Martin Fluegge
 */
public class ResourceInfo
{

  private CDOResource resource;

  private long lastChanged;

  public void setResource(CDOResource resource)
  {
    this.resource = resource;
  }

  public CDOResource getResource()
  {
    return resource;
  }

  public void setLastChanged(long lastChanged)
  {
    this.lastChanged = lastChanged;
  }

  public long getLastChanged()
  {
    return lastChanged;
  }

}
