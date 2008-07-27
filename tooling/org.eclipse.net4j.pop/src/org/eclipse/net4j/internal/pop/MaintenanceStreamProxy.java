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

import org.eclipse.net4j.internal.pop.util.ElementProxy;
import org.eclipse.net4j.internal.pop.util.IElementResolver;
import org.eclipse.net4j.pop.IBaseline;
import org.eclipse.net4j.pop.IDevelopmentStream;
import org.eclipse.net4j.pop.IMaintenanceStream;
import org.eclipse.net4j.pop.IPop;
import org.eclipse.net4j.pop.IPopManager;
import org.eclipse.net4j.pop.ITaskStream;
import org.eclipse.net4j.pop.code.IBranch;
import org.eclipse.net4j.pop.delivery.IDelivery;
import org.eclipse.net4j.pop.delivery.IMerge;
import org.eclipse.net4j.pop.release.IRelease;
import org.eclipse.net4j.pop.release.IVersion;

import org.eclipse.mylyn.tasks.core.ITask;

import java.util.Date;

/**
 * @author Eike Stepper
 */
public class MaintenanceStreamProxy extends ElementProxy<IMaintenanceStream> implements IMaintenanceStream
{
  private MaintenanceStreamProxy(IPopManager manager, ITask task)
  {
    super(manager, task);
  }

  private MaintenanceStreamProxy(IMaintenanceStream maintenanceStream)
  {
    super(maintenanceStream.getPop().getManager(), maintenanceStream.getTask(), maintenanceStream);
  }

  public static MaintenanceStreamProxy proxy(IMaintenanceStream maintenanceStream)
  {
    if (maintenanceStream instanceof MaintenanceStreamProxy)
    {
      return ((MaintenanceStreamProxy)maintenanceStream).copy();
    }

    return new MaintenanceStreamProxy(maintenanceStream);
  }

  public MaintenanceStreamProxy copy()
  {
    return new MaintenanceStreamProxy(getManager(), getTask());
  }

  public IPop getPop()
  {
    return getElement().getPop();
  }

  public IBranch getBranch()
  {
    return getElement().getBranch();
  }

  public IRelease getBaseline()
  {
    return getElement().getBaseline();
  }

  public IDevelopmentStream getParent()
  {
    return getElement().getParent();
  }

  public IMerge addMerge(Date date, IDelivery delivery)
  {
    return getElement().addMerge(date, delivery);
  }

  public IMerge getMerge(int index)
  {
    return getElement().getMerge(index);
  }

  public int getMergeCount()
  {
    return getElement().getMergeCount();
  }

  public IMerge[] getMerges()
  {
    return getElement().getMerges();
  }

  public IRelease addRelease(Date date)
  {
    return getElement().addRelease(date);
  }

  public IRelease getRelease(int index)
  {
    return getElement().getRelease(index);
  }

  public int getReleaseCount()
  {
    return getElement().getReleaseCount();
  }

  public IRelease getReleaseByVersion(IVersion version)
  {
    return getElement().getReleaseByVersion(version);
  }

  public IRelease[] getReleases()
  {
    return getElement().getReleases();
  }

  public ITaskStream addTaskStream(IBaseline baseline, ITask task)
  {
    return getElement().addTaskStream(baseline, task);
  }

  public ITaskStream addTaskStream(String tagName, ITask task)
  {
    return getElement().addTaskStream(tagName, task);
  }

  public ITaskStream getTaskStream(int index)
  {
    return getElement().getTaskStream(index);
  }

  public int getTaskStreamCount()
  {
    return getElement().getTaskStreamCount();
  }

  public ITaskStream[] getTaskStreams()
  {
    return getElement().getTaskStreams();
  }

  public IBaseline addBaseline(IBaseline baseline)
  {
    return getElement().addBaseline(baseline);
  }

  public IBaseline addBaseline(String tagName)
  {
    return getElement().addBaseline(tagName);
  }

  public IBaseline getBaseline(int index)
  {
    return getElement().getBaseline(index);
  }

  public int getBaselineCount()
  {
    return getElement().getBaselineCount();
  }

  public IBaseline getBaselineByTagName(String tagName)
  {
    return getElement().getBaselineByTagName(tagName);
  }

  public IBaseline[] getBaselines()
  {
    return getElement().getBaselines();
  }

  @Override
  protected IMaintenanceStream resolve()
  {
    return ((IElementResolver)getPop()).resolve(this);
  }
}
