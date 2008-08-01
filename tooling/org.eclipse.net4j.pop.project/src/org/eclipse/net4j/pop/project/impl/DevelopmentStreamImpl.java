/**
 * Copyright (c) 2004 - 2008 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *
 * $Id: DevelopmentStreamImpl.java,v 1.5 2008-08-01 09:36:25 estepper Exp $
 */
package org.eclipse.net4j.pop.project.impl;

import org.eclipse.net4j.pop.project.DevelopmentStream;
import org.eclipse.net4j.pop.project.MaintenanceStream;
import org.eclipse.net4j.pop.project.ProjectPackage;
import org.eclipse.net4j.pop.project.Release;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;
import java.util.Date;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Development Stream</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.net4j.pop.project.impl.DevelopmentStreamImpl#getMaintenanceStreams <em>Maintenance Streams
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class DevelopmentStreamImpl extends IntegrationStreamImpl implements DevelopmentStream
{
  /**
   * The cached value of the '{@link #getMaintenanceStreams() <em>Maintenance Streams</em>}' reference list. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getMaintenanceStreams()
   * @generated
   * @ordered
   */
  protected EList<MaintenanceStream> maintenanceStreams;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected DevelopmentStreamImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return ProjectPackage.Literals.DEVELOPMENT_STREAM;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EList<MaintenanceStream> getMaintenanceStreams()
  {
    if (maintenanceStreams == null)
    {
      maintenanceStreams = new EObjectWithInverseResolvingEList<MaintenanceStream>(MaintenanceStream.class, this,
          ProjectPackage.DEVELOPMENT_STREAM__MAINTENANCE_STREAMS, ProjectPackage.MAINTENANCE_STREAM__PARENT);
    }
    return maintenanceStreams;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public Release addRelease(Date date, boolean compatible, int increment)
  {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
    case ProjectPackage.DEVELOPMENT_STREAM__MAINTENANCE_STREAMS:
      return ((InternalEList<InternalEObject>)(InternalEList<?>)getMaintenanceStreams()).basicAdd(otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
    case ProjectPackage.DEVELOPMENT_STREAM__MAINTENANCE_STREAMS:
      return ((InternalEList<?>)getMaintenanceStreams()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
    case ProjectPackage.DEVELOPMENT_STREAM__MAINTENANCE_STREAMS:
      return getMaintenanceStreams();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
    case ProjectPackage.DEVELOPMENT_STREAM__MAINTENANCE_STREAMS:
      getMaintenanceStreams().clear();
      getMaintenanceStreams().addAll((Collection<? extends MaintenanceStream>)newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
    case ProjectPackage.DEVELOPMENT_STREAM__MAINTENANCE_STREAMS:
      getMaintenanceStreams().clear();
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
    case ProjectPackage.DEVELOPMENT_STREAM__MAINTENANCE_STREAMS:
      return maintenanceStreams != null && !maintenanceStreams.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} // DevelopmentStreamImpl