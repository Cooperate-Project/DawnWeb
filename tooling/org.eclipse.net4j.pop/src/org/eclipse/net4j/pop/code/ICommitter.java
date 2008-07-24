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
package org.eclipse.net4j.pop.code;

import org.eclipse.net4j.pop.IElement;
import org.eclipse.net4j.pop.ticket.ITicketUser;

import java.util.Date;

/**
 * @author Eike Stepper
 */
public interface ICommitter extends ITicketUser
{
  public String getCodeAccount();

  public Date getEntryDate();

  public Date getExitDate();

  /**
   * @author Eike Stepper
   */
  public interface Container extends IElement
  {
    public int getCommitterCount();

    public ICommitter getCommitter(int index);

    public ICommitter[] getCommitters();
  }
}
