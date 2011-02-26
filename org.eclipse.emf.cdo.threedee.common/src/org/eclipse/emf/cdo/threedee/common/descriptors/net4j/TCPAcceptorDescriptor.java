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

/**
 * @author Eike Stepper
 */
@SuppressWarnings("restriction")
public class TCPAcceptorDescriptor extends AcceptorDescriptor
{
  @Override
  public Class<?> getType()
  {
    return org.eclipse.net4j.internal.tcp.TCPAcceptor.class;
  }

  @Override
  public void initElement(Object object, Element element)
  {
    super.initElement(object, element);

    org.eclipse.net4j.internal.tcp.TCPAcceptor acceptor = (org.eclipse.net4j.internal.tcp.TCPAcceptor)object;
    element.addReference(false, acceptor.getSelector());
  }
}