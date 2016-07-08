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

import org.eclipse.net4j.util.ObjectUtil;

import org.eclipse.emf.common.util.URI;

/**
 * @author Martin Fluegge
 */
public class RegistryKey
{
  private URI uri;

  private String httpSessionId;

  public RegistryKey(URI uri, String httpSessionId)
  {
    this.uri = uri;
    this.httpSessionId = httpSessionId;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }

    if (obj instanceof RegistryKey)
    {
      RegistryKey registryKey = (RegistryKey)obj;
      if (getUri().equals(registryKey.getUri()) && getHttpSessionId().equals(registryKey.getHttpSessionId()))
      {
        return true;
      }
    }

    return super.equals(obj);
  }

  @Override
  public int hashCode()
  {
    return ObjectUtil.hashCode(httpSessionId) ^ ObjectUtil.hashCode(uri);
  }

  public URI getUri()
  {
    return uri;
  }

  public String getHttpSessionId()
  {
    return httpSessionId;
  }

}
