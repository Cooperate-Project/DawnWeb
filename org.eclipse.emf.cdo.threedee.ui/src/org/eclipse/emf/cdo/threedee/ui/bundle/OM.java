/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.threedee.ui.bundle;

import org.eclipse.net4j.util.om.OMBundle;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.OMLogger;
import org.eclipse.net4j.util.om.trace.OMTracer;
import org.eclipse.net4j.util.ui.UIActivator;

/**
 * The <em>Operations & Maintenance</em> class of this bundle.
 * 
 * @author Martin Fluegge
 */
public abstract class OM
{
  public static final String BUNDLE_ID = "org.eclipse.emf.cdo.threedee.ui"; //$NON-NLS-1$

  public static final OMBundle BUNDLE = OMPlatform.INSTANCE.bundle(BUNDLE_ID, OM.class);

  public static final OMTracer DEBUG = BUNDLE.tracer("debug"); //$NON-NLS-1$

  public static final OMLogger LOG = BUNDLE.logger();

  /**
   * @author Eike Stepper
   */
  public static final class Activator extends UIActivator
  {
    public static Activator INSTANCE;

    public Activator()
    {
      super(BUNDLE);
      INSTANCE = this;
    }
  }
}
