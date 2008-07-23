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
package org.eclipse.net4j.pop.delivery;

import org.eclipse.core.runtime.IAdaptable;

import java.util.Date;

/**
 * @author Eike Stepper
 */
public interface IMerge extends IAdaptable
{
  public Date getDate();

  public IDelivery getDelivery();

  public IMerge.Container getMergeContainer();

  /**
   * @author Eike Stepper
   */
  public interface Container extends IAdaptable
  {
    public int getMergeCount();

    public IMerge getMerge(int index);

    public IMerge[] getMerges();
  }

  /**
   * @author Eike Stepper
   */
  public interface Producer extends Container
  {
    public IMerge addMerge(IDelivery delivery, Date date);
  }
}
