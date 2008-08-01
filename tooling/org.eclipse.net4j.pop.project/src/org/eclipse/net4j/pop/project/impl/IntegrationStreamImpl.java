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
 * $Id: IntegrationStreamImpl.java,v 1.8 2008-08-01 18:29:00 estepper Exp $
 */
package org.eclipse.net4j.pop.project.impl;

import org.eclipse.net4j.pop.project.IntegrationStream;
import org.eclipse.net4j.pop.project.ProjectPackage;
import org.eclipse.net4j.pop.project.Release;
import org.eclipse.net4j.pop.project.TaskStream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;
import java.util.Date;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Integration Stream</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.net4j.pop.project.impl.IntegrationStreamImpl#getTaskStreams <em>Task Streams</em>}</li>
 * <li>{@link org.eclipse.net4j.pop.project.impl.IntegrationStreamImpl#getReleases <em>Releases</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class IntegrationStreamImpl extends StreamImpl implements IntegrationStream
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getTaskStreams() <em>Task Streams</em>}' containment reference list. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getTaskStreams()
   * @generated
   * @ordered
   */
  protected EList<TaskStream> taskStreams;

  /**
   * The cached value of the '{@link #getReleases() <em>Releases</em>}' containment reference list. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @see #getReleases()
   * @generated
   * @ordered
   */
  protected EList<Release> releases;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected IntegrationStreamImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return ProjectPackage.Literals.INTEGRATION_STREAM;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ProjectPackage.INTEGRATION_STREAM__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EList<TaskStream> getTaskStreams()
  {
    if (taskStreams == null)
    {
      taskStreams = new EObjectContainmentWithInverseEList.Resolving<TaskStream>(TaskStream.class, this,
          ProjectPackage.INTEGRATION_STREAM__TASK_STREAMS, ProjectPackage.TASK_STREAM__PARENT);
    }
    return taskStreams;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EList<Release> getReleases()
  {
    if (releases == null)
    {
      releases = new EObjectContainmentWithInverseEList.Resolving<Release>(Release.class, this,
          ProjectPackage.INTEGRATION_STREAM__RELEASES, ProjectPackage.RELEASE__STREAM);
    }
    return releases;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public TaskStream startTask(String taskId, Date baseline)
  {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public Release addRelease(Date date)
  {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
    case ProjectPackage.INTEGRATION_STREAM__TASK_STREAMS:
      return ((InternalEList<InternalEObject>)(InternalEList<?>)getTaskStreams()).basicAdd(otherEnd, msgs);
    case ProjectPackage.INTEGRATION_STREAM__RELEASES:
      return ((InternalEList<InternalEObject>)(InternalEList<?>)getReleases()).basicAdd(otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
    case ProjectPackage.INTEGRATION_STREAM__TASK_STREAMS:
      return ((InternalEList<?>)getTaskStreams()).basicRemove(otherEnd, msgs);
    case ProjectPackage.INTEGRATION_STREAM__RELEASES:
      return ((InternalEList<?>)getReleases()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
    case ProjectPackage.INTEGRATION_STREAM__NAME:
      return getName();
    case ProjectPackage.INTEGRATION_STREAM__TASK_STREAMS:
      return getTaskStreams();
    case ProjectPackage.INTEGRATION_STREAM__RELEASES:
      return getReleases();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
    case ProjectPackage.INTEGRATION_STREAM__NAME:
      setName((String)newValue);
      return;
    case ProjectPackage.INTEGRATION_STREAM__TASK_STREAMS:
      getTaskStreams().clear();
      getTaskStreams().addAll((Collection<? extends TaskStream>)newValue);
      return;
    case ProjectPackage.INTEGRATION_STREAM__RELEASES:
      getReleases().clear();
      getReleases().addAll((Collection<? extends Release>)newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
    case ProjectPackage.INTEGRATION_STREAM__NAME:
      setName(NAME_EDEFAULT);
      return;
    case ProjectPackage.INTEGRATION_STREAM__TASK_STREAMS:
      getTaskStreams().clear();
      return;
    case ProjectPackage.INTEGRATION_STREAM__RELEASES:
      getReleases().clear();
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
    case ProjectPackage.INTEGRATION_STREAM__NAME:
      return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
    case ProjectPackage.INTEGRATION_STREAM__TASK_STREAMS:
      return taskStreams != null && !taskStreams.isEmpty();
    case ProjectPackage.INTEGRATION_STREAM__RELEASES:
      return releases != null && !releases.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: "); //$NON-NLS-1$
    result.append(name);
    result.append(')');
    return result.toString();
  }

  /**
   * @ADDED
   */
  @Override
  public String getIdValue()
  {
    return getName();
  }
} // IntegrationStreamImpl
