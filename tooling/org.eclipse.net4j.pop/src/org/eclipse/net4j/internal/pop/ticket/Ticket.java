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
package org.eclipse.net4j.internal.pop.ticket;

import org.eclipse.net4j.internal.pop.util.Element;
import org.eclipse.net4j.pop.ticket.ITicket;

import java.text.MessageFormat;

/**
 * @author Eike Stepper
 */
public class Ticket extends Element implements ITicket
{
  private String id;

  public Ticket(String id)
  {
    this.id = id;
  }

  public String getID()
  {
    return id;
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("Ticket[id={0}]", id);
  }
}
