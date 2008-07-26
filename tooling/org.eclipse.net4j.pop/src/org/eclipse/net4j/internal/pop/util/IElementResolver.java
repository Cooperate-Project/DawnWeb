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
package org.eclipse.net4j.internal.pop.util;

import org.eclipse.net4j.internal.pop.MaintenanceStreamProxy;
import org.eclipse.net4j.internal.pop.TaskStreamProxy;
import org.eclipse.net4j.pop.IMaintenanceStream;
import org.eclipse.net4j.pop.ITaskStream;

/**
 * @author Eike Stepper
 */
public interface IElementResolver
{
  public IMaintenanceStream resolve(MaintenanceStreamProxy proxy);

  public ITaskStream resolve(TaskStreamProxy proxy);
}
