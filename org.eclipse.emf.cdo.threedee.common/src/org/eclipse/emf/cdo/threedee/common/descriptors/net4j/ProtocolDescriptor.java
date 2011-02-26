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
package org.eclipse.emf.cdo.threedee.common.descriptors.net4j;

import org.eclipse.emf.cdo.threedee.common.Element;
import org.eclipse.emf.cdo.threedee.common.ElementDescriptor;

import org.eclipse.spi.net4j.Protocol;

/**
 * @author Eike Stepper
 */
public class ProtocolDescriptor extends ElementDescriptor
{
  @Override
  public Class<?> getType()
  {
    return Protocol.class;
  }

  @Override
  public void initElement(Object object, Element element)
  {
    Protocol<?> protocol = (Protocol<?>)object;
    element.setNameAttribute(protocol.getType());
    element.setAttribute("location", protocol.getLocation());
    element.setAttribute("user", protocol.getUserID());
    // element.addReference(false, protocol.getChannel());
    element.addReference(false, protocol.getInfraStructure());
  }
}
