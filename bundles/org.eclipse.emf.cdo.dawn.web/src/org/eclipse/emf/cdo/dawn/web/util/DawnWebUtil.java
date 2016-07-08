/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.dawn.web.util;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Martin Fluegge
 */
public class DawnWebUtil
{
  public static String getUniqueId(EObject eObject)
  {
    CDOID cdoID = CDOUtil.getCDOObject(eObject).cdoID();

    StringBuilder builder = new StringBuilder();
    CDOIDUtil.write(builder, cdoID);
    return builder.toString();
  }

  public static EObject getObjectFromId(String idLiteral, CDOView view)
  {
    CDOID cdoId = CDOIDUtil.read(idLiteral);
    return view.getObject(cdoId);
  }
}
