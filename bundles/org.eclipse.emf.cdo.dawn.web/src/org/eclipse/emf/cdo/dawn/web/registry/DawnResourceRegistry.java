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

import org.eclipse.emf.cdo.dawn.util.connection.CDOConnectionUtil;
import org.eclipse.emf.cdo.dawn.web.DawnServerConfig;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.cdo.view.CDOViewInvalidationEvent;

import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;

import org.eclipse.emf.common.util.URI;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Fluegge
 */
public class DawnResourceRegistry
{

  public static DawnResourceRegistry instance = new DawnResourceRegistry();

  private Map<RegistryKey, ResourceInfo> resourcesInfos = new HashMap<RegistryKey, ResourceInfo>();

  private DawnResourceRegistry()
  {
  }

  public CDOResource getResource(URI uri, String httpSessionId, boolean create)
  {
    final RegistryKey key = new RegistryKey(uri, httpSessionId);
    ResourceInfo resourceInfo = getResourceInfo(key);
    CDOResource resource = null;

    if (resourceInfo == null && create)
    {
      resource = createResource(uri, key);
    }
    else
    {
      resource = resourceInfo.getResource();
    }
    return resource;
  }

  public CDOResource getResource(URI uri, String httpSessionId)
  {
    return getResource(uri, httpSessionId, true);
  }

  private CDOResource createResource(URI uri, final RegistryKey key)
  {
    ResourceInfo resourceInfo;
    CDOResource resource;
    CDOConnectionUtil.instance.init(DawnServerConfig.CDO_REPO, "tcp", DawnServerConfig.CDO_HOST);
    CDOSession session = CDOConnectionUtil.instance.openSession();
    CDOView view = session.openTransaction();

    view.addListener(new IListener()
    {
      public void notifyEvent(IEvent event)
      {
        if (event instanceof CDOViewInvalidationEvent)
        {
          DawnResourceRegistry.instance.setResourceChanged(key);
        }
      }
    });

    resource = view.getResource(uri.path());
    resourceInfo = new ResourceInfo();
    resourceInfo.setResource(resource);
    resourceInfo.setLastChanged(new Date().getTime());
    resourcesInfos.put(key, resourceInfo);
    return resource;
  }

  public void setResourceChanged(RegistryKey key)
  {
    getResourceInfo(key).setLastChanged(new Date().getTime());
  }

  public void setResourceChanged(URI uri, String httpSessionId)
  {
    RegistryKey key = new RegistryKey(uri, httpSessionId);
    setResourceChanged(key);
  }

  public long getLastChanged(URI uri, String httpSessionId)
  {
    RegistryKey key = new RegistryKey(uri, httpSessionId);
    return getLastChanged(key);
  }

  public long getLastChanged(RegistryKey key)
  {
    return getResourceInfo(key).getLastChanged();
  }

  private ResourceInfo getResourceInfo(RegistryKey key)
  {
    return resourcesInfos.get(key);
  }

}
