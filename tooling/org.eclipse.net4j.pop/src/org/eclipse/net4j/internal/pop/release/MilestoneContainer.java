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

import org.eclipse.net4j.internal.pop.Element;
import org.eclipse.net4j.internal.pop.ElementContainer;
import org.eclipse.net4j.pop.release.IMilestone;
import org.eclipse.net4j.pop.release.IMilestone.Container;

/**
 * @author Eike Stepper
 */
public class MilestoneContainer extends ElementContainer<IMilestone> implements IMilestone.Container
{
  public MilestoneContainer(Element delegator)
  {
    super(delegator);
  }

  public IMilestone addMilestone(String name)
  {
    IMilestone milestone = new Milestone((Container)getDelegator(), name);
    addElement(milestone);
    return milestone;
  }

  public IMilestone getMilestone(int index)
  {
    return null;
  }

  public int getMilestoneCount()
  {
    return 0;
  }

  public IMilestone[] getMilestones()
  {
    return null;
  }
}
