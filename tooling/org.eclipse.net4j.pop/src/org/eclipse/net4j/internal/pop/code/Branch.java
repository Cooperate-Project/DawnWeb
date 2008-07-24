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

import org.eclipse.net4j.internal.pop.Element;
import org.eclipse.net4j.pop.code.IBranch;
import org.eclipse.net4j.pop.code.IBranchPoint;

/**
 * @author Eike Stepper
 */
public abstract class Branch extends Element implements IBranch
{
  private String branchName;

  private IBranchPoint baseline;

  public Branch(String branchName, IBranchPoint baseline)
  {
    checkArgument(branchName, "branchName");
    checkArgument(baseline, "baseline");
    this.baseline = baseline;
    this.branchName = branchName;
  }

  public String getBranchName()
  {
    return branchName;
  }

  public IBranchPoint getBaseline()
  {
    return baseline;
  }
}