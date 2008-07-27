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
package org.eclipse.net4j.internal.pop.release;

import org.eclipse.net4j.internal.pop.InternalPopManager;
import org.eclipse.net4j.internal.pop.util.ElementProxy;
import org.eclipse.net4j.pop.IIntegrationStream;
import org.eclipse.net4j.pop.code.ITag;
import org.eclipse.net4j.pop.release.IMilestone;
import org.eclipse.net4j.pop.release.IRelease;
import org.eclipse.net4j.pop.release.IVersion;

import org.eclipse.mylyn.tasks.core.ITask;

import java.util.Date;

/**
 * @author Eike Stepper
 */
public class ReleaseProxy extends ElementProxy<IRelease> implements IRelease
{
  private IVersion version;

  private ReleaseProxy(InternalPopManager manager, ITask task, IVersion version)
  {
    super(manager, task);
    this.version = version;
  }

  private ReleaseProxy(IRelease release)
  {
    super((InternalPopManager)release.getStream().getPop().getManager(), release.getStream().getTask(), release);
    version = release.getVersion();
  }

  public static ReleaseProxy proxy(IRelease release)
  {
    if (release instanceof ReleaseProxy)
    {
      return ((ReleaseProxy)release).copy();
    }

    return new ReleaseProxy(release);
  }

  public ReleaseProxy copy()
  {
    return new ReleaseProxy(getManager(), getTask(), version);
  }

  public IVersion getVersion()
  {
    return version;
  }

  public IIntegrationStream getStream()
  {
    return getElement().getStream();
  }

  public ITag getTag()
  {
    return getElement().getTag();
  }

  public Date getDate()
  {
    return getElement().getDate();
  }

  public IMilestone addMilestone(String name, Date date)
  {
    return getElement().addMilestone(name, date);
  }

  public IMilestone getMilestone(int index)
  {
    return getElement().getMilestone(index);
  }

  public int getMilestoneCount()
  {
    return getElement().getMilestoneCount();
  }

  public IMilestone[] getMilestones()
  {
    return getElement().getMilestones();
  }

  @Override
  protected IRelease resolve()
  {
    return getManager().resolve(this);
  }
}
