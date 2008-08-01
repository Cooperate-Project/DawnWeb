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
 * $Id: BasePackage.java,v 1.3 2008-08-01 08:34:08 estepper Exp $
 */
package org.eclipse.net4j.pop.base;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.net4j.pop.base.BaseFactory
 * @model kind="package"
 * @generated
 */
public interface BasePackage extends EPackage
{
  /**
   * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNAME = "base"; //$NON-NLS-1$

  /**
   * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/pop/base/1.0.0"; //$NON-NLS-1$

  /**
   * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNS_PREFIX = "pop.base"; //$NON-NLS-1$

  /**
   * The package content type ID. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eCONTENT_TYPE = "pop.base"; //$NON-NLS-1$

  /**
   * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  BasePackage eINSTANCE = org.eclipse.net4j.pop.base.impl.BasePackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.net4j.pop.base.Identifiable <em>Identifiable</em>}' class. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.net4j.pop.base.Identifiable
   * @see org.eclipse.net4j.pop.base.impl.BasePackageImpl#getIdentifiable()
   * @generated
   */
  int IDENTIFIABLE = 0;

  /**
   * The number of structural features of the '<em>Identifiable</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   * @ordered
   */
  int IDENTIFIABLE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.eclipse.net4j.pop.base.Displayable <em>Displayable</em>}' class. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.net4j.pop.base.Displayable
   * @see org.eclipse.net4j.pop.base.impl.BasePackageImpl#getDisplayable()
   * @generated
   */
  int DISPLAYABLE = 1;

  /**
   * The number of structural features of the '<em>Displayable</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   * @ordered
   */
  int DISPLAYABLE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.eclipse.net4j.pop.base.impl.PopElementImpl <em>Pop Element</em>}' class.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.net4j.pop.base.impl.PopElementImpl
   * @see org.eclipse.net4j.pop.base.impl.BasePackageImpl#getPopElement()
   * @generated
   */
  int POP_ELEMENT = 2;

  /**
   * The number of structural features of the '<em>Pop Element</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   * @ordered
   */
  int POP_ELEMENT_FEATURE_COUNT = IDENTIFIABLE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '<em>Version</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.net4j.pop.base.Version
   * @see org.eclipse.net4j.pop.base.impl.BasePackageImpl#getVersion()
   * @generated
   */
  int VERSION = 3;

  /**
   * Returns the meta object for class '{@link org.eclipse.net4j.pop.base.Identifiable <em>Identifiable</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Identifiable</em>'.
   * @see org.eclipse.net4j.pop.base.Identifiable
   * @generated
   */
  EClass getIdentifiable();

  /**
   * Returns the meta object for class '{@link org.eclipse.net4j.pop.base.Displayable <em>Displayable</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Displayable</em>'.
   * @see org.eclipse.net4j.pop.base.Displayable
   * @generated
   */
  EClass getDisplayable();

  /**
   * Returns the meta object for class '{@link org.eclipse.net4j.pop.base.PopElement <em>Pop Element</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Pop Element</em>'.
   * @see org.eclipse.net4j.pop.base.PopElement
   * @generated
   */
  EClass getPopElement();

  /**
   * Returns the meta object for data type '{@link org.eclipse.net4j.pop.base.Version <em>Version</em>}'. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for data type '<em>Version</em>'.
   * @see org.eclipse.net4j.pop.base.Version
   * @model instanceClass="org.eclipse.net4j.pop.base.Version"
   * @generated
   */
  EDataType getVersion();

  /**
   * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the factory that creates the instances of the model.
   * @generated
   */
  BaseFactory getBaseFactory();

  /**
   * <!-- begin-user-doc --> Defines literals for the meta objects that represent
   * <ul>
   * <li>each class,</li>
   * <li>each feature of each class,</li>
   * <li>each enum,</li>
   * <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * 
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.eclipse.net4j.pop.base.Identifiable <em>Identifiable</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.net4j.pop.base.Identifiable
     * @see org.eclipse.net4j.pop.base.impl.BasePackageImpl#getIdentifiable()
     * @generated
     */
    EClass IDENTIFIABLE = eINSTANCE.getIdentifiable();

    /**
     * The meta object literal for the '{@link org.eclipse.net4j.pop.base.Displayable <em>Displayable</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.net4j.pop.base.Displayable
     * @see org.eclipse.net4j.pop.base.impl.BasePackageImpl#getDisplayable()
     * @generated
     */
    EClass DISPLAYABLE = eINSTANCE.getDisplayable();

    /**
     * The meta object literal for the '{@link org.eclipse.net4j.pop.base.impl.PopElementImpl <em>Pop Element</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.net4j.pop.base.impl.PopElementImpl
     * @see org.eclipse.net4j.pop.base.impl.BasePackageImpl#getPopElement()
     * @generated
     */
    EClass POP_ELEMENT = eINSTANCE.getPopElement();

    /**
     * The meta object literal for the '<em>Version</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.net4j.pop.base.Version
     * @see org.eclipse.net4j.pop.base.impl.BasePackageImpl#getVersion()
     * @generated
     */
    EDataType VERSION = eINSTANCE.getVersion();

  }

} // BasePackage