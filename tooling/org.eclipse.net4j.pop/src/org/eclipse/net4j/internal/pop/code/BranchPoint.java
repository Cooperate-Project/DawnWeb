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
package org.eclipse.net4j.internal.pop.code;

import org.eclipse.net4j.internal.pop.AbstractElement;
import org.eclipse.net4j.pop.code.IBranch;
import org.eclipse.net4j.pop.code.IBranchPoint;

import java.util.Date;

/**
 * @author Eike Stepper
 */
public class BranchPoint extends AbstractElement implements IBranchPoint
{
  private IBranch branch;

  private Date date;

  public BranchPoint(IBranch branch, Date date)
  {
    checkArgument(branch, "branch");
    checkArgument(date, "date");
    this.branch = branch;
    this.date = date;
  }

  public BranchPoint(Branch branch)
  {
    this(branch, UNKNOWN);
  }

  public IBranch getBranch()
  {
    return branch;
  }

  public Date getDate()
  {
    return date;
  }

  public void setDate(Date date)
  {
    this.date = date;
  }

  public int compareTo(IBranchPoint o)
  {
    return date.compareTo(o.getDate());
  }
}
