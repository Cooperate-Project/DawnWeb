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
import org.eclipse.net4j.internal.pop.util.IElementProxy;
import org.eclipse.net4j.internal.pop.util.IElementResolver;
import org.eclipse.net4j.pop.IBaseline;
import org.eclipse.net4j.pop.IIntegrationStream;
import org.eclipse.net4j.pop.IPop;
import org.eclipse.net4j.pop.ITaskStream;
import org.eclipse.net4j.pop.code.IBranch;
import org.eclipse.net4j.pop.delivery.IDelivery;
import org.eclipse.net4j.pop.delivery.IMerge;
import org.eclipse.net4j.pop.ticket.ITicket;

import java.util.Date;

/**
 * @author Eike Stepper
 */
public class TaskStreamProxy extends ElementProxy<ITaskStream> implements ITaskStream
{
  private TaskStreamProxy(IPop pop, String ticketID)
  {
    super(pop, ticketID);
  }

  public TaskStreamProxy(ITaskStream taskStream)
  {
    super(taskStream.getPop(), taskStream.getTicket().getID(), taskStream);
  }

  public IElementProxy<? extends ITaskStream> copy()
  {
    return new TaskStreamProxy(getPop(), getTicketID());
  }

  public IBranch getBranch()
  {
    return getElement().getBranch();
  }

  public ITicket getTicket()
  {
    return getElement().getTicket();
  }

  public IBaseline getBaseline()
  {
    return getElement().getBaseline();
  }

  public IIntegrationStream getParent()
  {
    return getElement().getParent();
  }

  public IMerge merge(Date date, IDelivery delivery)
  {
    return getElement().merge(date, delivery);
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

  public IDelivery addDelivery(Date deliveryDate)
  {
    return getElement().addDelivery(deliveryDate);
  }

  public IDelivery[] getDeliveries()
  {
    return getElement().getDeliveries();
  }

  public IDelivery getDelivery(int index)
  {
    return getElement().getDelivery(index);
  }

  public int getDeliveryCount()
  {
    return getElement().getDeliveryCount();
  }

  @Override
  protected ITaskStream resolve()
  {
    return ((IElementResolver)getPop()).resolve(this);
  }
}