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
 * $Id: ProductFactory.java,v 1.3 2008-08-01 08:14:45 estepper Exp $
 */
package org.eclipse.net4j.pop.product;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.net4j.pop.product.ProductPackage
 * @generated
 */
public interface ProductFactory extends EFactory
{
  /**
   * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  ProductFactory eINSTANCE = org.eclipse.net4j.pop.product.impl.ProductFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Pop Product</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Pop Product</em>'.
   * @generated
   */
  PopProduct createPopProduct();

  /**
   * Returns a new object of class '<em>Working Set</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Working Set</em>'.
   * @generated
   */
  WorkingSet createWorkingSet();

  /**
   * Returns a new object of class '<em>Workspace Project</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Workspace Project</em>'.
   * @generated
   */
  WorkspaceProject createWorkspaceProject();

  /**
   * Returns a new object of class '<em>File</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>File</em>'.
   * @generated
   */
  File createFile();

  /**
   * Returns a new object of class '<em>Folder</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Folder</em>'.
   * @generated
   */
  Folder createFolder();

  /**
   * Returns a new object of class '<em>Archive</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Archive</em>'.
   * @generated
   */
  Archive createArchive();

  /**
   * Returns a new object of class '<em>Archive Content</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return a new object of class '<em>Archive Content</em>'.
   * @generated
   */
  ArchiveContent createArchiveContent();

  /**
   * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the package supported by this factory.
   * @generated
   */
  ProductPackage getProductPackage();

} // ProductFactory