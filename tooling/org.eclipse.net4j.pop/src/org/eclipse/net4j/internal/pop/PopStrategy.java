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

import org.eclipse.net4j.internal.pop.util.Element;
import org.eclipse.net4j.pop.IIntegrationStream;
import org.eclipse.net4j.pop.IStreamBaseline;
import org.eclipse.net4j.pop.IPop.Strategy;
import org.eclipse.net4j.pop.code.IBranch;
import org.eclipse.net4j.pop.code.ITag;
import org.eclipse.net4j.pop.release.IRelease;
import org.eclipse.net4j.pop.release.IVersion;
import org.eclipse.net4j.pop.ticket.ITicket;

import java.util.Date;

/**
 * @author Eike Stepper
 */
public class PopStrategy extends Element implements Strategy
{
  public PopStrategy()
  {
  }

  public IBranch createMaintenanceBranch(IRelease baseline, ITicket ticket)
  {
    return null;
  }

  public IBranch createTaskBranch(IStreamBaseline baseline, ITicket ticket)
  {
    return null;
  }

  public IStreamBaseline createTaskBaseline(Date baselineDate, ITicket ticket)
  {
    return null;
  }

  public ITag createMilestoneTag(IRelease release, String name)
  {
    return null;
  }

  public ITag createReleaseTag(IIntegrationStream stream, IVersion version)
  {
    return null;
  }

  public Date getTagDate(ITag tag)
  {
    return null;
  }

  @Override
  public String toString()
  {
    return "DEFAULT";
  }
}
