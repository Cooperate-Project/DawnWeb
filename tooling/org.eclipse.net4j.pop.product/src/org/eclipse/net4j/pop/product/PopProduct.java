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
 * $Id: PopProduct.java,v 1.3 2008-08-01 08:14:44 estepper Exp $
 */
package org.eclipse.net4j.pop.product;

import org.eclipse.net4j.pop.project.PopProject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Pop Product</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.net4j.pop.product.PopProduct#getPopProject <em>Pop Project</em>}</li>
 * <li>{@link org.eclipse.net4j.pop.product.PopProduct#getProjects <em>Projects</em>}</li>
 * <li>{@link org.eclipse.net4j.pop.product.PopProduct#getWorkingSets <em>Working Sets</em>}</li>
 * <li>{@link org.eclipse.net4j.pop.product.PopProduct#getConfigurators <em>Configurators</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.net4j.pop.product.ProductPackage#getPopProduct()
 * @model
 * @generated
 */
public interface PopProduct extends EObject
{
  /**
   * Returns the value of the '<em><b>Pop Project</b></em>' reference. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pop Project</em>' reference isn't clear, there really should be more of a description
   * here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Pop Project</em>' reference.
   * @see #setPopProject(PopProject)
   * @see org.eclipse.net4j.pop.product.ProductPackage#getPopProduct_PopProject()
   * @model required="true"
   * @generated
   */
  PopProject getPopProject();

  /**
   * Sets the value of the '{@link org.eclipse.net4j.pop.product.PopProduct#getPopProject <em>Pop Project</em>}'
   * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Pop Project</em>' reference.
   * @see #getPopProject()
   * @generated
   */
  void setPopProject(PopProject value);

  /**
   * Returns the value of the '<em><b>Projects</b></em>' containment reference list. The list contents are of type
   * {@link org.eclipse.net4j.pop.product.WorkspaceProject}. It is bidirectional and its opposite is '
   * {@link org.eclipse.net4j.pop.product.WorkspaceProject#getWorkspaceSpec <em>Workspace Spec</em>}'. <!--
   * begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Projects</em>' containment reference list isn't clear, there really should be more of a
   * description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Projects</em>' containment reference list.
   * @see org.eclipse.net4j.pop.product.ProductPackage#getPopProduct_Projects()
   * @see org.eclipse.net4j.pop.product.WorkspaceProject#getWorkspaceSpec
   * @model opposite="workspaceSpec" containment="true"
   * @generated
   */
  EList<WorkspaceProject> getProjects();

  /**
   * Returns the value of the '<em><b>Working Sets</b></em>' containment reference list. The list contents are of type
   * {@link org.eclipse.net4j.pop.product.WorkingSet}. It is bidirectional and its opposite is '
   * {@link org.eclipse.net4j.pop.product.WorkingSet#getWorkspaceSpec <em>Workspace Spec</em>}'. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Working Sets</em>' containment reference list isn't clear, there really should be more
   * of a description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Working Sets</em>' containment reference list.
   * @see org.eclipse.net4j.pop.product.ProductPackage#getPopProduct_WorkingSets()
   * @see org.eclipse.net4j.pop.product.WorkingSet#getWorkspaceSpec
   * @model opposite="workspaceSpec" containment="true"
   * @generated
   */
  EList<WorkingSet> getWorkingSets();

  /**
   * Returns the value of the '<em><b>Configurators</b></em>' containment reference list. The list contents are of type
   * {@link org.eclipse.net4j.pop.product.WorkspaceConfigurator}. It is bidirectional and its opposite is '
   * {@link org.eclipse.net4j.pop.product.WorkspaceConfigurator#getWorkspaceSpec <em>Workspace Spec</em>}'. <!--
   * begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Configurators</em>' containment reference list isn't clear, there really should be more
   * of a description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Configurators</em>' containment reference list.
   * @see org.eclipse.net4j.pop.product.ProductPackage#getPopProduct_Configurators()
   * @see org.eclipse.net4j.pop.product.WorkspaceConfigurator#getWorkspaceSpec
   * @model opposite="workspaceSpec" containment="true"
   * @generated
   */
  EList<WorkspaceConfigurator> getConfigurators();

} // PopProduct