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
package org.eclipse.net4j.pop.util;

import org.eclipse.net4j.internal.pop.Pop;
import org.eclipse.net4j.internal.pop.code.DefaultCodeStrategy;
import org.eclipse.net4j.internal.pop.code.MainBranch;
import org.eclipse.net4j.internal.pop.ticket.Ticket;
import org.eclipse.net4j.internal.pop.ticket.TicketUser;
import org.eclipse.net4j.pop.IPop;
import org.eclipse.net4j.pop.code.IBranch;
import org.eclipse.net4j.pop.code.ICodeStrategy;
import org.eclipse.net4j.pop.ticket.ITicket;
import org.eclipse.net4j.pop.ticket.ITicketUser;

import java.util.Date;

/**
 * @author Eike Stepper
 */
public final class PopUtil
{
  private PopUtil()
  {
  }

  public static ITicket createTicket(String ticketID)
  {
    return new Ticket(ticketID);
  }

  public static ITicketUser createTicketUser(String name, String email, String ticketAccount)
  {
    return new TicketUser(name, email, ticketAccount);
  }

  public static ITicketUser createTicketUser(String name, String email)
  {
    return new TicketUser(name, email, email);
  }

  public static IBranch createMainBranch(String name, Date startDate)
  {
    return new MainBranch(name, startDate);
  }

  public static IPop createPop(String name, IBranch branch, ITicket ticket)
  {
    return createPop(name, branch, ticket, new DefaultCodeStrategy());
  }

  public static IPop createPop(String name, IBranch branch, ITicket ticket, ICodeStrategy strategy)
  {
    return new Pop(name, strategy, branch, ticket);
  }
}
