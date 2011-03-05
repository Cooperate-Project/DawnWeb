/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.threedee.common.descriptors.cdo.client;

import org.eclipse.emf.cdo.threedee.common.Element;
import org.eclipse.emf.cdo.threedee.common.descriptors.cdo.CDODescriptor;

/**
 * @author Eike Stepper
 */
public class CDOClientDescriptor extends CDODescriptor
{
  @Override
  public Class<?> getElementType()
  {
    return FOLDER_TYPE;
  }

  @Override
  public void initElement(Object object, Element element)
  {
  }

  @Override
  public String getLabel()
  {
    return getClass() != CDOClientDescriptor.class ? getBaseLabel() : "Client";
  }
}
