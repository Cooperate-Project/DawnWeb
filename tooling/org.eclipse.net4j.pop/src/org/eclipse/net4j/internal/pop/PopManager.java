/***************************************************************************
 * Copyright (c) 2004 - 2008 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.net4j.internal.pop;

import org.eclipse.net4j.internal.pop.delivery.DeliveryProxy;
import org.eclipse.net4j.internal.pop.release.ReleaseProxy;
import org.eclipse.net4j.internal.pop.util.Element;
import org.eclipse.net4j.pop.IBaseline;
import org.eclipse.net4j.pop.IIntegrationStream;
import org.eclipse.net4j.pop.IMaintenanceStream;
import org.eclipse.net4j.pop.IPop;
import org.eclipse.net4j.pop.IStream;
import org.eclipse.net4j.pop.ITaskStream;
import org.eclipse.net4j.pop.code.IBranch;
import org.eclipse.net4j.pop.delivery.IDelivery;
import org.eclipse.net4j.pop.release.IRelease;
import org.eclipse.net4j.util.ImplementationError;
import org.eclipse.net4j.util.ref.ReferenceValueMap;

import org.eclipse.mylyn.tasks.core.ITask;

import java.util.concurrent.ConcurrentMap;

/**
 * @author Eike Stepper
 */
public class PopManager extends Element implements InternalPopManager
{
  private transient ConcurrentMap<ITask, IStream> streamCache = new ReferenceValueMap.Weak<ITask, IStream>();

  public PopManager()
  {
  }

  @Override
  public String toString()
  {
    return "PopManager";
  }

  public IPop createPop(String name, IBranch branch, String taskID)
  {
    // TODO Implement PopManager.createPop(name, branch, taskID)
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public void putStream(IStream stream)
  {
    synchronized (streamCache)
    {
      ITask task = stream.getTask();
      streamCache.putIfAbsent(task, stream);
    }
  }

  public IStream getStream(ITask task)
  {
    synchronized (streamCache)
    {
      IStream stream = streamCache.get(task);
      if (stream == null)
      {
        stream = resolveStream(task);
        if (stream == null)
        {
          throw new ImplementationError("resolveStream() must not return null");
        }

        streamCache.put(task, stream);
      }

      return stream;
    }
  }

  private IStream resolveStream(ITask task)
  {
    // TODO Implement Pop.resolveStream(ticketID)
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public IMaintenanceStream resolve(MaintenanceStreamProxy proxy)
  {
    return (IMaintenanceStream)getStream(proxy.getTask());
  }

  public ITaskStream resolve(TaskStreamProxy proxy)
  {
    return (ITaskStream)getStream(proxy.getTask());
  }

  public IDelivery resolve(DeliveryProxy proxy)
  {
    ITaskStream stream = (ITaskStream)getStream(proxy.getTask());
    return stream.getDeliveryByNumber(proxy.getNumber());
  }

  public IRelease resolve(ReleaseProxy proxy)
  {
    IIntegrationStream stream = (IIntegrationStream)getStream(proxy.getTask());
    return stream.getReleaseByVersion(proxy.getVersion());
  }

  public IBaseline resolve(BaselineProxy proxy)
  {
    IStream stream = getStream(proxy.getTask());
    return stream.getBaselineByTagName(proxy.getTagName());
  }
}
