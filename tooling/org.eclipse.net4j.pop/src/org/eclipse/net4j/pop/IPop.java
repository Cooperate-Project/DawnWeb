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
package org.eclipse.net4j.pop;

import org.eclipse.net4j.pop.stream.IDevelopmentStream;

/**
 * Represents a <em>point of process</em>, a concept similar to a <em>project</em>.
 * <p>
 * 
 * @author Eike Stepper
 */
public interface IPop extends IDevelopmentStream
{
  public String getName();

  public ICommitter addCommitter(String name, String email, String ticketAccount, String codeAccount);

  public ICommitter[] getCommitters();
}