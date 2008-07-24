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
package org.eclipse.net4j.pop.ticket;

import org.eclipse.net4j.pop.IElement;
import org.eclipse.net4j.pop.stream.IStream;

/**
 * @author Eike Stepper
 */
public interface ITicket extends IElement
{
  public IStream getStream();

  public String getTicketID();
}